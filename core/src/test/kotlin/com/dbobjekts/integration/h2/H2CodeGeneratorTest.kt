package com.dbobjekts.integration.h2

import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.sampledbs.h2.acme.custom.AddressTypeAsIntegerColumn
import com.dbobjekts.sampledbs.h2.acme.custom.AddressTypeAsStringColumn
import com.dbobjekts.sampledbs.h2.library.TestCatalog
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class H2CodeGeneratorTest {

    @Test
    fun `create schemas`() {

        LibraryDB.setupDatabaseObjects()
        //H2CodeGenerator().validate()
        val generator = CodeGenerator().withDataSource(LibraryDB.dataSource)
       /* generator.mappingConfigurer()
            .setSequenceNameForTable("core", "employee", "id", "EMPLOYEE_SEQ")
            .setSequenceNameForTable("core", "address", "id", "ADDRESS_SEQ")
            .setSequenceNameForTable("core", "department", "id", "DEPARTMENT_SEQ")
            .setSequenceNameForTable("hr", "certificate", "id", "CERTIFICATE_SEQ")
            .setColumnTypeForName(table = "EMPLOYEE_ADDRESS", column = "KIND", columnType = AddressTypeAsStringColumn::class.java)
            .setColumnTypeForName(column = "address_string", columnType = AddressTypeAsStringColumn::class.java)
            .setColumnTypeForName(column = "address_int", columnType = AddressTypeAsIntegerColumn::class.java)
*/
        generator.outputConfigurer()
            .basePackageForSources("com.dbobjekts.sampledbs.h2.library")
            .outputDirectoryForGeneratedSources(Paths.get("core/src/generated-sources/kotlin").toAbsolutePath().toString())
        val diff = generator.differencesWithCatalog(TestCatalog)
        if (!diff.isEmpty()) {
            println("Creating new source files")
            diff.forEach { println(it) }
            //generator.generateSourceFiles()
        } else {
            println("Nothing has changed. Skipping")
        }
    }


}
