package com.dbobjekts.component

import com.dbobjekts.testdb.acme.core.Employee
import com.dbobjekts.testdb.acme.hr.Hobby
import com.dbobjekts.statement.select.SelectStatementExecutor
import com.dbobjekts.testdb.acme.core.EmployeeRow
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class WriteStatementComponentTest {

    val e = Employee
    val h = Hobby

    companion object {
        val tm = AcmeDB.transactionManager

        @JvmStatic
        @BeforeAll
        fun setup() {
            AcmeDB.deleteAllTables(tm)
        }
    }

    @Test
    @Order(1)
    fun `verify ranges`() {
        fun insert(name: String, salary: Int) {
            val id = tm {
                it.insert(e).name(name).salary(salary.toDouble()).dateOfBirth(LocalDate.of(1980, 3, 3)).married(true).execute()
            }
            assert(id > 0)
        }

        insert("Eve", 34)
        insert("Bob", 32)
        insert("Inez", 35)
        insert("Diane", 33)
        insert("Hardeep", 37)
        insert("Alice", 36)
        insert("Gina", 31)
        insert("Charlie", 30)
        insert("Fred", 38)
        insert("Jasper", 39)
        assertEquals(7, tm { it.select(e.salary).where(e.salary.notIn(31.0, 33.0, 35.0)).asList().size })
        assertEquals(3, tm { it.select(e.salary).where(e.salary.within(31.0, 33.0, 35.0)).asList().size })
        assertEquals(4, tm { it.select(e.salary).where(e.salary.gt(35.0)).asList().size })
        assertEquals(5, tm { it.select(e.salary).where(e.salary.ge(35.0)).asList().size })
        assertEquals(4, tm { it.select(e.salary).where(e.salary.lt(34.0)).asList().size })
        assertEquals(5, tm { it.select(e.salary).where(e.salary.le(34.0)).asList().size })
        assertEquals(2, tm { it.select(e.salary).where(e.salary.ge(33.0).and(e.salary).le(34.0)).asList().size })
        assertEquals(3, tm { it.select(e.salary).where(e.salary.within(31.0, 33.0, 35.0)).asList().size })

        assertEquals(39.0, tm { it.select(e.salary).orderDesc(e.salary).first() })
        assertEquals(30.0, tm { it.select(e.salary).orderAsc(e.salary).first() })
        assertEquals(36.0, tm { it.select(e.salary).orderAsc(e.name).first() })
        assertEquals(39.0, tm { it.select(e.salary).orderDesc(e.name).first() })

        insert("Oliver", 31)
        insert("Karl", 30)
        val entities = tm { it.select(e.name).orderAsc(e.salary).orderDesc(e.name).asList() }
        assertEquals("Karl", entities[0])
        assertEquals("Charlie", entities[1])
        assertEquals("Oliver", entities[2])
        assertEquals("Gina", entities[3])
        val l2 = tm { it.select(e.name).where(e.salary.le(31.0)).orderDesc(e.salary).orderAsc(e.name).asList() }
        assertEquals("Gina", l2[0])
        assertEquals("Oliver", l2[1])
        assertEquals("Charlie", l2[2])
        assertEquals("Karl", l2[3])
    }

    @Test
    @Order(2)
    fun `verify null and default values`() {
        tm { tr ->
            val id = tr.insert(e).mandatoryColumns("Jack", 3000.5, LocalDate.of(1980, 1, 1)).execute()
            assertNull(tr.select(h.name.nullable).from(Employee.leftJoin(Hobby)).where(e.id.eq(id)).first())
            assertNull(tr.select(e.children).where(e.id.eq(id)).first())
        }
    }

    @Test
    @Order(2)
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

            tr.insert(h).id("c").name("curling").execute()
            tr.update(e).hobbyId("c").where(e.name.eq("Janet"))
            assertEquals("curling", tr.select(h.name).where(e.name.eq("Janet")).first())

            tr.update(e).hobbyId(null).where(e.name.eq("Janet"))
            assertEquals(0, tr.select(h.name).where(e.name.eq("Janet")).asList().size)

        }
    }


    @Test
    @Order(11)
    fun `test LIKE conditions on strings`() {
        tm { tr ->
            fun insert(name: String) {
                val dob = LocalDate.of(1980, 1, 1)
                tr.insert(e).name(name).dateOfBirth(dob).salary(3500.0).execute()
            }

            fun checkCount(result: SelectStatementExecutor<*, *>, size: Int) {
                assertEquals(size, result.asList().size)
            }
            insert("Arthur Philip Dent")
            insert("Arthur Matthew Dent")
            insert("Arthur Matthew Holmes")

            checkCount(tr.select(e.id).where(e.name.startsWith("Arthur")), 3)
            checkCount(tr.select(e.id).where(e.name.startsWith("Arthur Matthew")), 2)
            checkCount(tr.select(e.id).where(e.name.startsWith("Arthur Matthew Dent")), 1)

            checkCount(tr.select(e.id).where(e.name.contains("Dent")), 2)
            checkCount(tr.select(e.id).where(e.name.contains("Matthew")), 2)
            checkCount(tr.select(e.id).where(e.name.contains("Arthur Matthew")), 2)
            checkCount(tr.select(e.id).where(e.name.contains("Arthur Matthew Dent")), 1)
            checkCount(tr.select(e.id).where(e.name.contains("Arthur Philip Holmes")), 0)

            checkCount(tr.select(e.id).where(e.name.endsWith("Dent")), 2)
            checkCount(tr.select(e.id).where(e.name.endsWith("Holmes")), 1)
            checkCount(tr.select(e.id).where(e.name.endsWith("Arthur Matthew Dent")), 1)
        }
    }

    @Test
    @Order(12)
    fun `delete clause correct sql`() {
        tm { tr ->
            tr.deleteFrom(Employee).where(Employee.id.eq(5L).and(Employee.name).notIn("bob", "alice", "eve"))

            tr.deleteFrom(Employee)
                .where(Employee.name.notIn("bob", "alice", "eve").and(Employee.married).ne(true).or(Employee.salary.gt(300.50)))

            val id = tr.select(Employee.id).first()
            assertThat(tr.deleteFrom(Employee).where(Employee.id.eq(id))).isEqualTo(1)

            Assertions.assertThatThrownBy { tr.update(e).children(2).where(h.name.eq("curling")) }
                .hasMessage("Your database does not support UPDATE statements with JOIN syntax.")

            Assertions.assertThatThrownBy { tr.deleteFrom(e.innerJoin(Hobby)).where(h.name.eq("curling")) }
                .hasMessage("Your database does not support DELETE statements with JOIN syntax.")

            tr.update(Employee).children(5).where()
            val children = tr.select(Employee.children).asList()
            assertThat(children).hasSize(11)
            assertThat(children).containsOnly(5)

            tr.deleteFrom(Employee).where()
            assertThat(tr.select(Employee).asList()).isEmpty()
        }
    }

}
