package com.dbobjekts.integration

import com.dbobjekts.Tuple2
import com.dbobjekts.fixture.h2.H2DB
import com.dbobjekts.integration.h2.core.Address
import com.dbobjekts.integration.h2.core.Country
import com.dbobjekts.integration.h2.core.Employee
import com.dbobjekts.integration.h2.core.EmployeeAddress
import com.dbobjekts.integration.h2.hr.Hobby
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.time.LocalDate

class SelectIntegrationTest {


    companion object {

        val e = Employee
        val a = Address
        val h = Hobby
        var jane: Long = 0
        var john: Long = 0
        var arthur: Long = 0

        @BeforeAll
        @JvmStatic
        fun setup() {
            H2DB.setupDatabaseObjects()
            H2DB.newTransaction {
                val dob = LocalDate.of(1990, 12, 5)
                it.insert(Hobby).mandatoryColumns("chess", "chess").execute()
                arthur = it.insert(Employee).mandatoryColumns("Arthur", 300.50, dob).execute()
                jane = it.insert(Employee).mandatoryColumns("Jane", 300.50, LocalDate.of(1990, 12, 5)).hobbyId("chess").execute()
                john = it.insert(Employee).children(2).dateOfBirth(LocalDate.of(1990, 12, 5)).hobbyId("chess").name("John").married(true)
                    .salary(3000.0).execute()
                it.insert(Country).id("DE").name("Germany").execute()
                it.insert(Country).id("NL").name("Netherlands").execute()
                val zuidhoek = it.insert(Address).mandatoryColumns(street = "Zuidhoek", countryId = "DE").execute()
                it.insert(EmployeeAddress).mandatoryColumns(employeeId = jane, addressId = zuidhoek, kind = "WORK").execute()

            }

        }
    }


    @Test
    fun `test select two columns from two tables`() {
        H2DB.newTransaction({
            val result: Tuple2<Double?, String?> =
                it.select(e.salary, a.street).where(e.name.eq("Jane").and(a.street).eq("Zuidhoek")).first()
            assert(result.first == 300.50)
            assert(result.second == "Zuidhoek")
        })
    }

    @Test
    fun `test select two rows with custom mapper`() {
        H2DB.newTransaction({
            val buffer = mutableListOf<String?>()
            it.select(e.name).noWhereClause().orderAsc(e.name).forEachRow({ row ->
                buffer.add(row)
                //there are three rows in the resultset, but we stop fetching after two
                buffer.size != 2
            })
            assert(buffer.size == 2)
            assert(buffer[0] == "Arthur")
            assert(buffer[1] == "Jane")
        })
    }

    @Test
    fun `test select, IN whereclause`() {
        H2DB.newTransaction({ s ->
            val name = s.select(e.name).where(e.name.within("John", "Jane").and(e.married).eq(true)).first()
            assert(name == "John")
        })
    }

    @Test
    fun `test select all without where clause`() {
        H2DB.newTransaction({ s ->
            val ret = {
                s.select(Employee.id).noWhereClause().first()
            }
        })
    }

    @Test
    fun `test select all with optionally one row`() {
        H2DB.newTransaction({ s ->
            val ret = s.select(e.name).noWhereClause().orderAsc(e.name).first()
            assertNotNull(ret)
            assertEquals("Arthur", ret)
        })
    }

    @Test
    fun `test select some columns from two tables`() {
        H2DB.newTransaction({ s ->
            val nameStreet: Tuple2<String, String> = s.select(e.name, a.street).where(e.name.eq("Jane")).first()
            assertEquals(nameStreet.second, "Zuidhoek")
        })
    }

    @Test
    fun `test left join select person name where hobby is null`() {
        H2DB.newTransaction({ s ->
            s.select(e.name).from(e.leftJoin(h)).where(e.hobbyId.isNull()).first();
        })
    }

    @Test
    fun `test left join select person name and hobby name without where clause`() {
        H2DB.newTransaction({ s ->
            val nameHobby: Tuple2<String, String?> = s.select(e.name, h.name.nullable).from(e.leftJoin(h)).noWhereClause().first();
        })
    }

    @Test
    fun `test select the same column twice as a list is OK`() {
        val result =
            H2DB.newTransaction({ s -> s.select(Employee.name, Employee.name).where(Employee.name.eq("Arthur")).first() })
        assertEquals("Arthur", result.first)
        assertEquals("Arthur", result.second)
    }

    @Test
    fun `use the same column in two conditions`() {
        val result =
            H2DB.newTransaction({ s ->
                s.select(Employee.name).where(Employee.salary.gt(300.0).and(Employee.salary).lt(500.0)).first()
            })
        assertEquals("Arthur", result)
    }

    @Test
    fun `test select address with no driving table`() {
        H2DB.newTransaction({ s ->
            s.select(a.street, e.name).where(a.street.eq("Zuidhoek")).first()
        })
    }

    @Test
    fun `test select, IN whereclause invokes preparedstatement`() {
        H2DB.newTransaction({ s ->
            s.select(Employee.id).where(e.id.ne(5).and(e.name).within("John", "Arthur"))
                .first()
        })
    }

    @Test
    fun `limit clause with invalid value returns one`() {
        H2DB.newTransaction({ s ->
            s.select(Employee.name).where(Employee.id.gt(5)).limit(0).first();
        })
    }

    @Test
    fun `Order by clause`() {
        H2DB.newTransaction({ s ->
            s.select(Employee.name).where(Employee.id.gt(5)).orderAsc(Employee.name).first()
        })
    }

    @Test
    fun `Multiple order by clauses`() {
        H2DB.newTransaction({ s ->
            s.select(Employee.name).where(Employee.id.gt(5)).orderAsc(Employee.name).orderDesc(Employee.salary)
                .first();
        })
    }

    @Test
    fun `limit clause with positive value produces proper clause`() {
        H2DB.newTransaction({ s ->
            s.select(Employee.name).where(Employee.id.gt(5)).limit(3).first();
        })
    }


}
