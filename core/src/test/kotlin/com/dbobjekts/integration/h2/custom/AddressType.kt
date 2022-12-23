package com.dbobjekts.integration.h2.custom

import com.dbobjekts.api.CustomColumnTypeMapper
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.*
import java.io.Serializable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

enum class AddressType : Serializable {
    HOME, WORK
}

class AddressTypeAsStringColumn(table: Table, name: String) : EnumAsStringColumn<AddressType>(table, name, AddressType::class.java) {
    override val nullable = NullableAddressTypeAsStringColumn(table, name)
    override fun toEnum(name: String): AddressType = AddressType.valueOf(name)
}


class NullableAddressTypeAsStringColumn(table: Table, name: String) :
    NullableEnumAsStringColumn<AddressType>(table, name, AddressType::class.java) {
    override fun toEnum(name: String): AddressType = AddressType.valueOf(name)
}

class AddressTypeAsIntegerColumn(table: Table, name: String) : EnumAsIntColumn<AddressType>(table, name, AddressType::class.java) {
    override val nullable = NullableAddressTypeAsIntegerColumn(table, name)
    override fun toEnum(ordinal: Int): AddressType = AddressType.values()[ordinal]
}


class NullableAddressTypeAsIntegerColumn(table: Table, name: String) :
    NullableEnumAsIntColumn<AddressType>(table, name, AddressType::class.java) {
    override fun toEnum(ordinal: Int): AddressType = AddressType.values()[ordinal]
}




