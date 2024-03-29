package com.dbobjekts.mariadb.testdb.nation

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.TableRowData
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.AutoKeyLongColumn
import com.dbobjekts.metadata.column.BigDecimalColumn
import com.dbobjekts.metadata.column.ForeignKeyLongColumn
import com.dbobjekts.metadata.column.NullableDateColumn
import com.dbobjekts.metadata.column.NullableVarcharColumn
import com.dbobjekts.metadata.column.VarcharColumn
import com.dbobjekts.statement.WriteQueryAccessors
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase

/**           
 * Auto-generated metadata object for db table nation.countries.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: country_id
 *
 * Foreign keys to: 
 * References by: nation.regions,nation.country_languages,nation.country_stats
 */
object Countries:Table<CountriesRow>("countries"), HasUpdateBuilder<CountriesUpdateBuilder, CountriesInsertBuilder> {
    /**
     * Represents db column nation.countries.country_id
     */
    val countryId = AutoKeyLongColumn(this, "country_id")
    /**
     * Represents db column nation.countries.name
     */
    val name = NullableVarcharColumn(this, "name")
    /**
     * Represents db column nation.countries.area
     */
    val area = BigDecimalColumn(this, "area")
    /**
     * Represents db column nation.countries.national_day
     */
    val nationalDay = NullableDateColumn(this, "national_day")
    /**
     * Represents db column nation.countries.country_code2
     */
    val countryCode2 = VarcharColumn(this, "country_code2")
    /**
     * Represents db column nation.countries.country_code3
     */
    val countryCode3 = VarcharColumn(this, "country_code3")
    /**
     * Represents db column nation.countries.region_id
     *
     * Foreign key to nation.regions.region_id
     */
    val regionId = ForeignKeyLongColumn(this, "region_id", Regions.regionId)
    override val columns: List<AnyColumn> = listOf(countryId,name,area,nationalDay,countryCode2,countryCode3,regionId)
    override fun toValue(values: List<Any?>) = CountriesRow(values[0] as Long,values[1] as String?,values[2] as java.math.BigDecimal,values[3] as java.time.LocalDate?,values[4] as String,values[5] as String,values[6] as Long)
    override fun metadata(): WriteQueryAccessors<CountriesUpdateBuilder, CountriesInsertBuilder> = WriteQueryAccessors(CountriesUpdateBuilder(), CountriesInsertBuilder())
}

class CountriesUpdateBuilder() : UpdateBuilderBase(Countries) {
    fun name(value: String?): CountriesUpdateBuilder = put(Countries.name, value)
    fun area(value: java.math.BigDecimal): CountriesUpdateBuilder = put(Countries.area, value)
    fun nationalDay(value: java.time.LocalDate?): CountriesUpdateBuilder = put(Countries.nationalDay, value)
    fun countryCode2(value: String): CountriesUpdateBuilder = put(Countries.countryCode2, value)
    fun countryCode3(value: String): CountriesUpdateBuilder = put(Countries.countryCode3, value)
    fun regionId(value: Long): CountriesUpdateBuilder = put(Countries.regionId, value)
    
    /**
     * FOR INTERNAL USE ONLY
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as CountriesRow
      add(Countries.countryId, rowData.countryId)
      add(Countries.name, rowData.name)
      add(Countries.area, rowData.area)
      add(Countries.nationalDay, rowData.nationalDay)
      add(Countries.countryCode2, rowData.countryCode2)
      add(Countries.countryCode3, rowData.countryCode3)
      add(Countries.regionId, rowData.regionId)
      return where(Countries.countryId.eq(rowData.countryId))
    }    
        
}

class CountriesInsertBuilder():InsertBuilderBase(){
    fun name(value: String?): CountriesInsertBuilder = put(Countries.name, value)
    fun area(value: java.math.BigDecimal): CountriesInsertBuilder = put(Countries.area, value)
    fun nationalDay(value: java.time.LocalDate?): CountriesInsertBuilder = put(Countries.nationalDay, value)
    fun countryCode2(value: String): CountriesInsertBuilder = put(Countries.countryCode2, value)
    fun countryCode3(value: String): CountriesInsertBuilder = put(Countries.countryCode3, value)
    fun regionId(value: Long): CountriesInsertBuilder = put(Countries.regionId, value)

    fun mandatoryColumns(area: java.math.BigDecimal, countryCode2: String, countryCode3: String, regionId: Long) : CountriesInsertBuilder {
      mandatory(Countries.area, area)
      mandatory(Countries.countryCode2, countryCode2)
      mandatory(Countries.countryCode3, countryCode3)
      mandatory(Countries.regionId, regionId)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as CountriesRow
      add(Countries.name, rowData.name)
      add(Countries.area, rowData.area)
      add(Countries.nationalDay, rowData.nationalDay)
      add(Countries.countryCode2, rowData.countryCode2)
      add(Countries.countryCode3, rowData.countryCode3)
      add(Countries.regionId, rowData.regionId)
      return execute()
    }    
        
}


data class CountriesRow(
val countryId: Long = 0,
  val name: String?,
  val area: java.math.BigDecimal,
  val nationalDay: java.time.LocalDate?,
  val countryCode2: String,
  val countryCode3: String,
  val regionId: Long    
) : TableRowData<CountriesUpdateBuilder, CountriesInsertBuilder>(Countries.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>(Pair(Countries.countryId, countryId))
}
        
