package com.dbobjekts.codegen

import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.metadata.SequenceNameResolverStrategy
import com.dbobjekts.integration.h2.custom.AddressTypeColumn
import com.dbobjekts.util.PathUtil
import com.dbobjekts.util.TestSourceWriter
import org.apache.commons.io.file.PathUtils
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Paths


class CodeGeneratorTest {

    @Test
    fun `create schemas`() {
        val writer = TestSourceWriter()
        val generator = CodeGenerator()

        generator.sourceConfigurer()
            .vendor("h2")
            .addLiquibaseChangelogFile("core", getFileInResourcesDir("core-changelog.xml"))
            .addLiquibaseChangelogFile("hr", getFileInResourcesDir("hr-changelog.xml"))

        val mappingConf = generator.mappingConfigurer()
        mappingConf.mapColumnToCustomType("KIND", AddressTypeColumn.INSTANCE, table = "EMPLOYEE_ADDRESS")
        mappingConf.sequenceForPrimaryKey("core", "ADDRESS", "ID", "ADDRESS_SEQ")
        mappingConf.sequenceForPrimaryKey("core", "DEPARTMENT", "ID", "DEPARTMENT_SEQ")
        mappingConf.sequenceForPrimaryKey("core", "EMPLOYEE", "ID", "EMPLOYEE_SEQ")


        generator.outputConfigurer()
            .basePackageForSources("com.dbobjekts.integration.h2")
            .outputDirectoryForGeneratedSources(PathUtil.getGeneratedSourceDir())
        generator.generate()

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
        Assertions.assertThatThrownBy { generator.createCatalogDefinition() }
            .hasMessage("Column COUNTRY_ID VARCHAR(10) NOT NULL cannot be marked for exclusion. It is either a primary key or foreign key.")

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
