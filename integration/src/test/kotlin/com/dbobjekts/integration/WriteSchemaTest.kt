package com.dbobjekts.integration

import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.datatypemapper.StandardColumnTypeMapper
import com.dbobjekts.codegen.metadata.SequenceNameResolverStrategy
import com.dbobjekts.example.custom.AddressTypeColumn
import com.dbobjekts.example.custom.AddressTypeMapper
import com.dbobjekts.fixture.PathUtil
import com.dbobjekts.fixture.TestSourceWriter
import com.dbobjekts.metadata.column.ColumnType
import com.dbobjekts.CatalogDefinitionHelper
import com.dbobjekts.vendors.MariaDB
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class WriteSchemaTest {

    @Test
    fun `create schemas`() {
        val writer = TestSourceWriter()
        val generator = CodeGenerator()

        generator.sourceConfigurer()
            .vendor("h2")
            .addLiquibaseChangelogFile("custom", PathUtil.getFileInResourcesDir("all_types.xml"))
            .addLiquibaseChangelogFile("core", PathUtil.getFileInResourcesDir("core-changelog.xml"))
            .addLiquibaseChangelogFile("hr", PathUtil.getFileInResourcesDir("hr-changelog.xml"))

        generator.mappingConfigurer()
            .mapColumnToCustomType("KIND", AddressTypeColumn.INSTANCE, table = "EMPLOYEE_ADDRESS")
            .generatedPrimaryKeyConfiguration().sequenceNameResolverStrategy(H2SequenceNameResolver)

        generator.exclusionConfigurer()
            .ignoreSchemas("custom", "blah")
            .ignoreTablesContainingPattern("IGNORED")
            .ignoreColumns("CREATED_DT")
            .ignoreColumns("MODIFIED_DT")

        generator.outputConfigurer()
            .basePackageForSources("com.dbobjekts")

        val helper = CatalogDefinitionHelper.forCatalog(generator.createCatalogDefinition())
        val ea = helper.getTable("core", "EMPLOYEE_ADDRESS")!!
        val kind = helper.getColumn(ea, "kind")!!
        assertThat(kind.column.columnClass.simpleName).isEqualTo("AddressTypeColumn")
        assertThat(helper.getColumn(ea, "CREATED_DT")).isNull()
        assertThat(helper.getColumn(ea, "MODIFIED_DT")).isNull()

        assertThat(helper.getTable(table = "TO_BE_IGNORED")).isNull()

    }

    @Test
    fun `cannot exclude foreign key reference`() {
        val writer = TestSourceWriter()
        val generator = CodeGenerator()

        generator.sourceConfigurer()
            .vendor("h2")
            .addLiquibaseChangelogFile("core", PathUtil.getFileInResourcesDir("core-changelog.xml"))
            .addLiquibaseChangelogFile("hr", PathUtil.getFileInResourcesDir("hr-changelog.xml"))

        generator.exclusionConfigurer()
            .ignoreColumns("COUNTRY_ID")

        generator.outputConfigurer()
            .basePackageForSources("com.dbobjekts")
        Assertions.assertThatThrownBy { generator.createCatalogDefinition() }.hasMessage("Column COUNTRY_ID VARCHAR(10) NOT NULL cannot be marked for exclusion. It is either a primary key or foreign key.")

    }

    @Test
    fun `create schemas for live db`() {
        val writer = TestSourceWriter()
        val generator = CodeGenerator()

        generator.sourceConfigurer()
            .vendor(MariaDB).configureDataSource().password("test").url("jdbc:mariadb://localhost:3306/classicmodels")
            .user("root")//.driverClassName("org.mariadb.jdbc.Driver")
        generator.outputConfigurer().basePackageForSources("com.dbobjekts")

        val mapping = generator.mappingConfigurer()
        generator.exclusionConfigurer().ignoreTablesContainingPattern("classicmodels", "flyway_schema_history")
        mapping.addColumnTypeMapper(AddressTypeMapper)
            .addColumnTypeMapper(StandardColumnTypeMapper("INT_BOOLEAN", ColumnType.NUMBER_AS_BOOLEAN))
        //.generatedPrimaryKeyConfiguration().autoIncrementPrimaryKey()

    }

    object H2SequenceNameResolver : SequenceNameResolverStrategy() {
        override fun getSequence(columnProperties: ColumnMappingProperties): String =
            "${columnProperties.schema}.${columnProperties.table.value.uppercase()}_SEQ"
    }

}

