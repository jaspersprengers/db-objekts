package com.dbobjekts.component

import com.dbobjekts.api.Tuple3
import com.dbobjekts.testdb.acme.core.Department
import com.dbobjekts.testdb.acme.core.EmployeeDepartment
import com.dbobjekts.testdb.acme.core.*
import com.dbobjekts.testdb.acme.hr.Certificate
import com.dbobjekts.testdb.acme.hr.Hobby
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.time.LocalDate
import com.dbobjekts.testdb.AddressType


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

        val tm = AcmeDB.transactionManager
        
        @BeforeAll
        @JvmStatic
         fun setup() {

            tm { tr ->
                tr.insert(c).id("us").name("USA").execute()
                tr.insert(c).id("nl").name("Netherlands").execute()
                tr.insert(c).id("fr").name("France").execute()

                tr.insert(h).id("c").name("curling").execute()
                tr.insert(h).id("f").name("fishing").execute()
            }

            val dob = LocalDate.of(1980, 3, 3)
            val johnsId = tm { tr ->
                tr.insert(Employee).name("John").salary(2000.0).married(true).dateOfBirth(dob).execute()
            }
            val janesId = tm { tr ->
                tr.insert(Employee).name("Jane").salary(3000.0).married(true).dateOfBirth(dob).execute()
            }
            val itDept = tm { tr -> tr.insert(Department).name("IT").execute() }
            val hrDept = tm { tr -> tr.insert(Department).name("IT").execute() }
            val johnAndJanesAddress =
                tm { tr -> tr.insert(a).street("Home sweet home").countryId("nl").execute() }
            tm { tr -> tr.insert(ed).departmentId(itDept).employeeId(johnsId).execute() }
            tm { tr -> tr.insert(ed).departmentId(hrDept).employeeId(janesId).execute() }
            tm { tr -> tr.insert(ed).departmentId(itDept).employeeId(janesId).execute() }
            tm { tr ->
                
                tr.insert(Certificate).name("BSC").employeeId(johnsId).execute()
                tr.insert(Certificate).name("MA").employeeId(janesId).execute()
                tr.insert(EmployeeAddress).addressId(johnAndJanesAddress).employeeId(johnsId).kind(AddressType.HOME)
                    .execute()
                tr.insert(EmployeeAddress).addressId(johnAndJanesAddress).employeeId(janesId).kind(AddressType.HOME)
                    .execute()
            }

            val johnsWorkAddress =
                tm { tr -> tr.insert(a).street("John's office").countryId("us").execute() }
            val janesWorkAddress =
                tm { tr -> tr.insert(a).street("Jane's office").countryId("fr").execute() }
            tm { tr ->
                tr.insert(ea).addressId(johnsWorkAddress).employeeId(johnsId).kind(AddressType.WORK).execute()
                tr.insert(ea).addressId(janesWorkAddress).employeeId(janesId).kind(AddressType.WORK).execute()
            }
        }

    }

    @Test
    fun `get all work addresses`() {
        tm { tr ->
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
        val results: List<Tuple3<String, String, String>> = tm {
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
        tm { tr ->
            tr.update(e).hobbyId("c").where(e.name.eq("John"))
            val rows = tr.select(e.name, h.name).from(e.innerJoin(h)).asList()
            assertThat(rows).hasSize(1)
            assertEquals("curling", rows[0].v2)
        }
    }

    @Test
    fun `get employees address and departments`() {
        tm { tr ->
            val rows = tr.select(a.street, d.name).where(e.name.eq("John")).asList()
            assertEquals("Home sweet home", rows[0].v1)
            assertEquals("IT", rows[0].v2)
        }
    }

    @Test
    fun `test outer join on matching row`() {
        tm { tr ->
            val row = tr.select(e.name, ce.name.nullable).where(e.name.eq("John").and(ce.name).eq("BSC")).first()
            assertEquals("John", row.v1)
            assertEquals("BSC", row.v2)
        }
    }

}
