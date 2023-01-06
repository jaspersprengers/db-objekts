package com.dbobjekts.mariadb.testdb.nation

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.TableRowData
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object CountryLanguages:Table<CountryLanguagesRow>("country_languages"), HasUpdateBuilder<CountryLanguagesUpdateBuilder, CountryLanguagesInsertBuilder> {
    val countryId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "country_id", Countries.countryId)
    val languageId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "language_id", Languages.languageId)
    val official = com.dbobjekts.metadata.column.NumberAsBooleanColumn(this, "official")
    override val columns: List<AnyColumn> = listOf(countryId,languageId,official)
    override fun toValue(values: List<Any?>) = CountryLanguagesRow(values[0] as Long,values[1] as Long,values[2] as Boolean)
    override fun metadata(): WriteQueryAccessors<CountryLanguagesUpdateBuilder, CountryLanguagesInsertBuilder> = WriteQueryAccessors(CountryLanguagesUpdateBuilder(), CountryLanguagesInsertBuilder())
}

class CountryLanguagesUpdateBuilder() : UpdateBuilderBase(CountryLanguages) {
    fun countryId(value: Long): CountryLanguagesUpdateBuilder = put(CountryLanguages.countryId, value)
    fun languageId(value: Long): CountryLanguagesUpdateBuilder = put(CountryLanguages.languageId, value)
    fun official(value: Boolean): CountryLanguagesUpdateBuilder = put(CountryLanguages.official, value)

    override fun updateRow(rowData: TableRowData<*, *>): Long = 
      throw StatementBuilderException("Sorry, but you cannot use row-based updates for table CountryLanguages. There must be exactly one column marked as primary key.")                
            
}

class CountryLanguagesInsertBuilder():InsertBuilderBase(){
    fun countryId(value: Long): CountryLanguagesInsertBuilder = put(CountryLanguages.countryId, value)
    fun languageId(value: Long): CountryLanguagesInsertBuilder = put(CountryLanguages.languageId, value)
    fun official(value: Boolean): CountryLanguagesInsertBuilder = put(CountryLanguages.official, value)

    fun mandatoryColumns(countryId: Long, languageId: Long, official: Boolean) : CountryLanguagesInsertBuilder {
      mandatory(CountryLanguages.countryId, countryId)
      mandatory(CountryLanguages.languageId, languageId)
      mandatory(CountryLanguages.official, official)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as CountryLanguagesRow
      add(CountryLanguages.countryId, rowData.countryId)
      add(CountryLanguages.languageId, rowData.languageId)
      add(CountryLanguages.official, rowData.official)
      return execute()
    }    
        
}


data class CountryLanguagesRow(
  val countryId: Long,
  val languageId: Long,
  val official: Boolean    
) : TableRowData<CountryLanguagesUpdateBuilder, CountryLanguagesInsertBuilder>(CountryLanguages.metadata())
        
