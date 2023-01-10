package com.dbobjekts.component

import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.datatypemapper.SequenceForPrimaryKeyResolver
import com.dbobjekts.fixture.columns.AddressTypeAsIntegerColumn
import com.dbobjekts.fixture.columns.AddressTypeAsStringColumn
import com.dbobjekts.metadata.column.NumberAsBooleanColumn
import org.junit.jupiter.api.Test
import com.dbobjekts.testdb.acme.CatalogDefinition
import org.assertj.core.api.Assertions.assertThat
import java.nio.file.Paths

class CodeGenerationComponentTest {

    @Test
    fun `validate acme catalog`() {

        AcmeDB.transactionManager {
            //add some stuff to the test db that wel will ignore for the code generator
            it.sql("create table IF NOT EXISTS hr.COUNTRY(id varchar(10) primary key, name varchar(50) not null)").execute()
            it.sql("CREATE SCHEMA if not exists finance").execute()
            it.sql("alter table core.employee add date_created DATETIME not null default now()").execute()
            it.sql("alter table core.country add date_created DATETIME not null default now()").execute()
            it.sql("alter table core.country add audit_pending DATETIME null").execute()
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
            .setSequenceNameForPrimaryKey("core", "department", "id", "DEPARTMENT_SEQ")

        generator.configureColumnTypeMapping()
            .setColumnTypeForJDBCType("TINYINT(1)", NumberAsBooleanColumn::class.java)
            .setColumnTypeForName(table = "EMPLOYEE_ADDRESS", column = "KIND", columnType = AddressTypeAsStringColumn::class.java)
            .setColumnTypeForName(column = "address_string", columnType = AddressTypeAsStringColumn::class.java)
            .setColumnTypeForName(column = "address_int", columnType = AddressTypeAsIntegerColumn::class.java)
        generator.configureOutput()
            .basePackageForSources("com.dbobjekts.testdb.acme")
        .outputDirectoryForGeneratedSources(Paths.get("src/generated-sources/kotlin").toAbsolutePath().toString())
        val diff: List<String> = generator.differencesWithCatalog(CatalogDefinition)
        //assertThat(diff).describedAs("acme catalog differs from database definition").isEmpty()
        generator.generateSourceFiles()

    }

    object LibrarySchemaResolver : SequenceForPrimaryKeyResolver {
        override fun invoke(properties: ColumnMappingProperties): String? =
            if (properties.schema.value.equals("library", true)  && properties.column.value == "ID") properties.table.value + "_SEQ" else null
    }


}
