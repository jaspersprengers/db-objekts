package com.dbobjekts.component

import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.integration.h2.custom.AddressTypeAsIntegerColumn
import com.dbobjekts.integration.h2.custom.AddressTypeAsStringColumn
import com.dbobjekts.util.PathUtil
import com.dbobjekts.util.TestSourceWriter
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.nio.file.Paths


class H2CodeGeneratorTest {

    @Test
    @Disabled
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
            .overrideTypeForColumnByName(table = "EMPLOYEE_ADDRESS", column = "KIND", columnType = AddressTypeAsStringColumn::class.java)
            .overrideTypeForColumnByName(column = "address_string", columnType = AddressTypeAsStringColumn::class.java)
            .overrideTypeForColumnByName(column = "address_int", columnType = AddressTypeAsIntegerColumn::class.java)
        generator.outputConfigurer()
            .basePackageForSources("com.dbobjekts.integration.h2")
            .sourceWriter(writer)
            //.outputDirectoryForGeneratedSources(PathUtil.getTestSourceDir())
        generator.generate()

    }
}
