package com.dbobjekts.component

import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.datatypemapper.SequenceForPrimaryKeyResolver
import org.junit.jupiter.api.Test
import com.dbobjekts.testdb.AddressTypeAsStringColumn
import com.dbobjekts.testdb.AddressTypeAsIntegerColumn
import com.dbobjekts.testdb.acme.CatalogDefinition
import org.assertj.core.api.Assertions.assertThat
import java.nio.file.Paths

class AcmeCatalogCodeGenComponentTest {

    @Test
    fun `validate acme catalog`() {

        AcmeDB.setupDatabaseObjects()

        AcmeDB.transactionManager {
            //add some stuff to the test db that wel will ignore for the code generator
            it.execute("create table IF NOT EXISTS hr.COUNTRY(id varchar(10) primary key, name varchar(50) not null)")
            it.execute("CREATE SCHEMA if not exists finance")
            it.execute("alter table core.employee add date_created DATETIME not null default now()")
            it.execute("alter table core.country add date_created DATETIME not null default now()")
            it.execute("alter table core.country add audit_pending DATETIME null")
        }

        val generator = CodeGenerator().withDataSource(AcmeDB.dataSource)

        generator.configureExclusions()
            .ignoreColumnPattern("audit")
            .ignoreColumn("date_created")
            .ignoreSchemas("finance")
            .ignoreTable("country", schema = "hr") // important to specify schema, because there is a core.country as well

        generator.configurePrimaryKeySequences()
            .addCustomResolver(LibrarySchemaResolver)
            .setSequenceNameForPrimaryKey("core", "employee", "id", "EMPLOYEE_SEQUENCE")
            .setSequenceNameForPrimaryKey("core", "address", "id", "ADDRESS_SEQ")


        generator.configureColumnTypeMapping()
            .setColumnTypeForName(table = "EMPLOYEE_ADDRESS", column = "KIND", columnType = AddressTypeAsStringColumn::class.java)
            .setColumnTypeForName(column = "address_string", columnType = AddressTypeAsStringColumn::class.java)
            .setColumnTypeForName(column = "address_int", columnType = AddressTypeAsIntegerColumn::class.java)
        generator.configureOutput()
            .basePackageForSources("com.dbobjekts.testdb.acme")
            //.outputDirectoryForGeneratedSources(Paths.get("src/generated-sources/kotlin").toAbsolutePath().toString())
        val diff = generator.differencesWithCatalog(CatalogDefinition)
        assertThat(diff).describedAs("acme catalog differs from database definition").isEmpty()
        //generator.generateSourceFiles()

    }

    object LibrarySchemaResolver : SequenceForPrimaryKeyResolver {
        override fun invoke(properties: ColumnMappingProperties): String? =
            if (properties.jdbcType == "BIGINT") properties.table.value + "_SEQ" else null
    }


}