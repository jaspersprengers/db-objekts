package com.dbobjekts.component

import com.dbobjekts.api.Tuple3
import com.dbobjekts.sampledbs.h2.acme.core.Department
import com.dbobjekts.sampledbs.h2.acme.core.EmployeeDepartment
import com.dbobjekts.sampledbs.h2.acme.core.*
import com.dbobjekts.sampledbs.h2.acme.custom.AddressType
import com.dbobjekts.sampledbs.h2.acme.hr.Certificate
import com.dbobjekts.sampledbs.h2.acme.hr.Hobby
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.time.LocalDate


class ForeignKeyComponentTest {

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
            AcmeDB.setupDatabaseObjects()
            AcmeDB.newTransaction { tr ->
                tr.insert(c).id("us").name("USA").execute()
                tr.insert(c).id("nl").name("Netherlands").execute()
                tr.insert(c).id("fr").name("France").execute()

                tr.insert(h).id("c").name("curling").execute()
                tr.insert(h).id("f").name("fishing").execute()
            }

            val dob = LocalDate.of(1980, 3, 3)
            val johnsId = AcmeDB.newTransaction { tr ->
                tr.insert(Employee).name("John").salary(2000.0).married(true).dateOfBirth(dob).execute()
            }
            val janesId = AcmeDB.newTransaction { tr ->
                tr.insert(Employee).name("Jane").salary(3000.0).married(true).dateOfBirth(dob).execute()
            }
            val itDept = AcmeDB.newTransaction { tr -> tr.insert(Department).name("IT").execute() }
            val hrDept = AcmeDB.newTransaction { tr -> tr.insert(Department).name("IT").execute() }
            val johnAndJanesAddress =
                AcmeDB.newTransaction { tr -> tr.insert(a).street("Home sweet home").countryId("nl").execute() }
            AcmeDB.newTransaction { tr -> tr.insert(ed).departmentId(itDept).employeeId(johnsId).execute() }
            AcmeDB.newTransaction { tr -> tr.insert(ed).departmentId(hrDept).employeeId(janesId).execute() }
            AcmeDB.newTransaction { tr -> tr.insert(ed).departmentId(itDept).employeeId(janesId).execute() }
            AcmeDB.newTransaction { tr ->
                
                tr.insert(Certificate).name("BSC").employeeId(johnsId).execute()
                tr.insert(Certificate).name("MA").employeeId(janesId).execute()
                tr.insert(EmployeeAddress).addressId(johnAndJanesAddress).employeeId(johnsId).kind(AddressType.HOME)
                    .execute()
                tr.insert(EmployeeAddress).addressId(johnAndJanesAddress).employeeId(janesId).kind(AddressType.HOME)
                    .execute()
            }

            val johnsWorkAddress =
                AcmeDB.newTransaction { tr -> tr.insert(a).street("John's office").countryId("us").execute() }
            val janesWorkAddress =
                AcmeDB.newTransaction { tr -> tr.insert(a).street("Jane's office").countryId("fr").execute() }
            AcmeDB.newTransaction { tr ->
                tr.insert(ea).addressId(johnsWorkAddress).employeeId(johnsId).kind(AddressType.WORK).execute()
                tr.insert(ea).addressId(janesWorkAddress).employeeId(janesId).kind(AddressType.WORK).execute()
            }
        }

    }

    @Test
    fun `get all work addresses`() {
        AcmeDB.newTransaction { tr ->
            val results: List<Tuple3<String?, String?, String?>> =
                tr.select(e.name, a.street, c.name).where(ea.kind.eq(AddressType.WORK)).orderAsc(e.name).asList()
            assertEquals("Jane", results[0].v1)
            assertEquals("Jane's office", results[0].v2)
            assertEquals("France", results[0].v3)

            assertEquals("John", results[1].v1)
            assertEquals("John's office", results[1].v2)
            assertEquals("USA", results[1].v3)
        }
    }


    @Test
    fun `get all private addresses`() {
        val results: List<Tuple3<String, String, String>> = AcmeDB.newTransaction {
            it.select(e.name, a.street, c.name).where(ea.kind.eq(AddressType.HOME)).orderAsc(e.name).asList()
        }
        assertEquals("Jane", results[0].v1)
        assertEquals("Home sweet home", results[0].v2)
        assertEquals("Netherlands", results[0].v3)

        assertEquals("John", results[1].v1)
        assertEquals("Home sweet home", results[1].v2)
        assertEquals("Netherlands", results[1].v3)

    }

    @Test
    fun `get employees with hobbies`() {
        AcmeDB.newTransaction { tr ->
            tr.update(e).hobbyId("c").where(e.name.eq("John"))
            val rows = tr.select(e.name, h.name).from(e.innerJoin(h)).asList()
            assertThat(rows).hasSize(1)
            assertEquals("curling", rows[0].v2)
        }
    }

    @Test
    fun `get employees address and departments`() {
        AcmeDB.newTransaction { tr ->
            val rows = tr.select(a.street, d.name).where(e.name.eq("John")).asList()
            assertEquals("Home sweet home", rows[0].v1)
            assertEquals("IT", rows[0].v2)
        }
    }

    @Test
    fun `test outer join on matching row`() {
        AcmeDB.newTransaction { tr ->
            val row = tr.select(e.name, ce.name.nullable).where(e.name.eq("John").and(ce.name).eq("BSC")).first()
            assertEquals("John", row.v1)
            assertEquals("BSC", row.v2)
        }
    }

}
