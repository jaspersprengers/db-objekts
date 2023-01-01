package com.dbobjekts.mariadb.testdb.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.TableRowData
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Address:Table<AddressRow>("ADDRESS"), HasUpdateBuilder<AddressUpdateBuilder, AddressInsertBuilder> {
    val id = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "id")
    val street = com.dbobjekts.metadata.column.VarcharColumn(this, "street")
    val countryId = com.dbobjekts.metadata.column.ForeignKeyVarcharColumn(this, "country_id", Country.id)
    override val columns: List<AnyColumn> = listOf(id,street,countryId)
    override fun toValue(values: List<Any?>) = AddressRow(values[0] as Long,values[1] as String,values[2] as String)
    override fun metadata(): WriteQueryAccessors<AddressUpdateBuilder, AddressInsertBuilder> = WriteQueryAccessors(AddressUpdateBuilder(), AddressInsertBuilder())
}

class AddressUpdateBuilder() : UpdateBuilderBase(Address) {
    fun street(value: String): AddressUpdateBuilder = put(Address.street, value)
    fun countryId(value: String): AddressUpdateBuilder = put(Address.countryId, value)

    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as AddressRow
      add(Address.id, rowData.id)
      add(Address.street, rowData.street)
      add(Address.countryId, rowData.countryId)
      return where (Address.id.eq(rowData.id))
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
) : TableRowData<AddressUpdateBuilder, AddressInsertBuilder>(Address.metadata())
        
