package com.dbobjekts.integration.h2

import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.fixture.h2.H2DB
import com.dbobjekts.util.TestSourceWriter
import org.junit.jupiter.api.Test


class H2CodeGeneratorTest {

    @Test
    fun `create schemas`() {

        H2DB.setupDatabaseObjects()

        val writer = TestSourceWriter()
        val generator = CodeGenerator()
        generator.dataSourceConfigurer()
            .vendor("H2")
            .configureDataSource().url("jdbc:h2:mem:test").user("sa")
        generator.outputConfigurer()
            .basePackageForSources("com.dbobjekts.integration.h2")
            .sourceWriter(writer)
        generator.generate()
        print(writer.toString())

    }
}
