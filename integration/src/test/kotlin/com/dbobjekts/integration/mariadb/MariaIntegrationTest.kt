package com.dbobjekts.integration.mariadb

import com.dbobjekts.AnyColumn
import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.datatypemapper.ColumnTypeMapper
import com.dbobjekts.codegen.datatypemapper.StandardColumnTypeMapper
import com.dbobjekts.fixture.MariaDBWrapper
import com.dbobjekts.integration.h2.custom.AddressTypeColumn
import com.dbobjekts.metadata.DefaultTable
import com.dbobjekts.metadata.column.ColumnType
import com.dbobjekts.util.TestSourceWriter
import com.dbobjekts.vendors.Vendors
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.testcontainers.junit.jupiter.Container

//@Testcontainers
class MariaIntegrationTest {

    companion object {
        @Container
        @JvmStatic
        val CONTAINER = MariaDBWrapper()

        @JvmStatic
        @BeforeAll
        fun setup() {
            //val port = CONTAINER.firstMappedPort
            /* val ds = HikariDataSourceFactory
                 .create(
                     url = "jdbc:mariadb://localhost:$port/test",
                     username = "root",
                     password = "test", driver = "org.mariadb.jdbc.Driver"
                 )
             TransactionManager.builder().dataSource(ds).catalog(Catalog("mariadb")).buildForSingleton()*/
        }
    }

    /* @Test
     fun `generate`() {
         val gen = CodeGenerator()
         gen.sourceConfigurer().vendor(Vendors.MARIADB)
             .configureDataSource().password("test").user("root").url("jdbc:mariadb://localhost:${CONTAINER.firstMappedPort}/")
         gen.mappingConfigurer().generatedPrimaryKeyConfiguration().autoIncrementPrimaryKey()
         gen.outputConfigurer().basePackageForSources("com.dbobjekts.integration.mariadb.catalog")
             .outputDirectoryForGeneratedSources("/Users/jsprengersrabo/dev/db-objekts/integration/src/generated-sources/kotlin")
         gen.generate()
     }*/

    /*    @Test
        fun `get content`() {
            TransactionManager.newTransaction {
                val id: Long = it.insert(Employee).mandatoryColumns("Bob", 12_000.0, LocalDate.of(1977, 10, 10)).execute()
                val name = it.select(Employee.name).from(Employee).where(Employee.id.eq(id)).first()
                assertThat(name).isEqualTo("Bob")
            }
        }*/

    @Test
    fun `create schemas for live db`() {
        val writer = TestSourceWriter()
        val generator = CodeGenerator()

        generator.dataSourceConfigurer()
            .vendor(Vendors.MARIADB).configureDataSource().password("test").url("jdbc:mariadb://localhost:3306/classicmodels")
            .user("root")//.driverClassName("org.mariadb.jdbc.Driver")
        generator.outputConfigurer().basePackageForSources("com.dbobjekts")

        val mapping = generator.mappingConfigurer()
        generator.exclusionConfigurer().ignoreTables("classicmodels", "flyway_schema_history")
        mapping.addColumnTypeMapper(AddressTypeMapper)
            .addColumnTypeMapper(StandardColumnTypeMapper("INT_BOOLEAN", ColumnType.NUMBER_AS_BOOLEAN))
        //.generatedPrimaryKeyConfiguration().autoIncrementPrimaryKey()

    }

    object AddressTypeMapper : ColumnTypeMapper {
        override fun invoke(properties: ColumnMappingProperties): AnyColumn? =
            if (properties.column.value.uppercase() == "KIND") AddressTypeColumn(DefaultTable, properties.column.value) else null
    }

}
