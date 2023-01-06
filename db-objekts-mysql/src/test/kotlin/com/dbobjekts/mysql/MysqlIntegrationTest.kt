package com.dbobjekts.mysql

import com.dbobjekts.api.TransactionManager
import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.metadata.column.NumberAsBooleanColumn
import com.dbobjekts.statement.select.SelectStatementExecutor
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.nio.file.Paths
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.sql.DataSource
import com.dbobjekts.util.HikariDataSourceFactory

//@Testcontainers
class MysqlIntegrationTest {

    companion object {

        //@Container
        val container: MysqlWrapper = MysqlWrapper("10.10", listOf("acme.sql"))
        lateinit var dataSource: DataSource
        lateinit var tm: TransactionManager

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            //dataSource = container.createDataSource()
            dataSource = HikariDataSourceFactory
                .create(
                    url = "jdbc:mysql://localhost:3306/test",
                    username = "root",
                    password = "test",
                    driver = "com.mysql.cj.jdbc.Driver"
                )
            tm = TransactionManager.builder()
                .withDataSource(dataSource)
               // .withCatalog(CatalogDefinition)
                .build()
        }
    }

    @Test
    fun validateCodeGeneration() {
        val gen = CodeGenerator()
        gen.withDataSource(dataSource)
        gen.configureColumnTypeMapping().setColumnTypeForJDBCType("TINYINT", NumberAsBooleanColumn::class.java)
        gen.configureOutput()
            .basePackageForSources("com.dbobjekts.mysql.testdb")
        .outputDirectoryForGeneratedSources(Paths.get("db-objekts-mysql/src/generated-sources/kotlin").toAbsolutePath().toString())
        //val diff = gen.differencesWithCatalog(CatalogDefinition)
        //assertThat(diff).isEmpty()
        gen.generateSourceFiles()
    }
/*
    @Test
    fun test_decimalCol() {
        tm {
            val value = BigDecimal.valueOf(345)
            val id = it.insert(AllTypesNil).decimalCol(value).execute()
            val retrieved = it.select(AllTypesNil.decimalCol).where(AllTypesNil.decimalCol.eq(value)).first()!!
            assertThat(retrieved).isEqualTo(value)
        }
    }


    @Test
    fun test_decCol() {
        tm {
            val value = BigDecimal.valueOf(345)
            it.insert(AllTypesNil).decCol(value).execute()
            val retrieved = it.select(AllTypesNil.decCol).where(AllTypesNil.decCol.eq(value)).first()!!
            assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_numericCol() {
        tm {
            val value = BigDecimal.valueOf(345)
            it.insert(AllTypesNil).numericCol(value).execute()
            val retrieved = it.select(AllTypesNil.numericCol).where(AllTypesNil.numericCol.eq(value)).first()!!
            assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_fixedCol() {
        tm {
            val value = BigDecimal.valueOf(345)
            it.insert(AllTypesNil).fixedCol(value).execute()
            val retrieved = it.select(AllTypesNil.fixedCol).where(AllTypesNil.fixedCol.eq(value)).first()!!
            assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_int1Col() {
        tm {
            val value = true
            it.insert(AllTypesNil).int1Col(value).execute()
            val retrieved = it.select(AllTypesNil.int1Col).where(AllTypesNil.int1Col.eq(value)).first()
            assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_tinyintCol() {
        tm {
            val value = true
            it.insert(AllTypesNil).tinyintCol(value).execute()
            val retrieved = it.select(AllTypesNil.tinyintCol).where(AllTypesNil.tinyintCol.eq(value)).first()
            assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_smallintCol() {
        tm {
            val value = 100
            it.insert(AllTypesNil).smallintCol(value).execute()
            val retrieved = it.select(AllTypesNil.smallintCol).where(AllTypesNil.smallintCol.eq(value)).first()!!
            assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_int2Col() {
        tm {
            val value = 200
            it.insert(AllTypesNil).int2Col(value).execute()
            val retrieved = it.select(AllTypesNil.int2Col).where(AllTypesNil.int2Col.eq(value)).first()!!
            assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_mediumintCol() {
        tm {
            val value = 100_000
            it.insert(AllTypesNil).mediumintCol(value).execute()
            val retrieved = it.select(AllTypesNil.mediumintCol).where(AllTypesNil.mediumintCol.eq(value)).first()!!
            assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_int3Col() {
        tm {
            val value = 200_000
            it.insert(AllTypesNil).int3Col(value).execute()
            val retrieved = it.select(AllTypesNil.int3Col).where(AllTypesNil.int3Col.eq(value)).first()!!
            assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_intCol() {
        tm {
            val value = 200L
            it.insert(AllTypesNil).intCol(value).execute()
            val retrieved = it.select(AllTypesNil.intCol).where(AllTypesNil.intCol.eq(value)).first()!!
            assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_int4Col() {
        tm {
            val value = 300L
            it.insert(AllTypesNil).int4Col(value).execute()
            val retrieved = it.select(AllTypesNil.int4Col).where(AllTypesNil.int4Col.eq(value)).first()!!
            assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_bigintCol() {
        tm {
            val value = 1234567L
            it.insert(AllTypesNil).bigintCol(value).execute()
            val retrieved = it.select(AllTypesNil.bigintCol).where(AllTypesNil.bigintCol.eq(value)).first()!!
            assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_int8Col() {
        tm {
            val value = 1234567L
            it.insert(AllTypesNil).int8Col(value).execute()
            val retrieved = it.select(AllTypesNil.int8Col).where(AllTypesNil.int8Col.eq(value)).first()!!
            assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_floatCol() {
        tm {
            val value = 123456f
            it.insert(AllTypesNil).floatCol(value).execute()
            val retrieved = it.select(AllTypesNil.floatCol).where(AllTypesNil.floatCol.eq(value)).first()!!
            assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_doubleCol() {
        tm {
            val value = 12345.12345
            it.insert(AllTypesNil).doubleCol(value).execute()
            val retrieved = it.select(AllTypesNil.doubleCol).where(AllTypesNil.doubleCol.eq(value)).first()!!
            assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_doublePrecisionCol() {
        tm {
            val value = 23456.23456
            it.insert(AllTypesNil).doublePrecisionCol(value).execute()
            val retrieved = it.select(AllTypesNil.doublePrecisionCol).where(AllTypesNil.doublePrecisionCol.eq(value)).first()!!
            assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_bitCol() {
        tm {
            val value = true
            it.insert(AllTypesNil).bitCol(value).execute()
            val retrieved = it.select(AllTypesNil.bitCol).where(AllTypesNil.bitCol.eq(value)).first()!!
            assertThat(retrieved).isEqualTo(value)
        }
    }

    //fixme result set is empty
    @Test
    fun test_binaryCol() {
        tm {
            val value = "hello".encodeToByteArray()
            val id = it.insert(AllTypesNil).binaryCol(value).execute()
            val retrieved = it.select(AllTypesNil.binaryCol).where(AllTypesNil.id.eq(id)).first()!!
            assertThat(String(retrieved)).startsWith(String(value))
        }
    }

    @Test
    fun test_blobCol() {
        tm {
            val value = "hello".encodeToByteArray()
            it.insert(AllTypesNil).blobCol(value).execute()
            val retrieved = it.select(AllTypesNil.blobCol).where(AllTypesNil.blobCol.eq(value)).first()!!
            assertThat(String(retrieved)).isEqualTo(String(value))
        }
    }

    @Test
    fun test_charCol() {
        tm {
            val value = "hello"
            it.insert(AllTypesNil).charCol(value).execute()
            val retrieved = it.select(AllTypesNil.charCol).where(AllTypesNil.charCol.eq(value)).first()!!
            assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_charByteCol() {
        tm {
            val value = "1".encodeToByteArray()
            it.insert(AllTypesNil).charByteCol(value).execute()
            val retrieved = it.select(AllTypesNil.charByteCol).where(AllTypesNil.charByteCol.eq(value)).first()!!
            assertThat(String(retrieved)).isEqualTo(String(value))
        }
    }

    @Test
    fun test_enumCol() {
        tm {
            val value = "MAYBE"
            it.insert(AllTypesNil).enumCol(value).execute()
            val retrieved = it.select(AllTypesNil.enumCol).where(AllTypesNil.enumCol.eq(value)).first()!!
           assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_jsonCol() {
        tm {
            val value = "[{\"hello\": 42}]"
            it.insert(AllTypesNil).jsonCol(value).execute()
            val retrieved = it.select(AllTypesNil.jsonCol).where(AllTypesNil.jsonCol.eq(value)).first()!!
           assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_textCol() {
        tm {
            val value = "The quick brown fox"
            it.insert(AllTypesNil).textCol(value).execute()
            val retrieved = it.select(AllTypesNil.textCol).where(AllTypesNil.textCol.eq(value)).first()!!
           assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_varcharCol() {
        tm {
            val value = "text"
            it.insert(AllTypesNil).varcharCol(value).execute()
            val retrieved = it.select(AllTypesNil.varcharCol).where(AllTypesNil.varcharCol.eq(value)).first()!!
           assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_setCol() {
        tm {
            val value = "chess"
            it.insert(AllTypesNil).setCol(value).execute()
            val retrieved = it.select(AllTypesNil.setCol).where(AllTypesNil.setCol.eq(value)).first()!!
           assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_dateCol() {
        tm {
            val value = LocalDate.of(1980, 10, 10)
            it.insert(AllTypesNil).dateCol(value).execute()
            val retrieved = it.select(AllTypesNil.dateCol).where(AllTypesNil.dateCol.eq(value)).first()!!
           assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_timeCol() {
        tm {
            val value = LocalTime.of(15, 30)
            it.insert(AllTypesNil).timeCol(value).execute()
            val retrieved = it.select(AllTypesNil.timeCol).where(AllTypesNil.timeCol.eq(value)).first()!!
           assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_datetimeCol() {
        tm {
            val value = LocalDateTime.of(1980, 10, 10, 14, 30)
            it.insert(AllTypesNil).datetimeCol(value).execute()
            val retrieved = it.select(AllTypesNil.datetimeCol).where(AllTypesNil.datetimeCol.eq(value)).first()!!
           assertThat(retrieved).isEqualTo(value)
        }
    }

    //fixme Incorrect datetime value: '15:30:00.000' for column `core`.`ALL_TYPES_NIL`.`TIMESTAMP_COL`
    @Test
    fun test_timestampCol() {
        tm {
            val value = LocalDateTime.of(1980, 10, 10, 14, 30)
            it.insert(AllTypesNil).timestampCol(value).execute()
            val retrieved = it.select(AllTypesNil.timestampCol).where(AllTypesNil.timestampCol.eq(value)).first()!!
           assertThat(retrieved).isEqualTo(value)
        }
    }

    @Test
    fun test_yearCol() {
        tm {
            val value = 1990
            it.insert(AllTypesNil).yearCol(value).execute()
            val retrieved = it.select(AllTypesNil.yearCol).where(AllTypesNil.yearCol.eq(value)).first()!!
           assertThat(retrieved).isEqualTo(value)
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
            val id = tr.insert(e).mandatoryColumns("Jack", 3000.5, true, LocalDate.of(1980, 1, 1)).execute()
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
    }*/


}
