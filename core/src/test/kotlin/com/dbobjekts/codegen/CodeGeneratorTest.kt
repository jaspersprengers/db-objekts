package com.dbobjekts.codegen

import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.metadata.SequenceNameResolverStrategy
import com.dbobjekts.integration.h2.custom.AddressTypeColumn
import com.dbobjekts.jdbc.TransactionManager
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.util.HikariDataSourceFactory
import com.dbobjekts.util.PathUtil
import com.dbobjekts.util.TestSourceWriter
import com.dbobjekts.vendors.Vendors
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Paths


class CodeGeneratorTest {

    @Test
    fun `create schemas`() {
        setupTables()

        val generator = CodeGenerator()

        generator.sourceConfigurer()
            .vendor("H2")
            .configureDataSource().url("jdbc:h2:mem:test").user("sa")

        val mappingConf = generator.mappingConfigurer()
        mappingConf.mapColumnToCustomType("KIND", AddressTypeColumn.INSTANCE, table = "EMPLOYEE_ADDRESS")
        mappingConf.sequenceForPrimaryKey("core", "ADDRESS", "ID", "ADDRESS_SEQ")
        mappingConf.sequenceForPrimaryKey("core", "DEPARTMENT", "ID", "DEPARTMENT_SEQ")
        mappingConf.sequenceForPrimaryKey("core", "EMPLOYEE", "ID", "EMPLOYEE_SEQ")


        generator.outputConfigurer()
            .basePackageForSources("com.dbobjekts.integration.h3")
            .outputDirectoryForGeneratedSources(PathUtil.getGeneratedSourceDir())

        generator.generate()

    }

    @Test
    fun `cannot exclude foreign key reference`() {
        val writer = TestSourceWriter()
        val generator = CodeGenerator()

        generator.sourceConfigurer()
            .vendor("h2")
            .addLiquibaseChangelogFile("core", getFileInResourcesDir("core-changelog.xml"))
            .addLiquibaseChangelogFile("hr", getFileInResourcesDir("hr-changelog.xml"))

        generator.exclusionConfigurer()
            .ignoreColumns("COUNTRY_ID")

        generator.outputConfigurer()
            .basePackageForSources("com.dbobjekts")
        Assertions.assertThatThrownBy { generator.createCatalogDefinition() }
            .hasMessage("Column COUNTRY_ID VARCHAR(10) NOT NULL cannot be marked for exclusion. It is either a primary key or foreign key.")

    }

    private fun getFileInResourcesDir(fileName: String): String {
        val path = Paths.get("src", "test", "resources", fileName)
        return path.toAbsolutePath().toString()
    }

    object H2SequenceNameResolver : SequenceNameResolverStrategy() {
        override fun getSequence(columnProperties: ColumnMappingProperties): String =
            "${columnProperties.schema}.${columnProperties.table.value.uppercase()}_SEQ"
    }

    private fun setupTables() {
        createManager().newTransaction { transaction ->
            transaction.execute("CREATE SCHEMA if not exists core authorization sa")
            transaction.execute("CREATE SCHEMA if not exists hr authorization sa")
            transaction.execute("CREATE SCHEMA if not exists custom authorization sa")

            transaction.execute("CREATE SEQUENCE IF NOT EXISTS core.EMPLOYEE_SEQ START WITH 10")
            transaction.execute("CREATE SEQUENCE IF NOT EXISTS core.ADDRESS_SEQ START WITH 10")
            transaction.execute("CREATE SEQUENCE IF NOT EXISTS hr.CERTIFICATE_SEQ START WITH 10")
            transaction.execute("CREATE SEQUENCE IF NOT EXISTS core.DEPARTMENT_SEQ START WITH 10")

            transaction.execute("create table IF NOT EXISTS core.GADGET(id BIGINT primary key auto_increment, name varchar(50) null)")

            transaction.execute("create table IF NOT EXISTS hr.HOBBY(id varchar(10) primary key, name varchar(50) not null)")
            transaction.execute(
                "create table IF NOT EXISTS core.EMPLOYEE(id BIGINT primary key, name varchar(50) not null, salary double not null, " +
                        "married boolean null, date_of_birth DATE not null, children SMALLINT null, hobby_id varchar(10) null, foreign key(hobby_id) references hr.HOBBY(id))"
            )
            transaction.execute("create table IF NOT EXISTS hr.CERTIFICATE(id BIGINT primary key, name varchar(50) not null, employee_id BIGINT not null, foreign key(employee_id) references core.employee(id))")
            transaction.execute("create table IF NOT EXISTS core.COUNTRY(id varchar(10) primary key, name varchar(50) not null)")

            transaction.execute("create table IF NOT EXISTS core.ADDRESS(id BIGINT primary key, street varchar(50) not null, country_id varchar(10) not null, foreign key(country_id) references core.country(id))")
            transaction.execute("create table if not exists core.EMPLOYEE_ADDRESS(employee_id BIGINT not null, address_id BIGINT not null,kind varchar(10) not null, foreign key(employee_id) references core.employee(id), foreign key(address_id) references core.ADDRESS(id))")
            transaction.execute("create table if not exists core.SHAPE (height DOUBLE, width DOUBLE)")
            transaction.execute("create table IF NOT EXISTS core.DEPARTMENT(id BIGINT primary key, name varchar(50) not null)")
            transaction.execute(
                "create table if not exists core.EMPLOYEE_DEPARTMENT(employee_id BIGINT not null, department_id BIGINT not null, foreign key(employee_id) references core.employee(id), " +
                        "foreign key(department_id) references core.DEPARTMENT(id))"
            )
            transaction.execute(
                "create table IF NOT EXISTS custom.ALL_TYPES(" +
                        "ID INT," +
                        "TINYINT_C TINYINT, " +
                        "SMALLINT_C SMALLINT, " +
                        "INTEGER_C INTEGER," +
                        "INT_C INT, " +
                        "CHAR_C CHAR," +
                        "BLOB_C BLOB," +
                        "CLOB_C CLOB," +
                        "VARCHAR_C VARCHAR(50)," +
                        "BIGINT_C BIGINT, " +
                        "FLOAT_C FLOAT, " +
                        "DOUBLE_C DOUBLE, " +
                        "TIME_C TIME, " +
                        "DATE_C DATE, " +
                        "TIMESTAMP_C TIMESTAMP, " +
                        "TIMESTAMP_TZ_C TIMESTAMP WITH TIME ZONE," +
                        "BOOLEAN_C BOOLEAN, " +
                        "INT_BOOLEAN_C TINYINT)"
            )
            transaction.execute("create table IF NOT EXISTS CUSTOM.TUPLES(c1 INTEGER,c2 INTEGER,c3 INTEGER,c4 INTEGER,c5 INTEGER,c6 INTEGER,c7 INTEGER,c8 INTEGER,c9 INTEGER,c10 INTEGER,c11 INTEGER,c12 INTEGER,c13 INTEGER,c14 INTEGER,c15 INTEGER,c16 INTEGER,c17 INTEGER,c18 INTEGER,c19 INTEGER,c20 INTEGER,c21 INTEGER,c22 INTEGER)")

        }
    }

    private fun createManager(): TransactionManager {
        val ds = HikariDataSourceFactory.create(url = "jdbc:h2:mem:test", username = "sa", password = null, driver = "org.h2.Driver")
        TransactionManager.builder()
            .dataSource(dataSource = ds)
            .catalog(Catalog(Vendors.H2))
            .buildForSingleton()
        return TransactionManager.singletonInstance()
    }

}
