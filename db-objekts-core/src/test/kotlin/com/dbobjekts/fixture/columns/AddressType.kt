package com.dbobjekts.fixture.columns

import com.dbobjekts.api.AnyTable
import com.dbobjekts.metadata.column.AggregateType
import com.dbobjekts.metadata.column.EnumAsStringColumn
import com.dbobjekts.metadata.column.NullableEnumAsStringColumn
import java.io.Serializable
import java.sql.PreparedStatement

enum class AddressType(val description: String) : Serializable {
    HOME("HOME"), WORK("WORK");
    companion object {
        fun fromDescription(desc: String) = AddressType.values().firstOrNull { it.description == desc }?:throw IllegalArgumentException("Invalid address type: $desc")
    }
}

class AddressTypeColumn(table: AnyTable, name: String, aggregateType: AggregateType? = null) :
    EnumAsStringColumn<AddressType>(table, name, AddressType::class.java, aggregateType) {
    override fun toEnum(value: String): AddressType = AddressType.fromDescription(value)
}

class NullableAddressTypeColumn(table: AnyTable, name: String, aggregateType: AggregateType? = null) :
    NullableEnumAsStringColumn<AddressType>(table, name, AddressType::class.java, aggregateType) {

    override fun setValue(position: Int, statement: PreparedStatement, value: AddressType?) {
        super.setValue(position, statement, value?:AddressType.UNKNOWN)
    }
    override fun toEnum(value: String?) = value?.let { AddressType.fromDescription(it) } ?: AddressType.HOME
}



