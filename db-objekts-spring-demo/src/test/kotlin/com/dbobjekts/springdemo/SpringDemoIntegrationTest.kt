package com.dbobjekts.springdemo

import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.demo.db.CatalogDefinition
import com.dbobjekts.metadata.column.DoubleColumn
import com.dbobjekts.metadata.column.NumberAsBooleanColumn
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.nio.file.Paths
import javax.sql.DataSource

@SpringBootTest
@Testcontainers
class SpringDemoIntegrationTest {

    companion object {
        @Container
        val container: MysqlContainer = MysqlContainer("8.0", listOf("acme.sql", "classicmodels.sql"))

        /**
         * The host port on which the dockerized db is available is not known until after container is live.
         * This Spring mechanism allows us to inject the correct jdbc URl into the context
         */
        @JvmStatic
        @DynamicPropertySource
        fun updateDbProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") {
                String.format(
                    "jdbc:mysql://localhost:%d/test",
                    container.getFirstMappedPort()
                )
            }
        }
    }

    @Autowired
    lateinit var dataSource: DataSource

    @Autowired
    lateinit var service: ClassicModelsService

    @Test
    fun `validate generated code`() {
        val generator = CodeGenerator()
        generator.withDataSource(dataSource)
        generator.configureColumnTypeMapping()
            .setColumnTypeForName(table = "orders", column = "status", columnType = OrderStatusColumn::class.java)
            .setColumnTypeForJDBCType("DECIMAL", DoubleColumn::class.java)
            .setColumnTypeForJDBCType("TINYINT", NumberAsBooleanColumn::class.java)
        generator.configureOutput()
            .basePackageForSources("com.dbobjekts.demo.db")
            .outputDirectoryForGeneratedSources(Paths.get("src/generated-sources/kotlin").toAbsolutePath().toString())
        generator.validateCatalog(CatalogDefinition).assertNoDifferences()
        generator.generateSourceFiles()
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

    @ParameterizedTest
    @CsvSource(value = ["Shipped,303","Resolved,4","On Hold,4","Shipped,303", "In Process,6","Resolved,4"])
    fun `validate orders by size`(status: String, size: Int) {
        val orders = service.getOrdersWithStatus(OrderStatus.valueOf(status))
        Assertions.assertThat(orders.size).isEqualTo(size)
    }

}
