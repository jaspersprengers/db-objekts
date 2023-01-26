package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import com.dbobjekts.api.exception.DBObjektsException
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

/**
 * Abstract [NonNullableColumn] that converts between instances of a custom Enum and an Int through the Enum's ordinal value.
 */
open class EnumAsIntColumn<E : Enum<E>>(
    table: AnyTable,
    name: String,
    private val enumClass: Class<E>,
    aggregateType: AggregateType?= null
) : NonNullableColumn<E>(table,name, enumClass, aggregateType), IsEnumColumn{

    override fun getValue(position: Int, resultSet: ResultSet): E? = toEnum(resultSet.getInt(position))
    override fun setValue(position: Int, statement: PreparedStatement, value: E) = statement.setInt(position, value.ordinal)

    @Suppress("UNCHECKED_CAST")
    internal fun toEnum(ordinal: Int) = enumClass.enumConstants[ordinal] as E
}



/**
 * Abstract [NullableColumn] that converts between instances of a custom Enum and an Int through the Enum's ordinal value.
 */
open class NullableEnumAsIntColumn<E : Enum<E>>(
    table: AnyTable,
    name: String,
    private val enumClass: Class<E>,
    aggregateType: AggregateType?= null
) : NullableColumn<E?>(table,name, Types.VARCHAR, enumClass, aggregateType), IsEnumColumn{

    override fun getValue(position: Int, resultSet: ResultSet): E? = toEnum(resultSet.getInt(position))
    override fun setValue(position: Int, statement: PreparedStatement, value: E?) = statement.setInt(position, value?.ordinal ?: -1)

    @Suppress("UNCHECKED_CAST")
    internal fun toEnum(ordinal: Int) = enumClass.enumConstants[ordinal] as E
}

/**
 * Abstract [NullableColumn] that converts between instances of a custom Enum and a String.
 *
 * To convert an Enum to a String, its toString() value is called, which defaults to the Enum's name property (the Enum literal expressed as a String)
 * If you need something else (like a description field), you need to override toString()
 *
 * To convert a String to an Enum, you override the toEnum() method
 */
open class EnumAsStringColumn<E : Enum<E>>(
    table: AnyTable,
    name: String,
    private val enumClass: Class<E>,
    aggregateType: AggregateType? = null
) : NonNullableColumn<E>(table,name, enumClass, aggregateType), IsEnumColumn{

    override fun getValue(position: Int, resultSet: ResultSet): E? = toEnum(resultSet.getString(position))

    override fun setValue(position: Int, statement: PreparedStatement, value: E) = statement.setString(position, value.toString())

    @Suppress("UNCHECKED_CAST")
    internal fun toEnum(value: String) = enumClass.enumConstants.filterIsInstance(Enum::class.java).first { it.name == value } as E

}

/**
 * Abstract [NullableColumn] that converts between instances of a custom Enum and a String.
 *
 * To convert an Enum to a String, its toString() value is called, which defaults to the Enum's name property (the Enum literal expressed as a String). If you need something else (like a description field), you need to override toString()
 *
 * To convert a String to an Enum, you override the toEnum() method
 */
open class NullableEnumAsStringColumn<E : Enum<E>>(
    table: AnyTable,
    name: String,
    private val enumClass: Class<E>,
    aggregateType: AggregateType? = null
) : NullableColumn<E?>(table,name, Types.VARCHAR, enumClass, aggregateType), IsEnumColumn{

    override fun getValue(position: Int, resultSet: ResultSet): E? = resultSet.getString(position)?.let { toEnum(it) }

    override fun setValue(position: Int, statement: PreparedStatement, value: E?) = statement.setString(position, value.toString())

    @Suppress("UNCHECKED_CAST")
    internal fun toEnum(value: String): E = enumClass.enumConstants.filterIsInstance(Enum::class.java).first { it.name == value } as E

}
