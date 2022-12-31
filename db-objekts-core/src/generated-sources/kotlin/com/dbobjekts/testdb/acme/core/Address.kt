package com.dbobjekts.testdb.acme.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.Entity
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Address:Table<AddressRow>("ADDRESS"), HasUpdateBuilder<AddressUpdateBuilder, AddressInsertBuilder> {
    val id = com.dbobjekts.metadata.column.SequenceKeyLongColumn(this, "ID", "ADDRESS_SEQ")
    val street = com.dbobjekts.metadata.column.VarcharColumn(this, "STREET")
    val postcode = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "POSTCODE")
    val countryId = com.dbobjekts.metadata.column.ForeignKeyVarcharColumn(this, "COUNTRY_ID", Country.id)
    override val columns: List<AnyColumn> = listOf(id,street,postcode,countryId)
    override fun toValue(values: List<Any?>) = AddressRow(values[0] as Long,values[1] as String,values[2] as String?,values[3] as String)
    override fun metadata(): WriteQueryAccessors<AddressUpdateBuilder, AddressInsertBuilder> = WriteQueryAccessors(AddressUpdateBuilder(), AddressInsertBuilder())
}

class AddressUpdateBuilder() : UpdateBuilderBase(Address) {
    fun street(value: String): AddressUpdateBuilder = put(Address.street, value)
    fun postcode(value: String?): AddressUpdateBuilder = put(Address.postcode, value)
    fun countryId(value: String): AddressUpdateBuilder = put(Address.countryId, value)
    override fun updateRow(entity: Entity<*, *>): Long = throw RuntimeException()
}

class AddressInsertBuilder():InsertBuilderBase(){
       fun street(value: String): AddressInsertBuilder = put(Address.street, value)
    fun postcode(value: String?): AddressInsertBuilder = put(Address.postcode, value)
    fun countryId(value: String): AddressInsertBuilder = put(Address.countryId, value)
    override fun insertRow(entity: Entity<*, *>): Long = throw RuntimeException()

    fun mandatoryColumns(street: String, countryId: String) : AddressInsertBuilder {
      mandatory(Address.street, street)
      mandatory(Address.countryId, countryId)
      return this
    }

}

data class AddressRow(
    val id: Long,
    val street: String,
    val postcode: String?,
    val countryId: String)
