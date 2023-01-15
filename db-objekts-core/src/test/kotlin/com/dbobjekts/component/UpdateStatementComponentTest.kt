package com.dbobjekts.component

import com.dbobjekts.metadata.joins.ManualJoinChain
import com.dbobjekts.testdb.acme.core.Employee
import com.dbobjekts.testdb.acme.core.EmployeeRow
import com.dbobjekts.testdb.acme.hr.Hobby
import com.dbobjekts.testdb.acme.hr.HobbyRow
import com.dbobjekts.testdb.acme.library.Composite
import com.dbobjekts.testdb.acme.library.CompositeForeignKey
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.time.LocalDate

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
    fun `composite foreign keys through manual join`() {
        tm {
            val co = Composite
            val cof = CompositeForeignKey
            val now = LocalDate.now()
            it.insert(Composite).mandatoryColumns("ISBN", "The Shining").published(now).execute()
            it.insert(CompositeForeignKey).mandatoryColumns("ISBN", "The Shining").message("Hello world!").execute()
            val (dt, message) = it.select(Composite.published, CompositeForeignKey.message)
                .from(co.innerJoin(cof).on(co.isbn.eq(cof.isbn).and(cof.title).eq(co.title)))
                .where(Composite.isbn.eq("ISBN")).first()
            assertThat(dt).isEqualTo(now)
            assertThat(message).isEqualTo("Hello world!")
        }
    }


    @Test
    fun `update all fields in employee record`() {
        tm { tr ->
            tr.insert(e).name("Howard").salary(50.9).dateOfBirth(LocalDate.of(1970, 3, 3)).married(false).execute()

            val row: EmployeeRow = tr.select(e).where(e.name.eq("Howard")).first()
            val updated = row.copy(name = "Janet", married = true, salary = 50000.3, children = 5)

            tr.save(updated)
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
    fun `row-based update scenarios`() {
        tm { tr ->
            val hobby = HobbyRow("go", "the game of Go")
            tr.save(hobby)
            val empId = tr.insert(
                EmployeeRow(
                    name = "John",
                    salary = 300.5,
                    married = true,
                    dateOfBirth = LocalDate.of(1980, 3, 3),
                    children = 2,
                    hobbyId = "go"
                )
            )

            val retrieved2: EmployeeRow = tr.select(Employee).where(Employee.id.eq(empId)).first()
            tr.save(retrieved2.copy(salary = retrieved2.salary + 100))


            val retrieved: EmployeeRow = tr.select(Employee).where(Employee.id.eq(empId)).first()
            val updated = retrieved.copy(
                name = "Jake",
                salary = 320.5,
                married = false,
                dateOfBirth = LocalDate.of(1990, 3, 3),
                children = 1,
                hobbyId = null
            )
            tr.save(updated)
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
