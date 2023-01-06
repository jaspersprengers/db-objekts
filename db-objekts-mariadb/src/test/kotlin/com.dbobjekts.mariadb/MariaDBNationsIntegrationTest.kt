package com.dbobjekts.integration.mariadb

import com.dbobjekts.api.TransactionManager
import com.dbobjekts.mariadb.testdb.CatalogDefinition
import com.dbobjekts.mariadb.testdb.nation.Continents
import com.dbobjekts.mariadb.testdb.nation.Countries
import com.dbobjekts.mariadb.testdb.nation.CountryStats
import com.dbobjekts.mariadb.testdb.nation.Regions
import com.dbobjekts.metadata.column.Aggregate
import com.dbobjekts.util.HikariDataSourceFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import javax.sql.DataSource

@Testcontainers
class MariaDBNationsIntegrationTest {

    companion object {

        @Container
        val container: MariaDBWrapper = MariaDBWrapper("10.10", listOf("acme.sql"))
        lateinit var dataSource: DataSource
        lateinit var tm: TransactionManager

        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            dataSource = container.createDataSource()
            tm = TransactionManager.builder()
                .withDataSource(dataSource)
                .withCatalog(CatalogDefinition)
                .build()
        }
    }

    @Test
    fun `get total population per continent`() {
        tm { tr ->
            val pairs = tr.select(Continents.name, CountryStats.year, CountryStats.population.sum())
                .from(CountryStats.innerJoin(Countries).innerJoin(Regions).innerJoin(Continents))
                .orderAsc(CountryStats.year, CountryStats.population)
                .where(CountryStats.year.gt(2000))
                .having(Aggregate.gt(800_000_000))
                .asList()
            val (name, year, number) = pairs[0]
            assertThat(name).isEqualTo("Africa")
            assertThat(number).isEqualTo(814022223)
        }
    }


}
