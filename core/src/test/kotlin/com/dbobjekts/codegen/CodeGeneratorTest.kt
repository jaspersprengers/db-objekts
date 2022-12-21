package com.dbobjekts.codegen

import com.dbobjekts.api.Columns
import com.dbobjekts.codegen.parsers.TableMetaDataRow
import com.dbobjekts.metadata.column.*
import com.dbobjekts.metadata.column.AutoKeyLongColumn
import com.dbobjekts.util.PathUtil
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class CodeGeneratorTest {


    fun setup(): CodeGenerator {
        val generator = CodeGenerator()
        generator.dataSourceConfigurer()
            .vendor("H2")
            .configureDataSource().url("jdbc:h2:mem:test").user("sa")
        generator.outputConfigurer()
            .basePackageForSources("com.dbobjekts.integration.h2")
        return generator
    }

    @Test
    fun `create schemas`() {

        fun validate(dataType: String, autoIncrement: Boolean, expectedClass: Class<*>, sequence: String? = null) {
            val generator = setup()
            val parserFactory = MockDBParserFactory()
            val id = TableMetaDataRow(
                schema = "core",
                table = "EMPLOYEE",
                column = "id",
                dataType = dataType,
                isPrimaryKey = true,
                nullable = false,
                autoIncrement = autoIncrement
            )
            parserFactory.addRow(id)
            generator.parserFactory = parserFactory
            sequence?.let { generator.mappingConfigurer().sequenceForPrimaryKey("core", "employee", "id", it) }
            val helper = CatalogDefinitionHelper(generator.createCatalogDefinition())
            val t = helper.getTable(table = "EMPLOYEE")!!
            val idCol = helper.getColumn(t, "ID")!!
            assertThat(idCol.column).isInstanceOf(expectedClass)

        }

        validate("BIGINT", false, LongColumn::class.java)
        validate("INTEGER", false, IntegerColumn::class.java)

        validate("BIGINT", true, LongColumn::class.java)
        validate("INTEGER", true, AutoKeyIntegerColumn::class.java)

        // auto increment setting supersedes a matching index
        validate("BIGINT", true, AutoKeyLongColumn::class.java, "employee_seq")
        validate("INTEGER", true, AutoKeyIntegerColumn::class.java, "employee_seq")

        validate("BIGINT", false, SequenceKeyLongColumn::class.java, "employee_seq")
        validate("INTEGER", false, SequenceKeyIntegerColumn::class.java, "employee_seq")

    }

    @Test
    fun `edge cases`() {
        // non-nullable nullable column with default value?

        // auto increment setting supersedes a matching index
    }

    /*  @Test
      fun `cannot exclude foreign key reference`() {
          val writer = TestSourceWriter()
          val generator = CodeGenerator()

          generator.dataSourceConfigurer()
              .vendor("h2")

          generator.exclusionConfigurer()
              .ignoreColumns("COUNTRY_ID")

          generator.outputConfigurer()
              .basePackageForSources("com.dbobjekts")
          Assertions.assertThatThrownBy { generator.createCatalogDefinition() }
              .hasMessage("Column COUNTRY_ID VARCHAR(10) NOT NULL cannot be marked for exclusion. It is either a primary key or foreign key.")
      }*/

}
