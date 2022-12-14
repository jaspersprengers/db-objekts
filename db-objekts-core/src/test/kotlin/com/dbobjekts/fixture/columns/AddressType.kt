package com.dbobjekts.fixture.columns

import com.dbobjekts.api.AnyTable
import com.dbobjekts.metadata.column.EnumAsIntColumn
import com.dbobjekts.metadata.column.EnumAsStringColumn
import com.dbobjekts.metadata.column.NullableEnumAsIntColumn
import com.dbobjekts.metadata.column.NullableEnumAsStringColumn
import java.io.Serializable

enum class AddressType : Serializable {
    HOME, WORK
}

class AddressTypeAsStringColumn(table: AnyTable, name: String) : EnumAsStringColumn<AddressType>(table, name, AddressType::class.java) {
    override val nullable = NullableAddressTypeAsStringColumn(table, name)
    override fun toEnum(name: String): AddressType = AddressType.valueOf(name)
}


class NullableAddressTypeAsStringColumn(table: AnyTable, name: String) :
    NullableEnumAsStringColumn<AddressType>(table, name, AddressType::class.java) {
    override fun toEnum(name: String): AddressType = AddressType.valueOf(name)
}

class AddressTypeAsIntegerColumn(table: AnyTable, name: String) : EnumAsIntColumn<AddressType>(table, name, AddressType::class.java) {
    override val nullable = NullableAddressTypeAsIntegerColumn(table, name)
    override fun toEnum(ordinal: Int): AddressType = AddressType.values()[ordinal]
}


class NullableAddressTypeAsIntegerColumn(table: AnyTable, name: String) :
    NullableEnumAsIntColumn<AddressType>(table, name, AddressType::class.java) {
    override fun toEnum(ordinal: Int): AddressType = AddressType.values()[ordinal]
}




