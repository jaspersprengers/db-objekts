package com.dbobjekts.mariadb.testdb.nation

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.TableRowData
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Countries:Table<CountriesRow>("countries"), HasUpdateBuilder<CountriesUpdateBuilder, CountriesInsertBuilder> {
    val countryId = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "country_id")
    val name = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "name")
    val area = com.dbobjekts.metadata.column.BigDecimalColumn(this, "area")
    val nationalDay = com.dbobjekts.metadata.column.NullableDateColumn(this, "national_day")
    val countryCode2 = com.dbobjekts.metadata.column.VarcharColumn(this, "country_code2")
    val countryCode3 = com.dbobjekts.metadata.column.VarcharColumn(this, "country_code3")
    val regionId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "region_id", Regions.regionId)
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

    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as CountriesRow
      add(Countries.countryId, rowData.countryId)
      add(Countries.name, rowData.name)
      add(Countries.area, rowData.area)
      add(Countries.nationalDay, rowData.nationalDay)
      add(Countries.countryCode2, rowData.countryCode2)
      add(Countries.countryCode3, rowData.countryCode3)
      add(Countries.regionId, rowData.regionId)
      return where (Countries.countryId.eq(rowData.countryId))
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
) : TableRowData<CountriesUpdateBuilder, CountriesInsertBuilder>(Countries.metadata())
        
