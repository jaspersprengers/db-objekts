package com.dbobjekts.mysql

import com.dbobjekts.api.TransactionManager
import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.metadata.column.BlobColumn
import com.dbobjekts.metadata.column.NumberAsBooleanColumn
import com.dbobjekts.mysql.testdb.CatalogDefinition
import com.dbobjekts.mysql.testdb.core.*
import com.dbobjekts.mysql.testdb.hr.Certificate
import com.dbobjekts.mysql.testdb.hr.Hobby
import com.dbobjekts.statement.select.SelectStatementExecutor
import com.dbobjekts.util.HikariDataSourceFactory
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal
import java.nio.file.Paths
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import javax.sql.DataSource

@Testcontainers
class MysqlIntegrationTest {

    companion object {

        @Container
        val container: MysqlWrapper = MysqlWrapper("8.0", listOf("acme.sql", "classicmodels.sql"))
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
            .basePackageForSources("com.dbobjekts.mysql.testdb")
        .outputDirectoryForGeneratedSources(Paths.get("src/generated-sources/kotlin").toAbsolutePath().toString())
        generator.validateCatalog(CatalogDefinition).assertNoDifferences()
        generator.generateSourceFiles()
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
                .blobCol(BlobColumn.ofString("blob"))

        }
        assertThat(String(retrieved.binaryCol!!)).startsWith("hello")
        assertThat(retrieved.blobCol).isEqualTo(BlobColumn.ofString("blob"))
        assertThat(retrieved.charByteCol).isEqualTo("1".encodeToByteArray())
    }

    @Test
    fun test_text_columns() {
        val json = "[{\"hello\": 42}]"
        val uuid = UUID.randomUUID()
        val retrieved = insertAndRetrieve {
            it.charCol("hello")
                .enumCol("MAYBE")
                .setCol("chess")
                .jsonCol(json)
                .varcharCol("hello")
                .textCol("The quick brown fox")

        }
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
        val e = Employee
        val h = Hobby
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
        assertThat(tm { it.select(e.salary).where(e.salary.notIn(31.0, 33.0, 35.0)).asList().size }).isEqualTo(7)
        assertThat( tm { it.select(e.salary).where(e.salary.within(31.0, 33.0, 35.0)).asList().size }).isEqualTo(3)
        assertThat(tm { it.select(e.salary).where(e.salary.gt(35.0)).asList().size }).isEqualTo(4)
        assertThat(tm { it.select(e.salary).where(e.salary.ge(35.0)).asList().size }).isEqualTo(5)
        assertThat(tm { it.select(e.salary).where(e.salary.lt(34.0)).asList().size }).isEqualTo(4)
        assertThat(tm { it.select(e.salary).where(e.salary.le(34.0)).asList().size }).isEqualTo(5)
        assertThat(tm { it.select(e.salary).where(e.salary.ge(33.0).and(e.salary).le(34.0)).asList().size }).isEqualTo(2)
        assertThat(tm { it.select(e.salary).where(e.salary.within(31.0, 33.0, 35.0)).asList().size }).isEqualTo(3)

        assertThat(tm { it.select(e.salary).orderDesc(e.salary).first() }).isEqualTo(39.0)
        assertThat( tm { it.select(e.salary).orderAsc(e.salary).first() }).isEqualTo(30.0)
        assertThat( tm { it.select(e.salary).orderAsc(e.name).first() }).isEqualTo(36.0)
        assertThat( tm { it.select(e.salary).orderDesc(e.name).first() }).isEqualTo(39.0)

        insert("Oliver", 31)
        insert("Karl", 30)
        val entities = tm { it.select(e.name).orderAsc(e.salary).orderDesc(e.name).asList() }
        assertThat(entities[0]).isEqualTo("Karl")
        assertThat(entities[1]).isEqualTo("Charlie")
        assertThat(entities[2]).isEqualTo("Oliver")
        assertThat(entities[3]).isEqualTo("Gina")
        val l2 = tm {
            it.select(e.name).where(e.salary.le(31.0)).orderDesc(e.salary).orderAsc(e.name).asList()
        }
        assertThat(l2[0]).isEqualTo("Gina")
        assertThat(l2[1]).isEqualTo("Oliver")
        assertThat(l2[2]).isEqualTo("Charlie")
        assertThat(l2[3]).isEqualTo("Karl")

        tm { tr ->
            val id = tr.insert(e).mandatoryColumns("Jack", 3000.5, LocalDate.of(1980, 1, 1)).execute()
            Assertions.assertThat(
                tr.select(h.name.nullable)
                    .from(Employee.leftJoin(Hobby))
                    .where(e.id.eq(id)).first()
            )
            assertThat(tr.select(e.children).where(e.id.eq(id)).first()).isNull()
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
            tr.update(e).salary(3300.50).where(e.name.eq("Janet"))
            assertThat(tr.select(e.salary).where(e.name.eq("Janet")).first()).isEqualTo(3300.50)
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
            assertThat(tr.select(e.children).where(e.name.eq("Janet")).first()).isNull()
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
                tr.insert(e).name(name).dateOfBirth(dob).salary(3500.0).execute()
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


        tm { it.update(e).children(2).where(h.name.eq("curling")) }
        tm { it.deleteFrom(e.innerJoin(Hobby)).where(h.name.eq("curling")) }
    }


}
