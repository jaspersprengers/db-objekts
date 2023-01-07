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
 * Metadata object for db table ADDRESS.
 *
 * Primary key: id
 *
 * Foreign keys: [CORE.ADDRESS.COUNTRY_ID to CORE.COUNTRY.ID] 
 */
object Address:Table<AddressRow>("ADDRESS"), HasUpdateBuilder<AddressUpdateBuilder, AddressInsertBuilder> {
    /**
     * Represents db column CORE.ADDRESS.ID
     */
    val id = com.dbobjekts.metadata.column.SequenceKeyLongColumn(this, "ID", "ADDRESS_SEQ")
    /**
     * Represents db column CORE.ADDRESS.STREET
     */
    val street = com.dbobjekts.metadata.column.VarcharColumn(this, "STREET")
    /**
     * Represents db column CORE.ADDRESS.POSTCODE
     */
    val postcode = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "POSTCODE")
    /**
     * Represents db column CORE.ADDRESS.COUNTRY_ID
     *
     * Foreign key to CORE.COUNTRY.ID
     */
    val countryId = com.dbobjekts.metadata.column.ForeignKeyVarcharColumn(this, "COUNTRY_ID", Country.id)
    override val columns: List<AnyColumn> = listOf(id,street,postcode,countryId)
    override fun toValue(values: List<Any?>) = AddressRow(values[0] as Long,values[1] as String,values[2] as String?,values[3] as String)
    override fun metadata(): WriteQueryAccessors<AddressUpdateBuilder, AddressInsertBuilder> = WriteQueryAccessors(AddressUpdateBuilder(), AddressInsertBuilder())
}

class AddressUpdateBuilder() : UpdateBuilderBase(Address) {
    fun street(value: String): AddressUpdateBuilder = put(Address.street, value)
    fun postcode(value: String?): AddressUpdateBuilder = put(Address.postcode, value)
    fun countryId(value: String): AddressUpdateBuilder = put(Address.countryId, value)
    
    /**
     * FOR INTERNAL USE ONLY
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as AddressRow
      add(Address.id, rowData.id)
      add(Address.street, rowData.street)
      add(Address.postcode, rowData.postcode)
      add(Address.countryId, rowData.countryId)
      return where (Address.id.eq(rowData.id))
    }    
        
}

class AddressInsertBuilder():InsertBuilderBase(){
    fun street(value: String): AddressInsertBuilder = put(Address.street, value)
    fun postcode(value: String?): AddressInsertBuilder = put(Address.postcode, value)
    fun countryId(value: String): AddressInsertBuilder = put(Address.countryId, value)

    fun mandatoryColumns(street: String, countryId: String) : AddressInsertBuilder {
      mandatory(Address.street, street)
      mandatory(Address.countryId, countryId)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as AddressRow
      add(Address.street, rowData.street)
      add(Address.postcode, rowData.postcode)
      add(Address.countryId, rowData.countryId)
      return execute()
    }    
        
}


data class AddressRow(
val id: Long = 0,
  val street: String,
  val postcode: String?,
  val countryId: String    
) : TableRowData<AddressUpdateBuilder, AddressInsertBuilder>(Address.metadata())
        
