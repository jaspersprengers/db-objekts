package com.dbobjekts.component

import com.dbobjekts.testdb.AddressType
import com.dbobjekts.testdb.acme.core.*
import com.dbobjekts.testdb.acme.hr.Hobby
import com.dbobjekts.testdb.acme.hr.HobbyRow
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class UpdateStatementComponentTest {

    val e = Employee
    val h = Hobby

    companion object {
        val tm = AcmeDB.transactionManager

        @JvmStatic
        @BeforeAll
        fun setup() {
            AcmeDB.createExampleCatalog(AcmeDB.transactionManager)
        }
    }

    @Test
    fun `verify null and default values`() {
        tm { tr ->
            val id = tr.insert(e).mandatoryColumns("Jack", 3000.5, LocalDate.of(1980, 1, 1)).execute()
            assertNull(tr.select(h.name.nullable).from(Employee.leftJoin(Hobby)).where(e.id.eq(id)).first())
            assertNull(tr.select(e.children).where(e.id.eq(id)).first())
        }
    }

    @Test
    fun `update name for existing`() {
        tm { tr ->
            tr.insert(e).name("Howard").salary(50.9).dateOfBirth(LocalDate.of(1970, 3, 3)).married(false).execute()

            val row: EmployeeRow = tr.select(e).where(e.name.eq("Howard")).first()
            val updated = row.copy(name = "Janet", married = true, salary = 50000.3, children = 5)

            tr.update(updated)
            val retrieved: EmployeeRow = tr.select(e).where(e.id.eq(row.id)).first()
            assertThat(retrieved).isEqualTo(updated)

            tr.update(e).dateOfBirth(LocalDate.of(1970, 10, 10)).where(e.name.eq("Janet"))
            assertEquals("1970-10-10", tr.select(e.dateOfBirth).where(e.name.eq("Janet")).first().toString())

            tr.update(e).salary(3300.50).where(e.name.eq("Janet"))
            assertEquals(3300.50, tr.select(e.salary).where(e.name.eq("Janet")).first())

            tr.update(e).married(false).where(e.name.eq("Janet"))
            assertFalse(tr.select(e.married).where(e.name.eq("Janet")).first() ?: false)

            tr.update(e).married(false).where(e.name.eq("Janet"))
            assertFalse(tr.select(e.married).where(e.name.eq("Janet")).first() ?: false)

            tr.update(e).children(2).where(e.name.eq("Janet"))
            assertEquals(2, tr.select(e.children).where(e.name.eq("Janet")).first())

            tr.update(e).children(0).where(e.name.eq("Janet"))
            assertEquals(0, tr.select(e.children).where(e.name.eq("Janet")).first())

            tr.update(e).children(null).where(e.name.eq("Janet"))
            assertNull(tr.select(e.children).where(e.name.eq("Janet")).first())

            tr.update(e).hobbyId("c").where(e.name.eq("Janet"))
            assertEquals("Chess", tr.select(h.name).where(e.name.eq("Janet")).first())

            tr.update(e).hobbyId(null).where(e.name.eq("Janet"))
            assertEquals(0, tr.select(h.name).where(e.name.eq("Janet")).asList().size)

        }
    }


    @Test
    fun `update row with non-pk row throws`(){
        tm { tr ->
            val empId = tr.insert(EmployeeRow(
                name = "Jill",
                salary = 300.5,
                married = true,
                dateOfBirth = LocalDate.of(1980, 3, 3),
                children = 2,
                hobbyId = null
            ))
            val addressId = tr.insert(AddressRow(street="Sparrenlaan", postcode="5591HA", countryId = "be"))
            val ea = EmployeeAddressRow(empId, addressId, AddressType.HOME)
            tr.insert(ea)

            Assertions.assertThatThrownBy( { tr.update(ea.copy(kind = AddressType.WORK)) })
                .hasMessage("Sorry, but you cannot use entity-based update for table EmployeeAddress. There must be exactly one column marked as primary key.")
        }
    }

    @Test
    fun `test entity-based update`() {
        tm { tr ->
            val hobby = HobbyRow("go", "the game of Go")
            tr.insert(hobby)
            val empId = tr.insert(EmployeeRow(
                name = "John",
                salary = 300.5,
                married = true,
                dateOfBirth = LocalDate.of(1980, 3, 3),
                children = 2,
                hobbyId = "go"
            ))

            val retrieved2: EmployeeRow = tr.select(Employee).where(Employee.id.eq(empId)).first()
            tr.update(retrieved2.copy(salary = retrieved2.salary + 100))


            val retrieved: EmployeeRow = tr.select(Employee).where(Employee.id.eq(empId)).first()
            val updated = retrieved.copy(
                name = "Jake",
                salary = 320.5,
                married = false,
                dateOfBirth = LocalDate.of(1990, 3, 3),
                children = 1,
                hobbyId = null
            )
            tr.update(updated)
            val retrievedUpdated = tr.select(Employee).where(Employee.id.eq(empId)).first()
            assertThat(retrievedUpdated.name).isEqualTo("Jake")
            assertThat(retrievedUpdated.salary).isEqualTo(320.5)
            assertThat(retrievedUpdated.married).isFalse()
            assertThat(retrievedUpdated.dateOfBirth).isEqualTo(LocalDate.of(1990, 3, 3))
            assertThat(retrievedUpdated.children).isEqualTo(1)
            assertThat(retrievedUpdated.hobbyId).isNull()
        }
    }

}
