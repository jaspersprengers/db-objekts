package com.dbobjekts.mariadb.countries.nation

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object CountryStats:Table("country_stats"), HasUpdateBuilder<CountryStatsUpdateBuilder, CountryStatsInsertBuilder> {
    val countryId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "country_id", Countries.countryId)
    val year = com.dbobjekts.metadata.column.LongColumn(this, "year")
    val population = com.dbobjekts.metadata.column.NullableLongColumn(this, "population")
    val gdp = com.dbobjekts.metadata.column.NullableBigDecimalColumn(this, "gdp")
    override val columns: List<AnyColumn> = listOf(countryId,year,population,gdp)
    override fun metadata(): WriteQueryAccessors<CountryStatsUpdateBuilder, CountryStatsInsertBuilder> = WriteQueryAccessors(CountryStatsUpdateBuilder(), CountryStatsInsertBuilder())
}

class CountryStatsUpdateBuilder() : UpdateBuilderBase(CountryStats) {
    fun countryId(value: Long): CountryStatsUpdateBuilder = put(CountryStats.countryId, value)
    fun year(value: Long): CountryStatsUpdateBuilder = put(CountryStats.year, value)
    fun population(value: Long?): CountryStatsUpdateBuilder = put(CountryStats.population, value)
    fun gdp(value: java.math.BigDecimal?): CountryStatsUpdateBuilder = put(CountryStats.gdp, value)
}

class CountryStatsInsertBuilder():InsertBuilderBase(){
       fun countryId(value: Long): CountryStatsInsertBuilder = put(CountryStats.countryId, value)
    fun year(value: Long): CountryStatsInsertBuilder = put(CountryStats.year, value)
    fun population(value: Long?): CountryStatsInsertBuilder = put(CountryStats.population, value)
    fun gdp(value: java.math.BigDecimal?): CountryStatsInsertBuilder = put(CountryStats.gdp, value)

    fun mandatoryColumns(countryId: Long, year: Long) : CountryStatsInsertBuilder {
      mandatory(CountryStats.countryId, countryId)
      mandatory(CountryStats.year, year)
      return this
    }

}

