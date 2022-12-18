package com.dbobjekts.integration.h2.custom

import com.dbobjekts.metadata.DefaultTable
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.*
import java.io.Serializable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

enum class AddressType : Serializable {
    HOME, WORK
}

/*class AddressTypeColumn(table: Table, name: String) : EnumAsStringColumn<AddressType>(table, name) {
    override fun parse(str: String): AddressType = AddressType.valueOf(str)
    override val nullable: NullableColumn<AddressType?> = NullableAddressTypeColumn::class.java
    override val valueClass: Class<*> = AddressType::class.java
}*/

/*
class NullableAddressTypeColumn(table: Table, name: String) : NullableEnumAsStringColumn<AddressType>(table, name) {
    override fun parse(str: String): AddressType = AddressType.valueOf(str)

    override val valueClass: Class<*> = AddressType::class.java
}

abstract class EnumAsIntegerColumn<E : Enum<E>>(
    table: Table,
    name: String
) : NonNullableColumn<E>(name, table) {
    override fun getValue(position: Int, resultSet: ResultSet): E? = resultSet.getInt(position)?.let { parse(it) }

    override fun setValue(position: Int, statement: PreparedStatement, value: E) = statement.setInt(position, value.ordinal)

    abstract fun parse(ordinal: Int): E

}

abstract class NullableEnumAsIntegerColumn<E : Enum<E>>(
    table: Table,
    name: String
) : NullableColumn<E?>(name, table, Types.VARCHAR) {
    override fun getValue(position: Int, resultSet: ResultSet): E? = resultSet.getInt(position)?.let { parse(it) }

    override fun setValue(position: Int, statement: PreparedStatement, value: E?) = statement.setInt(position, value?.ordinal ?: -1)

    abstract fun parse(ordinal: Int): E?

}

abstract class EnumAsStringColumn<E : Enum<E>>(
    table: Table,
    name: String
) : NonNullableColumn<E>(name, table) {
    override fun getValue(position: Int, resultSet: ResultSet): E? = resultSet.getString(position)?.let { parse(it) }

    override fun setValue(position: Int, statement: PreparedStatement, value: E) = statement.setString(position, value.toString())

    abstract fun parse(name: String): E

}

abstract class NullableEnumAsStringColumn<E : Enum<E>>(
    table: Table,
    name: String
) : NullableColumn<E?>(name, table, Types.VARCHAR) {
    override fun getValue(position: Int, resultSet: ResultSet): E? = resultSet.getString(position)?.let { parse(it) }

    override fun setValue(position: Int, statement: PreparedStatement, value: E?) = statement.setString(position, value.toString())

    abstract fun parse(ordinal: String): E?

}
*/
