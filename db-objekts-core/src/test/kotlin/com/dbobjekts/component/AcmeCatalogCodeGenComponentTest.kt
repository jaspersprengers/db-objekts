package com.dbobjekts.component

import com.dbobjekts.codegen.CodeGenerator
import org.junit.jupiter.api.Test
import com.dbobjekts.testdb.AddressTypeAsStringColumn
import com.dbobjekts.testdb.AddressTypeAsIntegerColumn
import com.dbobjekts.testdb.acme.CatalogDefinition
import java.nio.file.Paths
import org.assertj.core.api.Assertions.assertThat

class AcmeCatalogCodeGenComponentTest {

    @Test
    fun `validate acme catalog`() {

        AcmeDB.setupDatabaseObjects()

        val generator = CodeGenerator().withDataSource(AcmeDB.dataSource)
        generator.mappingConfigurer()
            .setSequenceNameForTable("core", "employee", "id", "EMPLOYEE_SEQ")
            .setSequenceNameForTable("core", "address", "id", "ADDRESS_SEQ")
            .setSequenceNameForTable("core", "department", "id", "DEPARTMENT_SEQ")
            .setSequenceNameForTable("hr", "certificate", "id", "CERTIFICATE_SEQ")
            .setColumnTypeForName(table = "EMPLOYEE_ADDRESS", column = "KIND", columnType = AddressTypeAsStringColumn::class.java)
            .setColumnTypeForName(column = "address_string", columnType = AddressTypeAsStringColumn::class.java)
            .setColumnTypeForName(column = "address_int", columnType = AddressTypeAsIntegerColumn::class.java)
        generator.outputConfigurer()
            .basePackageForSources("com.dbobjekts.testdb.acme")
            //.outputDirectoryForGeneratedSources(Paths.get("src/generated-sources/kotlin").toAbsolutePath().toString())
        val diff = generator.differencesWithCatalog(CatalogDefinition)
        assertThat(diff).describedAs("acme catalog differs from database definition").isEmpty()
        //generator.generateSourceFiles()

    }


}
