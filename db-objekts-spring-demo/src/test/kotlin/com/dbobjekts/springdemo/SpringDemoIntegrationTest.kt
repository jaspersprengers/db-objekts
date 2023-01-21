package com.dbobjekts.springdemo

import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.demo.db.Aliases
import com.dbobjekts.demo.db.CatalogDefinition
import com.dbobjekts.demo.db.HasAliases
import com.dbobjekts.metadata.column.BigDecimalColumn
import com.dbobjekts.metadata.column.DoubleColumn
import com.dbobjekts.metadata.column.NumberAsBooleanColumn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import java.nio.file.Paths
import javax.sql.DataSource

@SpringBootTest
//@Testcontainers
class SpringDemoIntegrationTest : HasAliases by Aliases {

    companion object {
        //@Container
        //val container: MariaDBWrapper = MariaDBWrapper("10.10", listOf("acme.sql", "classicmodels.sql"))

        /**
         * The host port on which the dockerized db is available is not known until after container is live.
         * This Spring mechanism allows us to inject the correct jdbc URl into the context
         */
        @JvmStatic
        @DynamicPropertySource
        fun updateDbProperties(registry: DynamicPropertyRegistry) {
            /*registry.add("spring.datasource.url") {
                String.format(
                    "jdbc:mariadb://localhost:%d/test",
                    container.getFirstMappedPort()
                )
            }*/
        }
    }

    @Autowired
    lateinit var dataSource: DataSource

    @Autowired
    lateinit var service: ClassicModelsService

    @Test
    fun `validate generated code`() {
        val gen = CodeGenerator()
        gen.withDataSource(dataSource)
        gen.configureColumnTypeMapping()
            .setColumnTypeForJDBCType("DECIMAL", DoubleColumn::class.java)
            .setColumnTypeForJDBCType("TINYINT", NumberAsBooleanColumn::class.java)
        gen.configureOutput()
            .basePackageForSources("com.dbobjekts.demo.db")
            .outputDirectoryForGeneratedSources(Paths.get("src/generated-sources/kotlin").toAbsolutePath().toString())
        val diff = gen.differencesWithCatalog(CatalogDefinition)
        assertThat(diff).isEmpty()
        gen.generateSourceFiles()
    }

    @Test
    fun `insert and retrieve`() {
        service.getCustomersWithMinimumOrderCount(3).forEach { (customer, count) ->
            println("*****")
            println("Customer ${customer.contactFirstName} ${customer.contactFirstName} has $count orders.")
            service.getOrderForCustomer(customer.customerNumber).forEach { (status, price, name) ->
                println("$status $price $name")
            }
        }
    }

}