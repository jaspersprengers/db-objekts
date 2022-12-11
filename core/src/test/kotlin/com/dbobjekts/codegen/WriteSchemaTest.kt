package com.dbobjekts.codegen

import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.metadata.SequenceNameResolverStrategy
import com.dbobjekts.example.custom.AddressTypeColumn
import com.dbobjekts.util.TestSourceWriter
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.nio.file.Paths


class WriteSchemaTest {

    @Test
    fun `create schemas`() {
        val writer = TestSourceWriter()
        val generator = CodeGenerator()

        generator.sourceConfigurer()
            .vendor("h2")
            .addLiquibaseChangelogFile("custom", getFileInResourcesDir("all_types.xml"))
            .addLiquibaseChangelogFile("core", getFileInResourcesDir("core-changelog.xml"))
            .addLiquibaseChangelogFile("hr", getFileInResourcesDir("hr-changelog.xml"))

        generator.mappingConfigurer()
            .mapColumnToCustomType("KIND", AddressTypeColumn.INSTANCE, table = "EMPLOYEE_ADDRESS")
            .generatedPrimaryKeyConfiguration().sequenceNameResolverStrategy(H2SequenceNameResolver)

        generator.exclusionConfigurer()
            .ignoreSchemas("custom", "blah")
            .ignoreTables("IGNORED")
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
            .addLiquibaseChangelogFile("core", getFileInResourcesDir("core-changelog.xml"))
            .addLiquibaseChangelogFile("hr", getFileInResourcesDir("hr-changelog.xml"))

        generator.exclusionConfigurer()
            .ignoreColumns("COUNTRY_ID")

        generator.outputConfigurer()
            .basePackageForSources("com.dbobjekts")
        Assertions.assertThatThrownBy { generator.createCatalogDefinition() }.hasMessage("Column COUNTRY_ID VARCHAR(10) NOT NULL cannot be marked for exclusion. It is either a primary key or foreign key.")

    }

    private fun getFileInResourcesDir(fileName: String): String {
        val path = Paths.get("src", "test", "resources", fileName)
        return path.toAbsolutePath().toString()
    }

    object H2SequenceNameResolver : SequenceNameResolverStrategy() {
        override fun getSequence(columnProperties: ColumnMappingProperties): String =
            "${columnProperties.schema}.${columnProperties.table.value.uppercase()}_SEQ"
    }

}

