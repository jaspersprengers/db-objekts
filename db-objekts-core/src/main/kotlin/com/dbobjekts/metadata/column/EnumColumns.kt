package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import com.dbobjekts.api.exception.DBObjektsException
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

abstract class EnumAsIntColumn<E : Enum<E>>(
    table: AnyTable,
    name: String,
    private val enumClass: Class<E>,
    aggregateType: AggregateType?
) : NonNullableColumn<E>(name, table, enumClass, aggregateType) {
    constructor(table: AnyTable, name: String, enumClass: Class<E>) : this(table, name, enumClass, null)

    override fun distinct() =
        throw DBObjektsException("Missing override for distinct() method in concrete class. You must override it and return a copy of the column with AggregateType.DISTINCT")

    override fun getValue(position: Int, resultSet: ResultSet): E? = toEnum(resultSet.getInt(position))

    override fun setValue(position: Int, statement: PreparedStatement, value: E) = statement.setInt(position, value.ordinal)

    abstract fun toEnum(ordinal: Int): E

}

abstract class NullableEnumAsIntColumn<E : Enum<E>>(
    table: AnyTable,
    name: String,
    enumClass: Class<E>,
    aggregateType: AggregateType?
) : NullableColumn<E?>(name, table, Types.VARCHAR, enumClass, aggregateType) {
    constructor(table: AnyTable, name: String, enumClass: Class<E>) : this(table, name, enumClass, null)

    override fun distinct() =
        throw DBObjektsException("Missing override for distinct() method in concrete class. You must override it and return a copy of the column with AggregateType.DISTINCT")

    override fun getValue(position: Int, resultSet: ResultSet): E? = toEnum(resultSet.getInt(position))

    override fun setValue(position: Int, statement: PreparedStatement, value: E?) = statement.setInt(position, value?.ordinal ?: -1)

    abstract fun toEnum(ordinal: Int): E
}


abstract class EnumAsStringColumn<E : Enum<E>>(
    table: AnyTable,
    name: String,
    enumClass: Class<E>,
    aggregateType: AggregateType?
) : NonNullableColumn<E>(name, table, enumClass, aggregateType) {
    constructor(table: AnyTable, name: String, enumClass: Class<E>) : this(table, name, enumClass, null)

    override fun distinct() =
        throw DBObjektsException("Missing override for distinct() method in concrete class. You must override it and return a copy of the column with AggregateType.DISTINCT")

    override fun getValue(position: Int, resultSet: ResultSet): E? = toEnum(resultSet.getString(position))

    override fun setValue(position: Int, statement: PreparedStatement, value: E) = statement.setString(position, value.toString())

    abstract fun toEnum(name: String): E

}

abstract class NullableEnumAsStringColumn<E : Enum<E>>(
    table: AnyTable,
    name: String,
    enumClass: Class<E>,
    aggregateType: AggregateType?
) : NullableColumn<E?>(name, table, Types.VARCHAR, enumClass, aggregateType) {
    constructor(table: AnyTable, name: String, enumClass: Class<E>) : this(table, name, enumClass, null)

    override fun distinct() =
        throw DBObjektsException("Missing override for distinct() method in concrete class. You must override it and return a copy of the column with AggregateType.DISTINCT")

    override fun getValue(position: Int, resultSet: ResultSet): E? = resultSet.getString(position)?.let { toEnum(it) }

    override fun setValue(position: Int, statement: PreparedStatement, value: E?) = statement.setString(position, value.toString())

    abstract fun toEnum(name: String): E
}
