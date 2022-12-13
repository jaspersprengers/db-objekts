package com.dbobjekts.integration.mariadb

import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.fixture.MariaDBWrapper
import com.dbobjekts.jdbc.TransactionManager
import com.dbobjekts.vendors.Vendors
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.LocalDate

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


}
