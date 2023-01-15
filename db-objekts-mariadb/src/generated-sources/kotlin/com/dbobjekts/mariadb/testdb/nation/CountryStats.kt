package com.dbobjekts.mariadb.testdb.nation

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.TableRowData
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.ForeignKeyLongColumn
import com.dbobjekts.metadata.column.LongColumn
import com.dbobjekts.metadata.column.NullableBigDecimalColumn
import com.dbobjekts.metadata.column.NullableLongColumn
import com.dbobjekts.statement.WriteQueryAccessors
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase

/**           
 * Auto-generated metadata object for db table nation.country_stats.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: [country_id, year]
 *
 * Foreign keys to: 
 * References by: nation.countries
 */
object CountryStats:Table<CountryStatsRow>("country_stats"), HasUpdateBuilder<CountryStatsUpdateBuilder, CountryStatsInsertBuilder> {
    /**
     * Represents db column nation.country_stats.country_id
     *
     * Foreign key to nation.countries.country_id
     */
    val countryId = ForeignKeyLongColumn(this, "country_id", Countries.countryId)
    /**
     * Represents db column nation.country_stats.year
     */
    val year = LongColumn(this, "year")
    /**
     * Represents db column nation.country_stats.population
     */
    val population = NullableLongColumn(this, "population")
    /**
     * Represents db column nation.country_stats.gdp
     */
    val gdp = NullableBigDecimalColumn(this, "gdp")
    override val columns: List<AnyColumn> = listOf(countryId,year,population,gdp)
    override fun toValue(values: List<Any?>) = CountryStatsRow(values[0] as Long,values[1] as Long,values[2] as Long?,values[3] as java.math.BigDecimal?)
    override fun metadata(): WriteQueryAccessors<CountryStatsUpdateBuilder, CountryStatsInsertBuilder> = WriteQueryAccessors(CountryStatsUpdateBuilder(), CountryStatsInsertBuilder())
}

class CountryStatsUpdateBuilder() : UpdateBuilderBase(CountryStats) {
    fun countryId(value: Long): CountryStatsUpdateBuilder = put(CountryStats.countryId, value)
    fun year(value: Long): CountryStatsUpdateBuilder = put(CountryStats.year, value)
    fun population(value: Long?): CountryStatsUpdateBuilder = put(CountryStats.population, value)
    fun gdp(value: java.math.BigDecimal?): CountryStatsUpdateBuilder = put(CountryStats.gdp, value)
    
    /**
     * FOR INTERNAL USE ONLY
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as CountryStatsRow
      add(CountryStats.countryId, rowData.countryId)
      add(CountryStats.year, rowData.year)
      add(CountryStats.population, rowData.population)
      add(CountryStats.gdp, rowData.gdp)
      return where(CountryStats.countryId.eq(rowData.countryId).and(CountryStats.year.eq(rowData.year)))
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
) : TableRowData<CountryStatsUpdateBuilder, CountryStatsInsertBuilder>(CountryStats.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>(Pair(CountryStats.countryId, countryId),Pair(CountryStats.year, year))
}
        
