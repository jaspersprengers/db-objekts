package com.dbobjekts.integration.h2

import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.sampledbs.h2.library.TestCatalog
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class H2CodeGeneratorTest {

    @Test
    fun `create schemas`() {

        H2DB.setupDatabaseObjects()
        //H2CodeGenerator().validate()
        val generator = CodeGenerator().withDataSource(H2DB.dataSource)
        generator.outputConfigurer()
            .basePackageForSources("com.dbobjekts.sampledbs.h3.library")
            .outputDirectoryForGeneratedSources(Paths.get("src/generated-sources/kotlin").toAbsolutePath().toString())
        val diff = generator.differencesWithCatalog(TestCatalog)
        if (!diff.isEmpty()) {
            println("Creating new source files")
            generator.generateSourceFiles()
        } else {
            println("Nothing has changed. Skipping")
        }
    }


}
