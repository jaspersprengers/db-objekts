package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import com.dbobjekts.api.exception.DBObjektsException
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

interface NumericEnumColumn<E> {
    /**
     * Provides a concrete Enum element for an ordinal value. Example:
     * ```kotlin
     * override fun toEnum(ordinal: Int): AddressType = AddressType.values()[ordinal]
     * ```
     */
  fun toEnum(ordinal: Int): E
}

interface StringEnumColumn<E> {
    /**
     * Provides a concrete Enum element for an ordinal value. Example:
     * ```kotlin
     * override fun toEnum(ordinal: Int): AddressType = AddressType.values()[ordinal]
     * ```
     */
    fun toEnum(value: String): E
}

/**
 * Abstract [NonNullableColumn] that converts between instances of a custom Enum and an Int through the Enum's ordinal value.
 */
abstract class EnumAsIntColumn<E : Enum<E>>(
    table: AnyTable,
    name: String,
    private val enumClass: Class<E>,
    aggregateType: AggregateType?
) : NonNullableColumn<E>(name, table, enumClass, aggregateType), NumericEnumColumn<E> {
    constructor(table: AnyTable, name: String, enumClass: Class<E>) : this(table, name, enumClass, null)

    override fun distinct() =
        throw DBObjektsException("Missing override for distinct() method in concrete class. You must override it and return a copy of the column with AggregateType.DISTINCT")

    override fun getValue(position: Int, resultSet: ResultSet): E? = toEnum(resultSet.getInt(position))

    override fun setValue(position: Int, statement: PreparedStatement, value: E) = statement.setInt(position, value.ordinal)

}

/**
 * Abstract [NullableColumn] that converts between instances of a custom Enum and an Int through the Enum's ordinal value.
 */
abstract class NullableEnumAsIntColumn<E : Enum<E>>(
    table: AnyTable,
    name: String,
    enumClass: Class<E>,
    aggregateType: AggregateType?
) : NullableColumn<E?>(name, table, Types.VARCHAR, enumClass, aggregateType), NumericEnumColumn<E> {
    constructor(table: AnyTable, name: String, enumClass: Class<E>) : this(table, name, enumClass, null)

    override fun distinct() =
        throw DBObjektsException("Missing override for distinct() method in concrete class. You must override it and return a copy of the column with AggregateType.DISTINCT")

    override fun getValue(position: Int, resultSet: ResultSet): E? = toEnum(resultSet.getInt(position))

    override fun setValue(position: Int, statement: PreparedStatement, value: E?) = statement.setInt(position, value?.ordinal ?: -1)

}

/**
 * Abstract [NullableColumn] that converts between instances of a custom Enum and a String.
 *
 * To convert an Enum to a String, its toString() value is called, which defaults to the Enum's name property (the Enum literal expressed as a String)
 * If you need something else (like a description field), you need to override toString()
 *
 * To convert a String to an Enum, you override the toEnum() method
 */
abstract class EnumAsStringColumn<E : Enum<E>>(
    table: AnyTable,
    name: String,
    enumClass: Class<E>,
    aggregateType: AggregateType?
) : NonNullableColumn<E>(name, table, enumClass, aggregateType), StringEnumColumn<E> {
    constructor(table: AnyTable, name: String, enumClass: Class<E>) : this(table, name, enumClass, null)

    override fun distinct() =
        throw DBObjektsException("Missing override for distinct() method in concrete class. You must override it and return a copy of the column with AggregateType.DISTINCT")

    override fun getValue(position: Int, resultSet: ResultSet): E? = toEnum(resultSet.getString(position))

    override fun setValue(position: Int, statement: PreparedStatement, value: E) = statement.setString(position, value.toString())

}

/**
 * Abstract [NullableColumn] that converts between instances of a custom Enum and a String.
 *
 * To convert an Enum to a String, its toString() value is called, which defaults to the Enum's name property (the Enum literal expressed as a String). If you need something else (like a description field), you need to override toString()
 *
 * To convert a String to an Enum, you override the toEnum() method
 */
abstract class NullableEnumAsStringColumn<E : Enum<E>>(
    table: AnyTable,
    name: String,
    enumClass: Class<E>,
    aggregateType: AggregateType?
) : NullableColumn<E?>(name, table, Types.VARCHAR, enumClass, aggregateType), StringEnumColumn<E> {
    constructor(table: AnyTable, name: String, enumClass: Class<E>) : this(table, name, enumClass, null)

    override fun distinct() =
        throw DBObjektsException("Missing override for distinct() method in concrete class. You must override it and return a copy of the column with AggregateType.DISTINCT")

    override fun getValue(position: Int, resultSet: ResultSet): E? = resultSet.getString(position)?.let { toEnum(it) }

    override fun setValue(position: Int, statement: PreparedStatement, value: E?) = statement.setString(position, value.toString())

}
