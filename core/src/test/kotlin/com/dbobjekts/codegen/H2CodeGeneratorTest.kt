package com.dbobjekts.codegen

import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.util.TestSourceWriter
import org.junit.jupiter.api.Test
import java.nio.file.Paths


class H2CodeGeneratorTest {

    @Test
    fun `create schemas`() {
        val writer = TestSourceWriter()
        H2DB.setupDatabaseObjects()
        val generator = CodeGenerator()
        generator.dataSourceConfigurer()
            .vendor("H2")
            .configureDataSource().url("jdbc:h2:mem:test").user("sa")
        generator.mappingConfigurer()
            .sequenceForPrimaryKey("core", "employee", "id", "EMPLOYEE_SEQ")
            .sequenceForPrimaryKey("core", "address", "id", "ADDRESS_SEQ")
            .sequenceForPrimaryKey("core", "department", "id", "DEPARTMENT_SEQ")
            .sequenceForPrimaryKey("hr", "certificate", "id", "CERTIFICATE_SEQ")
        generator.outputConfigurer()
            .basePackageForSources("com.dbobjekts.integration.h2")
            //.sourceWriter(writer)
            .outputDirectoryForGeneratedSources(Paths.get("../integration/src/generated-sources/kotlin").toAbsolutePath().toString())
        generator.generate()
        println(writer)
    }
}
