package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

/**
 * Represents a database column whose type is converted from and to a Boolean
 *
 * @param name    The column name in the corresponding database table
 */
open class BooleanColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<Boolean>(table,name, Boolean::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun getValue(position: Int, resultSet: ResultSet): Boolean = resultSet.getBoolean(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: Boolean) =
        statement.setBoolean(position, value)
}

open class NullableBooleanColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<Boolean?>(table,name, Types.BOOLEAN, Boolean::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun getValue(position: Int, resultSet: ResultSet): Boolean? = resultSet.getBoolean(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: Boolean?) =
        statement.setBoolean(position, value as Boolean)
}

open class NumberAsBooleanColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<Boolean>(table,name, Boolean::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun getValue(position: Int, resultSet: ResultSet): Boolean = resultSet.getInt(position) == 1

    override fun setValue(position: Int, statement: PreparedStatement, value: Boolean) =
        statement.setInt(position, if (value) 1 else 0)
}

open class NullableNumberAsBooleanColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<Boolean?>(table,name, Types.INTEGER, Boolean::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun getValue(position: Int, resultSet: ResultSet): Boolean? = resultSet.getInt(position) == 1

    override fun setValue(position: Int, statement: PreparedStatement, value: Boolean?) =
        statement.setInt(position, if (value != null && value) 1 else 0)
}
