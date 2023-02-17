package com.dbobjekts.component

import com.dbobjekts.api.PathsUtil
import com.dbobjekts.api.SequenceForPrimaryKeyResolver
import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.fixture.columns.AddressType
import com.dbobjekts.metadata.column.NumberAsBooleanColumn
import com.dbobjekts.metadata.column.SequenceKeyLongColumn
import com.dbobjekts.testdb.acme.CatalogDefinition
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class CodeGenerationComponentTest {

    @Test
    fun `validate acme catalog`() {

        CodeGenerator()
            .withDataSource(AcmeDB.dataSource)
        .configurePrimaryKeySequences()
            .addCustomResolver(LibrarySchemaResolver)
            .setSequenceNameForPrimaryKey("core", "employee", "id", "EMPLOYEE_SEQUENCE")
            .setSequenceNameForPrimaryKey("core", "address", "id", "ADDRESS_SEQ")
            .setSequenceNameForPrimaryKey("core", "department", "id", "DEPARTMENT_SEQ")
        .and().configureColumnTypeMapping()
            .setColumnTypeForJDBCType("TINYINT(1)", NumberAsBooleanColumn::class.java)
            .setEnumForColumnName(column = "kind", table = "EMPLOYEE_ADDRESS", enumClass = AddressType::class.java)
            .setEnumForColumnName(column = "address_string", enumClass = AddressType::class.java)
            .setEnumForColumnName(column = "address_int", enumClass = AddressType::class.java)
        .and().configureOutput()
            .basePackageForSources("com.dbobjekts.testdb2.acme")
            .outputDirectoryForGeneratedSources(PathsUtil.getGeneratedSourcesDirectory())
        .and().validateCatalog(CatalogDefinition)
            .assertNoDifferences()
        .and().generateSourceFiles()
    }

    @Test
    fun `with custom table and column name mappings`() {
        AcmeDB.transactionManager {
            //add some stuff to the test db that wel will ignore for the code generator
            it.sql("CREATE SCHEMA if not exists trial").execute()
            it.sql("create table trial.emploiee(id BIGINT primary key not null auto_increment)").execute()
            it.sql("create table trial.adres(id BIGINT primary key not null, adres_id BIGINT not null, foreign key(adres_id) references trial.emploiee(id) )")
                .execute()
        }

        val def = CodeGenerator()
            .withDataSource(AcmeDB.dataSource)
            .configureExclusions()
            .ignoreSchemas("core", "hr", "library")
            .and().configureObjectNaming()
            .setObjectNameForTable("trial", "emploiee", "Employee")
            .setObjectNameForTable("trial", "adres", "Address")
            .setFieldNameForColumn("trial", "emploiee", "id", "primaryKey")
            .setFieldNameForColumn("trial", "adres", "adres_id", "addressId")
            .and().configureOutput()
            .basePackageForSources("com.dbobjekts.testdb.acme")
            .and().createCatalogDefinition()

        val employeeTable = def.findTable("trial", "emploiee") ?: throw IllegalStateException("no employee table found")
        val employeeId = employeeTable.findPrimaryKey("id") ?: throw IllegalStateException()

        val addressId = def.findTable("trial", "adres")?.findForeignKey("adres_id") ?: throw IllegalStateException()
        assertThat(employeeId.columnName.fieldName).isEqualTo("primaryKey")
        assertThat(addressId.columnName.fieldName).isEqualTo("addressId")
    }

    @Test
    fun `with reserved schema name`() {
        val generator = CodeGenerator().withDataSource(AcmeDB.dataSource)
        AcmeDB.transactionManager {
            it.sql("CREATE SCHEMA if not exists string").execute()
            it.sql("CREATE TABLE if not exists string.string(id int null)").execute()
            val def = generator.configureExclusions().ignoreSchemas("core", "hr", "library")
                .and().configureObjectNaming()
                .setObjectNameForSchema("string", "StringSchema")
                .setObjectNameForTable("string", "string", "StringTable")
                .and().configureOutput()
                .basePackageForSources("com.dbobjekts.testdb.acme")
                .and().createCatalogDefinition()

            assertThat(def.findSchema("string")?.schemaName?.metaDataObjectName).isEqualTo("StringSchema")
            assertThat(def.findTable("string", "string")?.tableName?.metaDataObjectName).isEqualTo("StringTable")
            it.sql("DROP TABLE string.string").execute()
            it.sql("DROP SCHEMA string").execute()
        }

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
        val def = generator.configureExclusions()
            .ignoreColumnPattern("audit")
            .ignoreColumn("date_created")
            .ignoreSchemas("finance")
            .ignoreTable("country", schema = "hr") // important to specify schema, because there is a core.country as well
            .and().configureOutput()
            .basePackageForSources("com.dbobjekts.testdb.acme")
            .and().createCatalogDefinition()

        assertThat(def.findColumnsForType(SequenceKeyLongColumn::class.java)).hasSize(7)

        assertThat(def.findSchema("finance")).isNull()
        assertThat(def.findTable("core", "employee")?.findColumn("date_created")).isNull()
        assertThat(def.findTable("core", "country")?.findColumn("date_created")).isNull()
        assertThat(def.findTable("hr", "country")).isNull()
    }

    @Test
    fun `with illegal identifiers`() {
        val conf = CodeGenerator().withDataSource(AcmeDB.dataSource).configureObjectNaming()
        assertThatThrownBy { conf.setObjectNameForSchema("core", "true") }
            .hasMessage("true cannot be used as an override for schema 'core'. It is a restricted Java/Kotlin keyword.")
        assertThatThrownBy { conf.setFieldNameForColumn("core", "employee", "married", "true") }
            .hasMessage("true cannot be used as an override for column 'core.employee.married'. It is a restricted Java/Kotlin keyword.")
        assertThatThrownBy { conf.setFieldNameForColumn("core", "employee", "married", "hello world") }
            .hasMessage("hello world cannot be used as an override for column 'core.employee.married'. It is not a valid Java/Kotlin identifier.")

        assertThatThrownBy { conf.setObjectNameForTable("core", "employee", "true") }
            .hasMessage("true cannot be used as an override for table 'core.employee'. It is a restricted Java/Kotlin keyword.")
        assertThatThrownBy { conf.setObjectNameForTable("core", "employee", "hello world") }
            .hasMessage("hello world cannot be used as an override for table 'core.employee'. It is not a valid Java/Kotlin identifier.")
    }

    @Test
    fun `with duplicate column names`() {
        val generator = CodeGenerator().withDataSource(AcmeDB.dataSource)
            .configureObjectNaming().setFieldNameForColumn("core", "employee", "married", "salary")
            .and()
            .configureOutput()
            .basePackageForSources("com.dbobjekts.testdb.acme")
            .and()
        assertThatThrownBy { generator.createCatalogDefinition() }.hasMessageStartingWith("The following column names are found more than once in table EMPLOYEE")
    }

    @Test
    fun `with duplicate table names`() {
        val generator = CodeGenerator().withDataSource(AcmeDB.dataSource)
            .configureObjectNaming().setObjectNameForTable("core", "employee", "country")
            .and().configureOutput()
            .basePackageForSources("com.dbobjekts.testdb.acme")
            .and()
        assertThatThrownBy { generator.createCatalogDefinition() }.hasMessageStartingWith("The following table names  [Country] are found multiple times across schemas. This is not allowed.")
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
