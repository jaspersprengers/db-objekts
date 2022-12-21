package com.dbobjekts.integration.h2

import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.util.PathUtil
import com.dbobjekts.util.TestSourceWriter
import org.junit.jupiter.api.Test

class H2CodeGeneratorTest {

    @Test
    fun `create schemas`() {

        H2DB.setupDatabaseObjects()
        val writer = TestSourceWriter()
        val generator = CodeGenerator()
        generator.withDataSource(datasource = H2DB.dataSource)
        generator.outputConfigurer()
            .basePackageForSources("com.dbobjekts.integration.h2")
            //.sourceWriter(writer)
            .outputDirectoryForGeneratedSources(PathUtil.getGeneratedSourceDir())
        generator.generateSourceFiles()

    }
}
