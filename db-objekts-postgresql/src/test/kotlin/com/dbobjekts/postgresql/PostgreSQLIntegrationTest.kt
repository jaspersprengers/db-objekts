package com.dbobjekts.postgresql

import com.dbobjekts.api.PathsUtil
import com.dbobjekts.api.TransactionManager
import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.metadata.column.VarcharColumn
import com.dbobjekts.postgresql.testdb.CatalogDefinition
import com.dbobjekts.postgresql.testdb.core.AllTypesNil
import com.dbobjekts.postgresql.testdb.core.AllTypesNilInsertBuilder
import com.dbobjekts.postgresql.testdb.core.AllTypesNilRow
import com.dbobjekts.postgresql.testdb.core.Employee
import com.dbobjekts.postgresql.testdb.hr.Hobby
import com.dbobjekts.statement.select.SelectStatementExecutor
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.postgresql.util.PGInterval
import org.postgresql.util.PGmoney
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.nio.file.Paths
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.sql.DataSource

@Testcontainers
class PostgreSQLIntegrationTest {

    companion object {

        val at = AllTypesNil

        @Container
        val container: PostgreSQLWrapper = PostgreSQLWrapper("15.1", listOf("acme.sql"))
        lateinit var dataSource: DataSource
        lateinit var tm: TransactionManager

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            dataSource = container.createDataSource()
            tm = TransactionManager.builder()
                .withDataSource(dataSource)
                .withCatalog(CatalogDefinition)
                .build()
        }
    }

    @Test
    fun validateCodeGeneration() {
        val generator = CodeGenerator()
        generator.withDataSource(dataSource)
        generator.configureColumnTypeMapping().setColumnTypeForJDBCType("mood", VarcharColumn::class.java)
        generator.configurePrimaryKeySequences().setSequenceNameForPrimaryKey("core","EMPLOYEE", "id", "EMPLOYEE_SEQ")
        generator.configureOutput()
            .basePackageForSources("com.dbobjekts.postgresql.testdb")
            .outputDirectoryForGeneratedSources(PathsUtil.getGeneratedSourcesDirectory())
        generator.validateCatalog(CatalogDefinition).assertNoDifferences()
        generator.generateSourceFiles()
    }

    private fun insertAndRetrieve(fct: (AllTypesNilInsertBuilder) -> Unit): AllTypesNilRow {
        return tm {
            val builder = it.insert(AllTypesNil)
            fct(builder)
            val ret = builder.execute()
            it.select(at).where(at.id.eq(ret.toInt())).first()
        }
    }

    @Test
    fun test_custom_types() {
        val retrieved = insertAndRetrieve {
            it.moneyCol(PGmoney(300.50))
                .internvalYearCol(PGInterval("3 years"))
        }
        assertThat(retrieved.moneyCol?.`val`).isEqualTo(300.50)
        assertThat(retrieved.internvalYearCol?.years).isEqualTo(3)
    }

    @Test
    fun test_numerics() {
        val retrieved = insertAndRetrieve {
            it.int8Col(1000L)
                .int2Col(300)
                .int4Col(200)
                .float8Col(200.0)
        }
        assertThat(retrieved.int8Col).isEqualTo(1000L)
        assertThat(retrieved.int2Col).isEqualTo(300)
        assertThat(retrieved.int4Col).isEqualTo(200)
        assertThat(retrieved.float8Col).isEqualTo(200.0)
    }

    @Test
    fun test_boolean_types() {
        val retrieved = insertAndRetrieve {
            it.booleanCol(true)
                .boolCol(true)
        }
        assertThat(retrieved.booleanCol).isTrue()
        assertThat(retrieved.boolCol).isTrue()
    }

   /* @Test
    fun `bit varying`() {
        tm {
            //val arr = "hello".toByteArray()
            val retrieved = insertAndRetrieve {
                it.byteaCol(arr).bitVaryingCol(arr)

            }
            assertThat(retrieved.byteaCol).isEqualTo(arr)
            assertThat(retrieved.bitVaryingCol).isEqualTo(arr)
        }
    }*/

    @Test
    fun test_date_and_time() {
        val localDateTime = LocalDateTime.of(2023, 1, 3, 12, 10, 10)
        val now = ZonedDateTime.of(localDateTime, ZoneId.of("Europe/Paris")).toOffsetDateTime()
        val instant = now.toInstant()
        val today = now.toLocalDate()
        val time = now.toLocalTime()
        val retrieved = insertAndRetrieve {
            it.dateCol(today)
                .timeTzCol(time)
                .timeWithTzCol(time)
                .timeWithoutTzCol(time)
                .timestampTzCol(now)
                .timestampWithTzCol(now)
                .timestampWihtoutTzCol(instant)
        }
        assertThat(retrieved.dateCol).isEqualTo(today)
        assertThat(retrieved.timeTzCol).isEqualTo(time)
        assertThat(retrieved.timeWithTzCol).isEqualTo(time)
        assertThat(retrieved.timeWithoutTzCol).isEqualTo(time)
        assertThat(retrieved.timestampTzCol).isEqualTo(now)
        assertThat(retrieved.timestampWithTzCol).isEqualTo(now)
        assertThat(retrieved.timestampWihtoutTzCol).isEqualTo(instant)
    }

    @Test
    fun `selection and updates`() {
        val e = Employee
        val h = Hobby

        fun insert(name: String, salary: Int) {
            val id = tm {
                it.insert(e).name(name).salary(salary.toFloat()).dateOfBirth(LocalDate.of(1980, 3, 3)).married(true).execute()
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
        assertThat(tm { it.select(e.salary).where(e.salary.notIn(31.0f, 33.0f, 35.0f)).asList().size }).isEqualTo(7)
        assertThat(tm { it.select(e.salary).where(e.salary.within(31.0f, 33.0f, 35.0f)).asList().size }).isEqualTo(3)
        assertThat(tm { it.select(e.salary).where(e.salary.gt(35.0f)).asList().size }).isEqualTo(4)
        assertThat(tm { it.select(e.salary).where(e.salary.ge(35.0f)).asList().size }).isEqualTo(5)
        assertThat(tm { it.select(e.salary).where(e.salary.lt(34.0f)).asList().size }).isEqualTo(4)
        assertThat(tm { it.select(e.salary).where(e.salary.le(34.0f)).asList().size }).isEqualTo(5)
        assertThat(tm { it.select(e.salary).where(e.salary.ge(33.0f).and(e.salary).le(34.0f)).asList().size }).isEqualTo(2)
        assertThat(tm { it.select(e.salary).where(e.salary.within(31.0f, 33.0f, 35.0f)).asList().size }).isEqualTo(3)

        assertThat(tm { it.select(e.salary).orderDesc(e.salary).first() }).isEqualTo(39.0f)
        assertThat(tm { it.select(e.salary).orderAsc(e.salary).first() }).isEqualTo(30.0f)
        assertThat(tm { it.select(e.salary).orderAsc(e.name).first() }).isEqualTo(36.0f)
        assertThat(tm { it.select(e.salary).orderDesc(e.name).first() }).isEqualTo(39.0f)

        insert("Oliver", 31)
        insert("Karl", 30)
        val entities = tm { it.select(e.name).orderAsc(e.salary).orderDesc(e.name).asList() }
        assertThat(entities[0]).isEqualTo("Karl")
        assertThat(entities[1]).isEqualTo("Charlie")
        assertThat(entities[2]).isEqualTo("Oliver")
        assertThat(entities[3]).isEqualTo("Gina")
        val l2 = tm {
            it.select(e.name).where(e.salary.le(31.0f)).orderDesc(e.salary).orderAsc(e.name).asList()
        }
        assertThat(l2[0]).isEqualTo("Gina")
        assertThat(l2[1]).isEqualTo("Oliver")
        assertThat(l2[2]).isEqualTo("Charlie")
        assertThat(l2[3]).isEqualTo("Karl")

        tm { tr ->
            val id = tr.insert(e).mandatoryColumns("Jack", 3000.5f, LocalDate.of(1980, 1, 1)).execute()
            Assertions.assertThat(
                tr.select(h.name.nullable)
                    .from(Employee.leftJoin(Hobby))
                    .where(e.id.eq(id)).firstOrNull()
            )
            assertThat(tr.select(e.children).where(e.id.eq(id)).firstOrNull()).isNull()
        }

        tm { tr ->
            tr.update(e).name("Janet").where(e.name.eq("Bob"))
            assertThat(tr.select(e.name).where(e.name.eq("Janet")).first()).isEqualTo("Janet")
        }

        tm { tr ->
            tr.update(e).dateOfBirth(LocalDate.of(1970, 10, 10)).where(e.name.eq("Janet"))
            assertThat(tr.select(e.dateOfBirth).where(e.name.eq("Janet")).first().toString()).isEqualTo("1970-10-10")
        }

        tm { tr ->
            tr.update(e).salary(3300.50f).where(e.name.eq("Janet"))
            assertThat(tr.select(e.salary).where(e.name.eq("Janet")).first()).isEqualTo(3300.50f)
        }

        tm { tr ->
            tr.update(e).married(false).where(e.name.eq("Janet"))
            assertThat(tr.select(e.married).where(e.name.eq("Janet")).first() ?: false).isFalse()
        }

        tm { tr ->
            tr.update(e).children(2).where(e.name.eq("Janet"))
            assertThat(tr.select(e.children).where(e.name.eq("Janet")).first()).isEqualTo(2)
        }

        tm { tr ->
            tr.update(e).children(0).where(e.name.eq("Janet"))
            assertThat(tr.select(e.children).where(e.name.eq("Janet")).first()).isEqualTo(0)
        }

        tm { tr ->
            tr.update(e).children(null).where(e.name.eq("Janet"))
            assertThat(tr.select(e.children).where(e.name.eq("Janet")).firstOrNull()).isNull()
        }

        tm { tr ->
            tr.insert(h).id("c").name("curling").execute()
            tr.update(e).hobbyId("c").where(e.name.eq("Janet"))
            assertThat(tr.select(h.name).where(e.name.eq("Janet")).first()).isEqualTo("curling")
        }

        tm { tr ->
            tr.update(e).hobbyId(null).where(e.name.eq("Janet"))
            assertThat(tr.select(h.name).where(e.name.eq("Janet")).asList()).isEmpty()
        }

        tm { tr ->
            fun insert(name: String) {
                val dob = LocalDate.of(1980, 1, 1)
                tr.insert(e).name(name).dateOfBirth(dob).salary(3500.0f).execute()
            }

            fun checkCount(result: SelectStatementExecutor<*, *>, size: Int) {
                assertThat(result.asList().size).isEqualTo(size)
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
}

