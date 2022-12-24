package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

/**
 * Represents a database column whose type is converted from and to a Float
 *
 * @param name    The column name in the corresponding database table
 */
class FloatColumn(table: Table, name: String) : NonNullableColumn<Float>(name, table, Float::class.java){
    override val nullable: NullableColumn<Float?> = NullableFloatColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): Float = resultSet.getFloat(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: Float) =
        statement.setFloat(position, value as Float)
    override fun defaultValue() = 0f
}

class NullableFloatColumn(table: Table, name: String) : NullableColumn<Float?>(name, table, Types.FLOAT, Float::class.java){
    override fun getValue(position: Int, resultSet: ResultSet): Float? = resultSet.getFloat(position)

    override fun setValue(position: Int, statement: PreparedStatement, value: Float?) =
        statement.setFloat(position, value!!)
}

