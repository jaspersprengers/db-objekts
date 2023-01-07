package com.dbobjekts.testdb.acme.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.TableRowData
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase


/**           
 * Metadata object for db table COUNTRY.
 *
 * Primary key: id
 *
 * Foreign keys: [] 
 */
object Country:Table<CountryRow>("COUNTRY"), HasUpdateBuilder<CountryUpdateBuilder, CountryInsertBuilder> {
    /**
     * Represents db column CORE.COUNTRY.ID
     */
    val id = com.dbobjekts.metadata.column.VarcharColumn(this, "ID")
    /**
     * Represents db column CORE.COUNTRY.NAME
     */
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "NAME")
    override val columns: List<AnyColumn> = listOf(id,name)
    override fun toValue(values: List<Any?>) = CountryRow(values[0] as String,values[1] as String)
    override fun metadata(): WriteQueryAccessors<CountryUpdateBuilder, CountryInsertBuilder> = WriteQueryAccessors(CountryUpdateBuilder(), CountryInsertBuilder())
}

class CountryUpdateBuilder() : UpdateBuilderBase(Country) {
    fun id(value: String): CountryUpdateBuilder = put(Country.id, value)
    fun name(value: String): CountryUpdateBuilder = put(Country.name, value)
    
    /**
     * FOR INTERNAL USE ONLY
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as CountryRow
      add(Country.id, rowData.id)
      add(Country.name, rowData.name)
      return where (Country.id.eq(rowData.id))
    }    
        
}

class CountryInsertBuilder():InsertBuilderBase(){
    fun id(value: String): CountryInsertBuilder = put(Country.id, value)
    fun name(value: String): CountryInsertBuilder = put(Country.name, value)

    fun mandatoryColumns(id: String, name: String) : CountryInsertBuilder {
      mandatory(Country.id, id)
      mandatory(Country.name, name)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as CountryRow
      add(Country.id, rowData.id)
      add(Country.name, rowData.name)
      return execute()
    }    
        
}


data class CountryRow(
  val id: String,
  val name: String    
) : TableRowData<CountryUpdateBuilder, CountryInsertBuilder>(Country.metadata())
        
