package com.dbobjekts.mariadb.testdb.nation

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.TableRowData
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object CountryStats:Table<CountryStatsRow>("country_stats"), HasUpdateBuilder<CountryStatsUpdateBuilder, CountryStatsInsertBuilder> {
    val countryId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "country_id", Countries.countryId)
    val year = com.dbobjekts.metadata.column.LongColumn(this, "year")
    val population = com.dbobjekts.metadata.column.NullableLongColumn(this, "population")
    val gdp = com.dbobjekts.metadata.column.NullableBigDecimalColumn(this, "gdp")
    override val columns: List<AnyColumn> = listOf(countryId,year,population,gdp)
    override fun toValue(values: List<Any?>) = CountryStatsRow(values[0] as Long,values[1] as Long,values[2] as Long?,values[3] as java.math.BigDecimal?)
    override fun metadata(): WriteQueryAccessors<CountryStatsUpdateBuilder, CountryStatsInsertBuilder> = WriteQueryAccessors(CountryStatsUpdateBuilder(), CountryStatsInsertBuilder())
}

class CountryStatsUpdateBuilder() : UpdateBuilderBase(CountryStats) {
    fun countryId(value: Long): CountryStatsUpdateBuilder = put(CountryStats.countryId, value)
    fun year(value: Long): CountryStatsUpdateBuilder = put(CountryStats.year, value)
    fun population(value: Long?): CountryStatsUpdateBuilder = put(CountryStats.population, value)
    fun gdp(value: java.math.BigDecimal?): CountryStatsUpdateBuilder = put(CountryStats.gdp, value)

    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as CountryStatsRow
      add(CountryStats.countryId, rowData.countryId)
      add(CountryStats.year, rowData.year)
      add(CountryStats.population, rowData.population)
      add(CountryStats.gdp, rowData.gdp)
      return where (CountryStats.year.eq(rowData.year))
    }    
        
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


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as CountryStatsRow
      add(CountryStats.countryId, rowData.countryId)
      add(CountryStats.year, rowData.year)
      add(CountryStats.population, rowData.population)
      add(CountryStats.gdp, rowData.gdp)
      return execute()
    }    
        
}


data class CountryStatsRow(
  val countryId: Long,
  val year: Long,
  val population: Long?,
  val gdp: java.math.BigDecimal?    
) : TableRowData<CountryStatsUpdateBuilder, CountryStatsInsertBuilder>(CountryStats.metadata())
        
