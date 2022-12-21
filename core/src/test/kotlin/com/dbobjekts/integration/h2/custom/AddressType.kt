package com.dbobjekts.integration.h2.custom

import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.NonNullableColumn
import com.dbobjekts.metadata.column.NullableColumn
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


abstract class EnumAsIntColumn<E : Enum<E>>(
    table: Table,
    name: String,
    enumClass: Class<E>
) : NonNullableColumn<E>(name, table, enumClass) {

    override fun getValue(position: Int, resultSet: ResultSet): E? = toEnum(resultSet.getInt(position))

    override fun setValue(position: Int, statement: PreparedStatement, value: E) = statement.setInt(position, value.ordinal)

    abstract fun toEnum(ordinal: Int): E

}

abstract class NullableEnumAsIntColumn<E : Enum<E>>(
    table: Table,
    name: String,
    enumClass: Class<E>
) : NullableColumn<E?>(name, table, Types.VARCHAR, enumClass) {
    override fun getValue(position: Int, resultSet: ResultSet): E? = toEnum(resultSet.getInt(position))

    override fun setValue(position: Int, statement: PreparedStatement, value: E?) = statement.setInt(position, value?.ordinal ?: -1)

    abstract fun toEnum(ordinal: Int): E
}


abstract class EnumAsStringColumn<E : Enum<E>>(
    table: Table,
    name: String,
    enumClass: Class<E>
) : NonNullableColumn<E>(name, table, enumClass) {

    override fun getValue(position: Int, resultSet: ResultSet): E? = toEnum(resultSet.getString(position))

    override fun setValue(position: Int, statement: PreparedStatement, value: E) = statement.setString(position, value.toString())

    abstract fun toEnum(name: String): E

}

abstract class NullableEnumAsStringColumn<E : Enum<E>>(
    table: Table,
    name: String,
    enumClass: Class<E>
) : NullableColumn<E?>(name, table, Types.VARCHAR, enumClass) {
    override fun getValue(position: Int, resultSet: ResultSet): E? = resultSet.getString(position)?.let { toEnum(it) }

    override fun setValue(position: Int, statement: PreparedStatement, value: E?) = statement.setString(position, value.toString())

    abstract fun toEnum(name: String): E
}

