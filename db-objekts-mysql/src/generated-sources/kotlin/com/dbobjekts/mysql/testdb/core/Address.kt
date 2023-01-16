package com.dbobjekts.mysql.testdb.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.TableRowData
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.AutoKeyLongColumn
import com.dbobjekts.metadata.column.ForeignKeyVarcharColumn
import com.dbobjekts.metadata.column.VarcharColumn
import com.dbobjekts.statement.WriteQueryAccessors
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase

/**           
 * Auto-generated metadata object for db table core.ADDRESS.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: id
 *
 * Foreign keys to: 
 * References by: core.COUNTRY,core.EMPLOYEE_ADDRESS
 */
object Address:Table<AddressRow>("ADDRESS"), HasUpdateBuilder<AddressUpdateBuilder, AddressInsertBuilder> {
    /**
     * Represents db column core.ADDRESS.id
     */
    val id = AutoKeyLongColumn(this, "id")
    /**
     * Represents db column core.ADDRESS.street
     */
    val street = VarcharColumn(this, "street")
    /**
     * Represents db column core.ADDRESS.country_id
     *
     * Foreign key to core.COUNTRY.id
     */
    val countryId = ForeignKeyVarcharColumn(this, "country_id", Country.id)
    override val columns: List<AnyColumn> = listOf(id,street,countryId)
    override fun toValue(values: List<Any?>) = AddressRow(values[0] as Long,values[1] as String,values[2] as String)
    override fun metadata(): WriteQueryAccessors<AddressUpdateBuilder, AddressInsertBuilder> = WriteQueryAccessors(AddressUpdateBuilder(), AddressInsertBuilder())
}

class AddressUpdateBuilder() : UpdateBuilderBase(Address) {
    fun street(value: String): AddressUpdateBuilder = put(Address.street, value)
    fun countryId(value: String): AddressUpdateBuilder = put(Address.countryId, value)
    
    /**
     * FOR INTERNAL USE ONLY
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as AddressRow
      add(Address.id, rowData.id)
      add(Address.street, rowData.street)
      add(Address.countryId, rowData.countryId)
      return where(Address.id.eq(rowData.id))
    }    
        
}

class AddressInsertBuilder():InsertBuilderBase(){
    fun street(value: String): AddressInsertBuilder = put(Address.street, value)
    fun countryId(value: String): AddressInsertBuilder = put(Address.countryId, value)

    fun mandatoryColumns(street: String, countryId: String) : AddressInsertBuilder {
      mandatory(Address.street, street)
      mandatory(Address.countryId, countryId)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as AddressRow
      add(Address.street, rowData.street)
      add(Address.countryId, rowData.countryId)
      return execute()
    }    
        
}


data class AddressRow(
val id: Long = 0,
  val street: String,
  val countryId: String    
) : TableRowData<AddressUpdateBuilder, AddressInsertBuilder>(Address.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>(Pair(Address.id, id))
}
        
