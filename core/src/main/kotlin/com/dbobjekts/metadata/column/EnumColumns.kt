package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

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
