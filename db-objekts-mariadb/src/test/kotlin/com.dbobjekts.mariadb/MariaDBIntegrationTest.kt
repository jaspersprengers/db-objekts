package com.dbobjekts.integration.mariadb

import com.dbobjekts.api.PathsUtil
import com.dbobjekts.api.TransactionManager
import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.mariadb.testdb.Aliases
import com.dbobjekts.mariadb.testdb.CatalogDefinition
import com.dbobjekts.mariadb.testdb.core.*
import com.dbobjekts.mariadb.testdb.hr.Certificate
import com.dbobjekts.mariadb.testdb.hr.Hobby
import com.dbobjekts.metadata.column.NumberAsBooleanColumn
import com.dbobjekts.statement.select.SelectStatementExecutor
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal
import java.nio.file.Paths
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.sql.DataSource
import com.dbobjekts.mariadb.testdb.HasAliases
import com.dbobjekts.mariadb.testdb.nation.Continents
import com.dbobjekts.mariadb.testdb.nation.Countries
import com.dbobjekts.mariadb.testdb.nation.CountryStats
import com.dbobjekts.mariadb.testdb.nation.Regions
import com.dbobjekts.metadata.column.Aggregate
import java.util.*

@Testcontainers
class MariaDBIntegrationTest : HasAliases by Aliases {

    companion object {

        @Container
        val container: MariaDBWrapper = MariaDBWrapper("10.10", listOf("acme.sql", "nation.sql"))
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
        generator.configureColumnTypeMapping().setColumnTypeForJDBCType("TINYINT", NumberAsBooleanColumn::class.java)
        generator.configureOutput()
            .basePackageForSources("com.dbobjekts.mariadb.testdb")
            .outputDirectoryForGeneratedSources(PathsUtil.getGeneratedSourcesDirectory())
        generator.validateCatalog(CatalogDefinition).assertNoDifferences()
        generator.generateSourceFiles()
    }

   @Test
    fun `get total population per continent`() {
        tm { tr ->
            val pairs = tr.select(Continents.name, CountryStats.year, CountryStats.population.sum())
                .from(CountryStats.innerJoin(Countries).innerJoin(Regions).innerJoin(Continents))
                .orderAsc(CountryStats.year, CountryStats.population)
                .where(CountryStats.year.gt(2000))
                .having(Aggregate.gt(800_000_000))
                .asList()
            val (name, year, number) = pairs[0]
            assertThat(name).isEqualTo("Africa")
            assertThat(number).isEqualTo(814022223)
        }
    }

    private fun insertAndRetrieve(fct: (AllTypesNilInsertBuilder) -> Unit): AllTypesNilRow {
        return tm {
            val builder = it.insert(AllTypesNil)
            fct(builder)
            val ret = builder.execute()
            it.select(AllTypesNil).where(AllTypesNil.id.eq(ret)).first()
        }
    }

    @Test
    fun test_numericCol() {
        val threeMillion = BigDecimal.valueOf(3_000_000)
        val retrieved = insertAndRetrieve {
            it.decimalCol(threeMillion)
                .decCol(threeMillion)
                .numericCol(threeMillion)
                .fixedCol(threeMillion)
                .intCol(200L)
                .int1Col(true)
                .bitCol(true)
                .int2Col(200)
                .int3Col(200_000)
                .int4Col(200_000L)
                .tinyintCol(true)
                .smallintCol(100)
                .bigintCol(1234567L)
                .int8Col(1234567L)
                .floatCol(12345.123f)
                .doubleCol(12345.12345)
                .doublePrecisionCol(23456.23456)
        }
        assertThat(retrieved.decimalCol).isEqualTo(threeMillion)
        assertThat(retrieved.decCol).isEqualTo(threeMillion)
        assertThat(retrieved.numericCol).isEqualTo(threeMillion)
        assertThat(retrieved.fixedCol).isEqualTo(threeMillion)
        assertThat(retrieved.intCol).isEqualTo(200L)
        assertThat(retrieved.int1Col).isTrue()
        assertThat(retrieved.tinyintCol).isTrue()
        assertThat(retrieved.bitCol).isTrue()
        assertThat(retrieved.smallintCol).isEqualTo(100)
        assertThat(retrieved.int2Col).isEqualTo(200)
        assertThat(retrieved.int3Col).isEqualTo(200_000)
        assertThat(retrieved.int4Col).isEqualTo(200_000)
        assertThat(retrieved.bigintCol).isEqualTo(1234567L)
        assertThat(retrieved.int8Col).isEqualTo(1234567L)
        assertThat(retrieved.floatCol).isEqualTo(12345.1f)
        assertThat(retrieved.doubleCol).isEqualTo(12345.12345)
        assertThat(retrieved.doublePrecisionCol).isEqualTo(23456.23456)

    }



    @Test
    fun test_binaryCol() {
        val value = "hello".encodeToByteArray()
        val retrieved = insertAndRetrieve {
            it.binaryCol(value)
                .charByteCol("1".encodeToByteArray())
                .blobCol(value)

        }
        assertThat(String(retrieved.binaryCol!!)).startsWith("hello")
        assertThat(String(retrieved.blobCol!!)).startsWith("hello")
        assertThat(retrieved.charByteCol).isEqualTo("1".encodeToByteArray())
    }

    @Test
    fun test_text_columns() {
        val json = "[{\"hello\": 42}]"
        val uuid = UUID.randomUUID()

        val retrieved = insertAndRetrieve {
            it.charCol("hello")
                .enumCol("MAYBE")
                .uuidCol(uuid)
                .setCol("chess")
                .jsonCol(json)
                .varcharCol("hello")
                .textCol("The quick brown fox")

        }
        assertThat(retrieved.uuidCol).isEqualTo(uuid)
        assertThat(retrieved.charCol).isEqualTo("hello")
        assertThat(retrieved.enumCol).isEqualTo("MAYBE")
        assertThat(retrieved.setCol).isEqualTo("chess")
        assertThat(retrieved.jsonCol).isEqualTo(json)
        assertThat(retrieved.textCol).isEqualTo("The quick brown fox")
        assertThat(retrieved.varcharCol).isEqualTo("hello")

    }

    @Test
    fun test_date_columns() {
        val date_1980 = LocalDate.of(1980, 10, 10)
        val time = LocalTime.of(15, 30)
        val dateTime = LocalDateTime.of(1980, 10, 10, 14, 30)
        val retrieved = insertAndRetrieve {
            it.dateCol(date_1980)
                .timeCol(time)
                .datetimeCol(dateTime)
                .timestampCol(dateTime)
                .yearCol(1990)

        }
        assertThat(retrieved.dateCol).isEqualTo(date_1980)
        assertThat(retrieved.timeCol).isEqualTo(time)
        assertThat(retrieved.datetimeCol).isEqualTo(dateTime)
        assertThat(retrieved.timestampCol).isEqualTo(dateTime)
        assertThat(retrieved.yearCol).isEqualTo(1990)

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
        }

        fun insert(name: String, salary: Int) {
            val id = tm {
                it.insert(em).name(name).salary(salary.toDouble()).dateOfBirth(LocalDate.of(1980, 3, 3)).married(true).execute()
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
        assertThat(tm { it.select(em.salary).where(em.salary.notIn(31.0, 33.0, 35.0)).asList().size }).isEqualTo(7)
        assertThat(tm { it.select(em.salary).where(em.salary.within(31.0, 33.0, 35.0)).asList().size }).isEqualTo(3)
        assertThat(tm { it.select(em.salary).where(em.salary.gt(35.0)).asList().size }).isEqualTo(4)
        assertThat(tm { it.select(em.salary).where(em.salary.ge(35.0)).asList().size }).isEqualTo(5)
        assertThat(tm { it.select(em.salary).where(em.salary.lt(34.0)).asList().size }).isEqualTo(4)
        assertThat(tm { it.select(em.salary).where(em.salary.le(34.0)).asList().size }).isEqualTo(5)
        assertThat(tm { it.select(em.salary).where(em.salary.ge(33.0).and(em.salary).le(34.0)).asList().size }).isEqualTo(2)
        assertThat(tm { it.select(em.salary).where(em.salary.within(31.0, 33.0, 35.0)).asList().size }).isEqualTo(3)

        assertThat(tm { it.select(em.salary).orderDesc(em.salary).first() }).isEqualTo(39.0)
        assertThat(tm { it.select(em.salary).orderAsc(em.salary).first() }).isEqualTo(30.0)
        assertThat(tm { it.select(em.salary).orderAsc(em.name).first() }).isEqualTo(36.0)
        assertThat(tm { it.select(em.salary).orderDesc(em.name).first() }).isEqualTo(39.0)

        insert("Oliver", 31)
        insert("Karl", 30)
        val entities = tm { it.select(em.name).orderAsc(em.salary).orderDesc(em.name).asList() }
        assertThat(entities[0]).isEqualTo("Karl")
        assertThat(entities[1]).isEqualTo("Charlie")
        assertThat(entities[2]).isEqualTo("Oliver")
        assertThat(entities[3]).isEqualTo("Gina")
        val l2 = tm {
            it.select(em.name).where(em.salary.le(31.0)).orderDesc(em.salary).orderAsc(em.name).asList()
        }
        assertThat(l2[0]).isEqualTo("Gina")
        assertThat(l2[1]).isEqualTo("Oliver")
        assertThat(l2[2]).isEqualTo("Charlie")
        assertThat(l2[3]).isEqualTo("Karl")

   /*     tm { tr ->
            val id = tr.insert(e).mandatoryColumns("Jack", 3000.5, true, LocalDate.of(1980, 1, 1)).execute()
            Assertions.assertThat(
                tr.select(h.name.nullable)
                    .from(Employee.leftJoin(Hobby))
                    .where(em.id.eq(id)).first()
            )
            assertThat(tr.select(e.children).where(em.id.eq(id)).first()).isNull()
        }
*/
        tm { tr ->
            tr.update(em).name("Janet").where(em.name.eq("Bob"))
            assertThat(tr.select(em.name).where(em.name.eq("Janet")).first()).isEqualTo("Janet")
        }

        tm { tr ->
            tr.update(em).dateOfBirth(LocalDate.of(1970, 10, 10)).where(em.name.eq("Janet"))
            assertThat(tr.select(em.dateOfBirth).where(em.name.eq("Janet")).first().toString()).isEqualTo("1970-10-10")
        }

        tm { tr ->
            tr.update(em).salary(3300.50).where(em.name.eq("Janet"))
            assertThat(tr.select(em.salary).where(em.name.eq("Janet")).first()).isEqualTo(3300.50)
        }

        tm { tr ->
            tr.update(em).married(false).where(em.name.eq("Janet"))
            assertThat(tr.select(em.married).where(em.name.eq("Janet")).first() ?: false).isFalse()
        }

        tm { tr ->
            tr.update(em).children(2).where(em.name.eq("Janet"))
            assertThat(tr.select(em.children).where(em.name.eq("Janet")).first()).isEqualTo(2)
        }

        tm { tr ->
            tr.update(em).children(0).where(em.name.eq("Janet"))
            assertThat(tr.select(em.children).where(em.name.eq("Janet")).first()).isEqualTo(0)
        }

        tm { tr ->
            tr.update(em).children(null).where(em.name.eq("Janet"))
            assertThat(tr.select(em.children).where(em.name.eq("Janet")).first()).isNull()
        }

        tm { tr ->
            tr.insert(ho).id("c").name("curling").execute()
            tr.update(em).hobbyId("c").where(em.name.eq("Janet"))
            assertThat(tr.select(ho.name).where(em.name.eq("Janet")).first()).isEqualTo("curling")
        }

        tm { tr ->
            tr.update(em).hobbyId(null).where(em.name.eq("Janet"))
            assertThat(tr.select(ho.name).where(em.name.eq("Janet")).asList()).isEmpty()
        }

        tm { tr ->
            fun insert(name: String) {
                val dob = LocalDate.of(1980, 1, 1)
                tr.insert(em).name(name).dateOfBirth(dob).salary(3500.0).execute()
            }

            fun checkCount(result: SelectStatementExecutor<*, *>, size: Int) {
                assertThat(result.asList().size).isEqualTo(size)
            }
            insert("Arthur Philip Dent")
            insert("Arthur Matthew Dent")
            insert("Arthur Matthew Holmes")

            checkCount(tr.select(em.id).where(em.name.startsWith("Arthur")), 3)
            checkCount(tr.select(em.id).where(em.name.startsWith("Arthur Matthew")), 2)
            checkCount(tr.select(em.id).where(em.name.startsWith("Arthur Matthew Dent")), 1)

            checkCount(tr.select(em.id).where(em.name.contains("Dent")), 2)
            checkCount(tr.select(em.id).where(em.name.contains("Matthew")), 2)
            checkCount(tr.select(em.id).where(em.name.contains("Arthur Matthew")), 2)
            checkCount(tr.select(em.id).where(em.name.contains("Arthur Matthew Dent")), 1)
            checkCount(tr.select(em.id).where(em.name.contains("Arthur Philip Holmes")), 0)

            checkCount(tr.select(em.id).where(em.name.endsWith("Dent")), 2)
            checkCount(tr.select(em.id).where(em.name.endsWith("Holmes")), 1)
            checkCount(tr.select(em.id).where(em.name.endsWith("Arthur Matthew Dent")), 1)
        }
        //composite foreign keys are not supported
       tm {
            it.insert(Composite).mandatoryColumns("ISBN", "The Shining").published(LocalDate.now()).execute()
            it.insert(CompositeForeignKey).mandatoryColumns("ISBN", "The Shining").message("Hello world!").execute()
            Assertions.assertThatThrownBy {
                it.select(Composite.published, CompositeForeignKey.message).where(Composite.isbn.eq("ISBN")).first()
            }
        }

        tm { it.update(em).children(2).where(ho.name.eq("curling")) }
        tm { it.deleteFrom(em.innerJoin(Hobby)).where(ho.name.eq("curling")) }
    }


}
