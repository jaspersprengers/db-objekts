package com.dbobjekts.component

import com.dbobjekts.api.SequenceForPrimaryKeyResolver
import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.fixture.columns.AddressTypeAsIntegerColumn
import com.dbobjekts.fixture.columns.AddressTypeAsStringColumn
import com.dbobjekts.metadata.column.NumberAsBooleanColumn
import com.dbobjekts.testdb.acme.CatalogDefinition
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.lang.IllegalStateException
import java.nio.file.Paths

class CodeGenerationComponentTest {

    @Test
    fun `validate acme catalog`() {

        val generator = CodeGenerator().withDataSource(AcmeDB.dataSource)

        generator.configurePrimaryKeySequences()
            .addCustomResolver(LibrarySchemaResolver)
            .setSequenceNameForPrimaryKey("core", "employee", "id", "EMPLOYEE_SEQUENCE")
            .setSequenceNameForPrimaryKey("core", "address", "id", "ADDRESS_SEQ")
            .setSequenceNameForPrimaryKey("core", "department", "id", "DEPARTMENT_SEQ")

        generator.configureColumnTypeMapping()
            .setColumnTypeForJDBCType("TINYINT(1)", NumberAsBooleanColumn::class.java)
            .setColumnTypeForName(table = "EMPLOYEE_ADDRESS", column = "KIND", columnType = AddressTypeAsStringColumn::class.java)
            .setColumnTypeForNamePattern(columnPattern = "address_string", columnType = AddressTypeAsStringColumn::class.java)
            .setColumnTypeForNamePattern(columnPattern = "address_int", columnType = AddressTypeAsIntegerColumn::class.java)
        generator.configureOutput()
            .basePackageForSources("com.dbobjekts.testdb.acme")
            .outputDirectoryForGeneratedSources(Paths.get("src/generated-sources/kotlin").toAbsolutePath().toString())
        val diff: List<String> = generator.differencesWithCatalog(CatalogDefinition)
        assertThat(diff).describedAs("acme catalog differs from database definition").isEmpty()
        //generator.generateSourceFiles()
    }

    @Test
    fun `with custom table and column name mappings`() {
        AcmeDB.transactionManager {
            //add some stuff to the test db that wel will ignore for the code generator
            it.sql("CREATE SCHEMA if not exists trial").execute()
            it.sql("create table trial.emploiee(id BIGINT primary key not null auto_increment)").execute()
            it.sql("create table trial.adres(id BIGINT primary key not null, adres_id BIGINT not null, foreign key(adres_id) references trial.emploiee(id) )").execute()
        }

        val generator = CodeGenerator().withDataSource(AcmeDB.dataSource)
        generator.configureExclusions().ignoreSchemas("core", "hr", "library")
        generator.configureObjectNaming()
            .setObjectNameForTable("trial","emploiee", "Employee")
            .setObjectNameForTable("trial","adres", "Address")
            .setFieldNameForColumn("trial", "emploiee", "id", "primaryKey")
            .setFieldNameForColumn("trial", "adres", "adres_id", "addressId")
        generator.configureOutput()
            .basePackageForSources("com.dbobjekts.testdb.acme")
        val def = generator.createCatalogDefinition()
        val employeeTable = def.findTable("trial", "emploiee")?:throw IllegalStateException("no employee table found")
        val employeeId = employeeTable.findPrimaryKey("id") ?: throw IllegalStateException()
        val addressId = def.findTable("trial", "adres")?.findForeignKey("adres_id") ?: throw IllegalStateException()
        assertThat(employeeId.columnName.fieldName).isEqualTo("primaryKey")
        assertThat(addressId.columnName.fieldName).isEqualTo("addressId")
    }

    @Test
    fun `with custom exclusions`() {
        val generator = CodeGenerator().withDataSource(AcmeDB.dataSource)
        AcmeDB.transactionManager {
            //add some stuff to the test db that wel will ignore for the code generator
            it.sql("CREATE SCHEMA if not exists finance").execute()
            it.sql("create table if not exists hr.country").execute()
            it.sql("alter table core.employee add date_created DATETIME not null default now()").execute()
            it.sql("alter table core.country add date_created DATETIME not null default now()").execute()
            it.sql("alter table core.country add audit_pending DATETIME null").execute()
        }
        generator.configureExclusions()
            .ignoreColumnPattern("audit")
            .ignoreColumn("date_created")
            .ignoreSchemas("finance")
            .ignoreTable("country", schema = "hr") // important to specify schema, because there is a core.country as well
        generator.configureOutput()
            .basePackageForSources("com.dbobjekts.testdb.acme")
        val def = generator.createCatalogDefinition()
        assertThat(def.findSchema("finance")).isNull()
        assertThat(def.findTable("core", "employee")?.findColumn("date_created")).isNull()
        assertThat(def.findTable("core", "country")?.findColumn("date_created")).isNull()
        assertThat(def.findTable("hr", "country")).isNull()

    }

    object LibrarySchemaResolver : SequenceForPrimaryKeyResolver {
        override fun invoke(properties: ColumnMappingProperties): String? =
            if (properties.schema.value.equals(
                    "library",
                    true
                ) && properties.column.value == "ID"
            ) properties.table.value + "_SEQ" else null
    }


}
