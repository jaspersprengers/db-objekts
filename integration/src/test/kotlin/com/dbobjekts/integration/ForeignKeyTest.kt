package com.dbobjekts.integration

import com.dbobjekts.Tuple3
import com.dbobjekts.fixture.h2.H2DB
import com.dbobjekts.integration.h2.core.*
import com.dbobjekts.integration.h2.custom.AddressType
import com.dbobjekts.integration.h2.hr.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeAll
import java.time.LocalDate


class ForeignKeyTest {

    companion object {

        val e = Employee
        val a = Address
        val ea = EmployeeAddress
        val c = Country
        val h = Hobby
        val ce = Certificate
        val d = Department
        val ed = EmployeeDepartment

        @BeforeAll
        @JvmStatic
         fun setup() {
            H2DB.setupDatabaseObjects()
            H2DB.newTransaction { tr ->
                tr.insert(c).id("us").name("USA").execute()
                tr.insert(c).id("nl").name("Netherlands").execute()
                tr.insert(c).id("fr").name("France").execute()

                tr.insert(h).id("c").name("curling").execute()
                tr.insert(h).id("f").name("fishing").execute()
            }

            val dob = LocalDate.of(1980, 3, 3)
            val johnsId = H2DB.newTransaction { tr ->
                tr.insert(Employee).name("John").salary(2000.0).married(true).dateOfBirth(dob).execute()
            }
            val janesId = H2DB.newTransaction { tr ->
                tr.insert(Employee).name("Jane").salary(3000.0).married(true).dateOfBirth(dob).execute()
            }
            val itDept = H2DB.newTransaction { tr -> tr.insert(Department).name("IT").execute() }
            val hrDept = H2DB.newTransaction { tr -> tr.insert(Department).name("IT").execute() }
            val johnAndJanesAddress =
                H2DB.newTransaction { tr -> tr.insert(a).street("Home sweet home").countryId("nl").execute() }
            H2DB.newTransaction { tr -> tr.insert(ed).departmentId(itDept).employeeId(johnsId).execute() }
            H2DB.newTransaction { tr -> tr.insert(ed).departmentId(hrDept).employeeId(janesId).execute() }
            H2DB.newTransaction { tr -> tr.insert(ed).departmentId(itDept).employeeId(janesId).execute() }
            H2DB.newTransaction { tr ->
                
                tr.insert(Certificate).name("BSC").employeeId(johnsId).execute()
                tr.insert(Certificate).name("MA").employeeId(janesId).execute()
                tr.insert(EmployeeAddress).addressId(johnAndJanesAddress).employeeId(johnsId).kind(AddressType.HOME)
                    .execute()
                tr.insert(EmployeeAddress).addressId(johnAndJanesAddress).employeeId(janesId).kind(AddressType.HOME)
                    .execute()
            }

            val johnsWorkAddress =
                H2DB.newTransaction { tr -> tr.insert(a).street("John's office").countryId("us").execute() }
            val janesWorkAddress =
                H2DB.newTransaction { tr -> tr.insert(a).street("Jane's office").countryId("fr").execute() }
            H2DB.newTransaction { tr ->
                tr.insert(ea).addressId(johnsWorkAddress).employeeId(johnsId).kind(AddressType.WORK).execute()
                tr.insert(ea).addressId(janesWorkAddress).employeeId(janesId).kind(AddressType.WORK).execute()
            }
        }

    }

    @Test
    fun `get all work addresses`() {
        H2DB.newTransaction { tr ->
            val results: List<Tuple3<String?, String?, String?>> =
                tr.select(e.name, a.street, c.name).where(ea.kind.eq(AddressType.WORK)).orderAsc(e.name).asList()
            assertEquals("Jane", results[0].first)
            assertEquals("Jane's office", results[0].second)
            assertEquals("France", results[0].third)

            assertEquals("John", results[1].first)
            assertEquals("John's office", results[1].second)
            assertEquals("USA", results[1].third)
        }
    }


    @Test
    fun `get all private addresses`() {
        val results = H2DB.newTransaction {
            it.select(e.name, a.street, c.name).where(ea.kind.eq(AddressType.HOME)).orderAsc(e.name).asList()
        }
        assertEquals("Jane", results[0].first)
        assertEquals("Home sweet home", results[0].second)
        assertEquals("Netherlands", results[0].third)

        assertEquals("John", results[1].first)
        assertEquals("Home sweet home", results[1].second)
        assertEquals("Netherlands", results[1].third)

    }

    @Test
    fun `get employees with hobbies`() {
        H2DB.newTransaction { tr ->
            tr.update(e).hobbyId("c").where(e.name.eq("John")).execute()
            val rows = tr.select(e.name, h.name).from(e.innerJoin(h)).noWhereClause().asList()
            assertThat(rows).hasSize(1)
            assertEquals("curling", rows[0].second)
        }
    }

    @Test
    fun `get employees address and departments`() {
        H2DB.newTransaction { tr ->
            val rows = tr.select(a.street, d.name).where(e.name.eq("John")).asList()
            assertEquals("Home sweet home", rows[0].first)
            assertEquals("IT", rows[0].second)
        }
    }

    @Test
    fun `test outer join on matching row`() {
        H2DB.newTransaction { tr ->
            val row = tr.select(e.name, ce.name.nullable).where(e.name.eq("John").and(ce.name).eq("BSC")).first()
            assertEquals("John", row.first)
            assertEquals("BSC", row.second)
        }
    }

}
