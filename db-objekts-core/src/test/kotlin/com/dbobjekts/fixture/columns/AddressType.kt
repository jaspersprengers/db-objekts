package com.dbobjekts.fixture.columns

import com.dbobjekts.api.AnyTable
import com.dbobjekts.metadata.column.*
import java.io.Serializable

enum class AddressType : Serializable {
    HOME, WORK
}

class AddressTypeAsStringColumn(table: AnyTable, name: String, aggregateType: AggregateType? = null) : EnumAsStringColumn<AddressType>(table, name, AddressType::class.java, aggregateType) {
}

class NullableAddressTypeAsStringColumn(table: AnyTable, name: String, aggregateType: AggregateType? = null) :
    NullableEnumAsStringColumn<AddressType>(table, name, AddressType::class.java, aggregateType) {
}

class AddressTypeAsIntegerColumn(table: AnyTable, name: String, aggregateType: AggregateType? = null) : EnumAsIntColumn<AddressType>(table, name, AddressType::class.java, aggregateType) {
}

class NullableAddressTypeAsIntegerColumn(table: AnyTable, name: String, aggregateType: AggregateType? = null) :
    NullableEnumAsIntColumn<AddressType>(table, name, AddressType::class.java, aggregateType) {
}




