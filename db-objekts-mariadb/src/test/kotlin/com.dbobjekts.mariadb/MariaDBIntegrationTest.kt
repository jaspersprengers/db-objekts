package com.dbobjekts.integration.mariadb

import com.dbobjekts.api.TransactionManager
import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.mariadb.testdb.CatalogDefinition
import com.dbobjekts.mariadb.testdb.core.*
import com.dbobjekts.mariadb.testdb.hr.Certificate
import com.dbobjekts.mariadb.testdb.hr.Hobby
import com.dbobjekts.metadata.column.NumberAsBooleanColumn
import com.dbobjekts.statement.select.SelectStatementExecutor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.nio.file.Paths
import java.time.LocalDate
import javax.sql.DataSource

@Testcontainers
class MariaDBIntegrationTest {

    val e = Employee
    val h = Hobby

    companion object {

        @Container
        val container: MariaDBWrapper = MariaDBWrapper("10.10", listOf("acme.sql"))
        lateinit var dataSource: DataSource
        lateinit var tm: TransactionManager

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            dataSource = container.createDataSource()
            tm = TransactionManager.builder().withDataSource(dataSource).withCatalog(CatalogDefinition).build()
        }

    }

    @Test
    fun validateCodeGeneration() {
        val gen = CodeGenerator()
        gen.withDataSource(dataSource)
        gen.mappingConfigurer().setColumnTypeForJDBCType("TINYINT", NumberAsBooleanColumn::class.java)
        gen.outputConfigurer()
            .basePackageForSources("com.dbobjekts.mariadb.testdb")
            //.outputDirectoryForGeneratedSources(Paths.get("db-objekts-mariadb/src/generated-sources/kotlin").toAbsolutePath().toString())
        val diff = gen.differencesWithCatalog(CatalogDefinition)
        Assertions.assertTrue(diff.isEmpty())
        //gen.generateSourceFiles()
    }

    @Test
    fun `date and time handling`() {
        tm {
            //  it.select(AllTypesNil.)
        }
    }

    @Test
    fun `selection and updates`() {
        tm { tr ->
            tr.deleteFrom(EmployeeAddress).where()
            tr.deleteFrom(EmployeeDepartment).where()
            tr.deleteFrom(Department).where()
            tr.deleteFrom(Address).where()
            tr.deleteFrom(Certificate).where()
            tr.deleteFrom(Employee).where()
            tr.deleteFrom(Country).where()
            tr.deleteFrom(Hobby).where()
            //tr.deleteFrom(Shape).where()
        }

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
        assertEquals(
            7,
            tm { it.select(e.salary).where(e.salary.notIn(31.0, 33.0, 35.0)).asList().size })
        assertEquals(
            3,
            tm { it.select(e.salary).where(e.salary.within(31.0, 33.0, 35.0)).asList().size })
        assertEquals(
            4,
            tm { it.select(e.salary).where(e.salary.gt(35.0)).asList().size })
        assertEquals(
            5,
            tm { it.select(e.salary).where(e.salary.ge(35.0)).asList().size })
        assertEquals(
            4,
            tm { it.select(e.salary).where(e.salary.lt(34.0)).asList().size })
        assertEquals(
            5,
            tm { it.select(e.salary).where(e.salary.le(34.0)).asList().size })
        assertEquals(
            2,
            tm { it.select(e.salary).where(e.salary.ge(33.0).and(e.salary).le(34.0)).asList().size })
        assertEquals(
            3,
            tm { it.select(e.salary).where(e.salary.within(31.0, 33.0, 35.0)).asList().size })

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
        val l2 = tm {
            it.select(e.name).where(e.salary.le(31.0)).orderDesc(e.salary).orderAsc(e.name).asList()
        }
        assertEquals("Gina", l2[0])
        assertEquals("Oliver", l2[1])
        assertEquals("Charlie", l2[2])
        assertEquals("Karl", l2[3])

        tm { tr ->
            val id = tr.insert(e).mandatoryColumns("Jack", 3000.5, true, LocalDate.of(1980, 1, 1)).execute()
            Assertions.assertNull(
                tr.select(h.name.nullable)
                    .from(Employee.leftJoin(Hobby))
                    .where(e.id.eq(id)).first()
            )
            Assertions.assertNull(tr.select(e.children).where(e.id.eq(id)).first())
        }

        tm { tr ->
            tr.update(e).name("Janet").where(e.name.eq("Bob"))
            assertEquals("Janet", tr.select(e.name).where(e.name.eq("Janet")).first())
        }

        tm { tr ->
            tr.update(e).dateOfBirth(LocalDate.of(1970, 10, 10)).where(e.name.eq("Janet"))
            assertEquals(
                "1970-10-10",
                tr.select(e.dateOfBirth).where(e.name.eq("Janet")).first().toString()
            )
        }

        tm { tr ->
            tr.update(e).salary(3300.50).where(e.name.eq("Janet"))
            assertEquals(
                3300.50,
                tr.select(e.salary).where(e.name.eq("Janet")).first()
            )
        }

        tm { tr ->
            tr.update(e).married(false).where(e.name.eq("Janet"))
            Assertions.assertFalse(
                tr.select(e.married).where(e.name.eq("Janet")).first() ?: false
            )
        }

        tm { tr ->
            tr.update(e).children(2).where(e.name.eq("Janet"))
            assertEquals(2, tr.select(e.children).where(e.name.eq("Janet")).first())
        }

        tm { tr ->
            tr.update(e).children(0).where(e.name.eq("Janet"))
            assertEquals(0, tr.select(e.children).where(e.name.eq("Janet")).first())
        }

        tm { tr ->
            tr.update(e).children(null).where(e.name.eq("Janet"))
            Assertions.assertNull(tr.select(e.children).where(e.name.eq("Janet")).first())
        }

        tm { tr ->
            tr.insert(h).id("c").name("curling").execute()
            tr.update(e).hobbyId("c").where(e.name.eq("Janet"))
            assertEquals(
                "curling",
                tr.select(h.name).where(e.name.eq("Janet")).first()
            )
        }

        tm { tr ->
            tr.update(e).hobbyId(null).where(e.name.eq("Janet"))

            assertEquals(0, tr.select(h.name).where(e.name.eq("Janet")).asList().size)
        }

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


        tm { it.update(e).children(2).where(h.name.eq("curling")) }
        tm { it.deleteFrom(e.innerJoin(Hobby)).where(h.name.eq("curling")) }


    }


}
