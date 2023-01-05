package com.dbobjekts.mariadbdemo

import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.mariadb.testdb.CatalogDefinition
import com.dbobjekts.metadata.column.NumberAsBooleanColumn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
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
class MariadbComponentTest {

    companion object {
        @Container
        val container: MariaDBWrapper = MariaDBWrapper("10.10", listOf("acme.sql"))

        @JvmStatic
        @DynamicPropertySource
        fun mysqlProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") {
                String.format(
                    "jdbc:mariadb://localhost:%d/test",
                    container.getFirstMappedPort()
                )
            }
        }

    }

    @Autowired
    lateinit var dataSource: DataSource

    @Autowired
    lateinit var service: DataService

    @Test
    fun `validate generated code`() {
        val gen = CodeGenerator()
        gen.withDataSource(dataSource)
        gen.configureColumnTypeMapping().setColumnTypeForJDBCType("TINYINT", NumberAsBooleanColumn::class.java)
        gen.configureOutput()
            .basePackageForSources("com.dbobjekts.mariadb.testdb")
            .outputDirectoryForGeneratedSources(Paths.get("src/generated-sources/kotlin").toAbsolutePath().toString())
        val diff = gen.differencesWithCatalog(CatalogDefinition)
        assertThat(diff).isEmpty()
    }

    @Test
    fun `insert and retrieve`() {
        service.insertBasicEmployeeData("John", true)
        service.insertBasicEmployeeData("Sally", false)

        val entities = service.getEmployees()
        assertThat(entities).hasSize(2)
        assertThat(entities.map { it.name }).containsExactlyInAnyOrder("John", "Sally")
    }

}
