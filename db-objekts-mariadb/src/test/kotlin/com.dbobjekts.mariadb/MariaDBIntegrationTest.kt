package com.dbobjekts.integration.mariadb

import com.dbobjekts.api.TransactionManager
import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.mariadb.countries.CatalogDefinition
import com.dbobjekts.mariadb.countries.nation.*
import com.dbobjekts.util.HikariDataSourceFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.nio.file.Paths
import javax.sql.DataSource

@Testcontainers
class MariaDBIntegrationTest {

    @Container
    val container: MariaDBWrapper = MariaDBWrapper()

    @Test
    fun `validate metadata and fetch countries list`() {
        val port = container.firstMappedPort
        val ds = HikariDataSourceFactory
            .create(
                url = "jdbc:mariadb://localhost:$port/test", username = "root", password = "test", driver = "org.mariadb.jdbc.Driver"
            )
        val tm = TransactionManager.builder().withDataSource(ds).withCatalog(CatalogDefinition).build()
        validateCodeGeneration(ds)
        tm { tr ->
            val results =
                tr.select(Countries.name, Regions.name, Continents.name, CountryLanguages.languageId, Languages.language).useOuterJoinsWithDefaultValues()
                    .asList()
            assertThat(results).hasSize(990)
            val sql = tr.transactionExecutionLog().last().sql
            assertThat(sql).isEqualTo("select c1.name,r.name,c.name,cl.language_id,l.language from nation.countries c1 left join nation.country_languages cl on c1.country_id = cl.country_id left join nation.regions r on c1.region_id = r.region_id left join nation.languages l on cl.language_id = l.language_id left join nation.continents c on r.continent_id = c.continent_id")
        }

    }

    fun validateCodeGeneration(ds: DataSource) {
        val gen = CodeGenerator()
        gen.withDataSource(ds)
        gen.outputConfigurer()
            .basePackageForSources("com.dbobjekts.mariadb.countries")
            .outputDirectoryForGeneratedSources(Paths.get("src/generated-sources/kotlin").toAbsolutePath().toString())
        val diff = gen.differencesWithCatalog(CatalogDefinition)
        assertThat(diff).isEmpty()
    }


}
