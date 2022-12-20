package com.dbobjekts.integration.h2

import com.dbobjekts.api.Tuple2
import com.dbobjekts.integration.h2.core.Address
import com.dbobjekts.integration.h2.core.Country
import com.dbobjekts.integration.h2.core.Employee
import com.dbobjekts.integration.h2.core.EmployeeAddress
import com.dbobjekts.integration.h2.custom.AddressType
import com.dbobjekts.integration.h2.hr.Hobby
import org.assertj.core.api.Assertions.assertThat
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
                val zuidhoek = it.insert(Address).mandatoryColumns(street = "Zuidhoek", countryId = "NL").execute()
                it.insert(EmployeeAddress).mandatoryColumns(employeeId = jane, addressId = zuidhoek, kind = AddressType.WORK).execute()
            }
        }
    }


    @Test
    fun `test select two columns from two tables`() {
        H2DB.newTransaction({
            val result: Tuple2<Double?, String?> =
                it.select(e.salary, a.street).where(e.name.eq("Jane").and(a.street).eq("Zuidhoek")).first()
            assert(result.v1 == 300.50)
            assert(result.v2 == "Zuidhoek")
        })
    }

    @Test
    fun `test select two rows with custom mapper`() {
        H2DB.newTransaction({
            val buffer = mutableListOf<String?>()
            it.select(e.name).orderAsc(e.name).forEachRow({ row ->
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
            s.select(e.name).asList()
            val name = s.select(e.name).where(e.name.within("John", "Jane").and(e.married).eq(true)).first()
            assert(name == "John")
        })
    }

    @Test
    fun `test select all without where clause`() {
        H2DB.newTransaction({ s ->
            val ret = {
                s.select(e.id).first()
            }
        })
    }

    @Test
    fun `test select all with optionally one row`() {
        H2DB.newTransaction({ s ->
            val ret = s.select(e.name).orderAsc(e.name).first()
            assertNotNull(ret)
            assertEquals("Arthur", ret)
        })
    }

    @Test
    fun `test select some columns from two tables`() {
        H2DB.newTransaction({ s ->
            val nameStreet: Tuple2<String, String> = s.select(e.name, a.street).where(e.name.eq("Jane")).first()
            assertEquals(nameStreet.v2, "Zuidhoek")
        })
    }

    @Test
    fun `test left join select person name where hobby is null`() {
        H2DB.newTransaction({ s ->
            assertThat(s.select(e.name).from(e.leftJoin(h)).where(e.hobbyId.isNull()).first()).isEqualTo("Arthur")
        })
    }

    @Test
    fun `test inner join for person based on country`() {
        H2DB.newTransaction({ s ->
            assertThat(
                s.select(e.name).from(
                    e.innerJoin(EmployeeAddress)
                        .innerJoin(Address)
                        .innerJoin(Country)
                )
                    .where(Country.name.eq("Netherlands")).first()
            ).isEqualTo("Jane")
            println(s.statementLog().lastSQLStatement())
        })
    }

    @Test
    fun `test left join select person name and hobby name without where clause`() {
        H2DB.newTransaction({ s ->
            val (name, hobby) = s.select(e.name, h.name.nullable).from(e.leftJoin(h)).first();
            assertThat(hobby).isNull()
        })
    }

    @Test
    fun `test select the same column twice as a list is OK`() {
        val result =
            H2DB.newTransaction({ s -> s.select(e.name, e.name).where(e.name.eq("Arthur")).first() })
        assertEquals("Arthur", result.v1)
        assertEquals("Arthur", result.v2)
    }

    @Test
    fun `use the same column in two conditions`() {
        val result =
            H2DB.newTransaction({ s ->
                s.select(e.name).where(e.salary.gt(300.0).and(e.salary).lt(500.0)).first()
            })
        assertEquals("Arthur", result)
    }

    @Test
    fun `test select address with no driving table`() {
        H2DB.newTransaction({ s ->
            assertThat(s.select(a.street).where(a.street.eq("Zuidhoek")).first()).isEqualTo("Zuidhoek")
        })
    }

    @Test
    fun `test select, IN whereclause invokes preparedstatement`() {
        H2DB.newTransaction({ s ->
            assertThat(s.select(e.id).where(e.name.within("John", "Arthur")).asList()).hasSize(2)
        })
    }

    @Test
    fun `limit clause with invalid value returns one`() {
        H2DB.newTransaction({ s ->
            s.select(e.name).where(e.id.gt(5)).limit(0).first()
        })
    }

    @Test
    fun `Order by clause`() {
        H2DB.newTransaction({ s ->
            s.select(e.name).where(e.id.gt(5)).orderAsc(e.name).first()
        })
    }

    @Test
    fun `Multiple order by clauses`() {
        H2DB.newTransaction({ s ->
            assertThat(s.select(e.name).where(e.id.gt(5)).orderAsc(e.name).orderDesc(e.salary).first()).isEqualTo("Arthur")
        })
    }

    @Test
    fun `limit clause with positive value produces proper clause`() {
        H2DB.newTransaction({ s ->
            s.select(e.name).where(e.id.gt(5)).limit(3).first()
        })
    }


}
