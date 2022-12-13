package com.dbobjekts.integration

import com.dbobjekts.fixture.h2.H2DB
import com.dbobjekts.integration.h2.core.Employee
import com.dbobjekts.integration.h2.hr.Hobby
import com.dbobjekts.statement.select.SelectStatementExecutor
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class UpdateIntegrationTest {

    val e = Employee
    val h = Hobby

    companion object {
        @BeforeAll
        @JvmStatic
        fun beforeGroup() {
            H2DB.setupDatabaseObjects()
        }
    }


    @Test
    @Order(1)
    fun `verify ranges`() {
        fun insert(name: String, salary: Int) {
            val id = H2DB.newTransaction {
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
        assertEquals(7, H2DB.newTransaction { it.select(e.salary).where(e.salary.notIn(31.0, 33.0, 35.0)).asList().size })
        assertEquals(3, H2DB.newTransaction { it.select(e.salary).where(e.salary.within(31.0, 33.0, 35.0)).asList().size })
        assertEquals(4, H2DB.newTransaction { it.select(e.salary).where(e.salary.gt(35.0)).asList().size })
        assertEquals(5, H2DB.newTransaction { it.select(e.salary).where(e.salary.ge(35.0)).asList().size })
        assertEquals(4, H2DB.newTransaction { it.select(e.salary).where(e.salary.lt(34.0)).asList().size })
        assertEquals(5, H2DB.newTransaction { it.select(e.salary).where(e.salary.le(34.0)).asList().size })
        assertEquals(2, H2DB.newTransaction { it.select(e.salary).where(e.salary.ge(33.0).and(e.salary).le(34.0)).asList().size })
        assertEquals(3, H2DB.newTransaction { it.select(e.salary).where(e.salary.within(31.0, 33.0, 35.0)).asList().size })

        assertEquals(39.0, H2DB.newTransaction { it.select(e.salary).orderDesc(e.salary).noWhereClause().first() })
        assertEquals(30.0, H2DB.newTransaction { it.select(e.salary).orderAsc(e.salary).noWhereClause().first() })
        assertEquals(36.0, H2DB.newTransaction { it.select(e.salary).orderAsc(e.name).noWhereClause().first() })
        assertEquals(39.0, H2DB.newTransaction { it.select(e.salary).orderDesc(e.name).noWhereClause().first() })

        insert("Oliver", 31)
        insert("Karl", 30)
        val entities = H2DB.newTransaction { it.select(e.name).orderAsc(e.salary).orderDesc(e.name).noWhereClause().asList() }
        assertEquals("Karl", entities[0])
        assertEquals("Charlie", entities[1])
        assertEquals("Oliver", entities[2])
        assertEquals("Gina", entities[3])
        val l2 = H2DB.newTransaction { it.select(e.name).where(e.salary.le(31.0)).orderDesc(e.salary).orderAsc(e.name).asList() }
        assertEquals("Gina", l2[0])
        assertEquals("Oliver", l2[1])
        assertEquals("Charlie", l2[2])
        assertEquals("Karl", l2[3])
    }

    @Test
    @Order(2)
    fun `verify null and default values`() {
        H2DB.newTransaction { tr ->
            val id = tr.insert(e).mandatoryColumns("Jack", 3000.5, LocalDate.of(1980, 1, 1)).execute()
            assertNull(tr.select(h.name.nullable).from(Employee.leftJoin(Hobby)).where(e.id.eq(id)).first())
            assertNull(tr.select(e.children).where(e.id.eq(id)).first())
        }
    }

    @Test
    @Order(2)
    fun `update name for existing e`() {
        H2DB.newTransaction { tr ->
            tr.update(e).name("Janet").where(e.name.eq("Bob")).execute()
            assertEquals("Janet", tr.select(e.name).where(e.name.eq("Janet")).first())
        }
    }

    @Test
    @Order(3)
    fun `update date of birth for existing e`() {
        H2DB.newTransaction { tr ->
            tr.update(e).dateOfBirth(LocalDate.of(1970, 10, 10)).where(e.name.eq("Janet")).execute()
            assertEquals("1970-10-10", tr.select(e.dateOfBirth).where(e.name.eq("Janet")).first().toString())
        }
    }

    @Test
    @Order(4)
    fun `update salary for existing e`() {
        H2DB.newTransaction { tr ->
            tr.update(e).salary(3300.50).where(e.name.eq("Janet")).execute()
            assertEquals(3300.50, tr.select(e.salary).where(e.name.eq("Janet")).first())
        }
    }

    @Test
    @Order(5)
    fun `update marital status for existing e`() {
        H2DB.newTransaction { tr ->
            tr.update(e)
            tr.update(e).married(false).where(e.name.eq("Janet")).execute()
            assertFalse(tr.select(e.married).where(e.name.eq("Janet")).first() ?: false)
        }
    }

    @Test
    @Order(6)
    fun `update number of children to two`() {
        H2DB.newTransaction { tr ->
            tr.update(e).children(2).where(e.name.eq("Janet")).execute()
            assertEquals(2, tr.select(e.children).where(e.name.eq("Janet")).first())
        }
    }

    @Test
    @Order(7)
    fun `update number of children to zero`() {
        H2DB.newTransaction { tr ->
            tr.update(e).children(0).where(e.name.eq("Janet")).execute()
            assertEquals(0, tr.select(e.children).where(e.name.eq("Janet")).first())
        }
    }

    @Test
    @Order(8)
    fun `update number of children to null`() {
        H2DB.newTransaction { tr ->
            tr.update(e).children(null).where(e.name.eq("Janet")).execute()
            assertNull(tr.select(e.children).where(e.name.eq("Janet")).first())
        }
    }

    @Test
    @Order(9)
    fun `update hobbies to curling`() {
        H2DB.newTransaction { tr ->
            tr.insert(h).id("c").name("curling").execute()
            tr.update(e).hobbyId("c").where(e.name.eq("Janet")).execute()
            assertEquals("curling", tr.select(h.name).where(e.name.eq("Janet")).first())
        }
    }

    @Test
    @Order(10)
    fun `update hobbies to None`() {
        H2DB.newTransaction { tr ->
            tr.update(e).hobbyId(null).where(e.name.eq("Janet")).execute()

            assertEquals(0, tr.select(h.name).where(e.name.eq("Janet")).asList().size)
        }
    }

    @Test
    @Order(11)
    fun `test LIKE conditions on strings`() {
        H2DB.newTransaction { tr ->
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
        val result = H2DB.newTransaction { s ->
            s.deleteFrom(Employee).where(Employee.id.eq(5L).and(Employee.name).notIn("bob", "alice", "eve"))
        }
    }

    @Test
    @Order(13)
    fun `setting whereclause parameters`() {
        val ok = H2DB.newTransaction {
            it.deleteFrom(Employee)
                .where(Employee.name.notIn("bob", "alice", "eve").and(Employee.married).ne(true).or(Employee.salary.gt(300.50)))
        }
    }

    @Test
    @Order(14)
    fun `delete row`() {
        H2DB.newTransaction { it.deleteFrom(Employee).where(Employee.id.eq(42)) }
    }

    @Test
    @Order(15)
    fun `delete without whereclause`() {
        H2DB.newTransaction { s ->
            s.deleteFrom(Employee).noWhereClause()
        }
    }
}
