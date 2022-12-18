package com.dbobjekts.integration.h2

import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.util.TestSourceWriter
import org.junit.jupiter.api.Test
import java.nio.file.Paths


class H2CodeGeneratorTest {

    @Test
    fun `create schemas`() {

        H2DB.setupDatabaseObjects()

        val writer = TestSourceWriter()
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
            .outputDirectoryForGeneratedSources(Paths.get("../core/src/generated-sources/kotlin").toAbsolutePath().toString())
        generator.generate()
       print(writer.toString())

    }
}
