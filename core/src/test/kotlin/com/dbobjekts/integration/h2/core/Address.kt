package com.dbobjekts.integration.h2.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase

object Address : Table("ADDRESS"), HasUpdateBuilder<AddressUpdateBuilder, AddressInsertBuilder> {
    val id = com.dbobjekts.metadata.column.SequenceKeyLongColumn(this, "ID", "ADDRESS_SEQ")
    val street = com.dbobjekts.metadata.column.VarcharColumn(this, "STREET")
    val countryId = com.dbobjekts.metadata.column.ForeignKeyVarcharColumn(this, "COUNTRY_ID", Country.id)
    override val columns: List<AnyColumn> = listOf(id, street, countryId)
    override fun metadata(): WriteQueryAccessors<AddressUpdateBuilder, AddressInsertBuilder> =
        WriteQueryAccessors(AddressUpdateBuilder(), AddressInsertBuilder())
}

class AddressUpdateBuilder() : UpdateBuilderBase(Address) {
    fun street(value: String): AddressUpdateBuilder = put(Address.street, value)
    fun countryId(value: String): AddressUpdateBuilder = put(Address.countryId, value)
}

class AddressInsertBuilder() : InsertBuilderBase() {
    fun street(value: String): AddressInsertBuilder = put(Address.street, value)
    fun countryId(value: String): AddressInsertBuilder = put(Address.countryId, value)

    fun mandatoryColumns(street: String, countryId: String): AddressInsertBuilder {
        mandatory(Address.street, street)
        mandatory(Address.countryId, countryId)
        return this
    }

}


