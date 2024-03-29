package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

/**
 * Represents a database column whose type is converted from and to a Float
 *
 * @param name    The column name in the corresponding database table
 */
open class FloatColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<Float>(table,name, Float::class.java, aggregateType), FloatingPointNumericColumn by FloatingPointNumericColumnCloner(table, name){
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun getValue(position: Int, resultSet: ResultSet): Float = resultSet.getFloat(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: Float) =
        statement.setFloat(position, value)
}

open class NullableFloatColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<Float?>(table,name, Types.FLOAT, Float::class.java, aggregateType), FloatingPointNumericColumn by FloatingPointNumericColumnCloner(table, name){
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun getValue(position: Int, resultSet: ResultSet): Float? = resultSet.getFloat(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: Float?) =
        statement.setFloat(position, value!!)
}

