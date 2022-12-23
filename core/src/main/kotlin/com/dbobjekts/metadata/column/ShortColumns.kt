package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

open class ShortColumn(table: Table, name: String) : NonNullableColumn<Short>(name, table, Short::class.java){
    override val nullable: NullableColumn<Short?> = NullableShortColumn(table, name)
    override fun setValue(position: Int, statement: PreparedStatement, value: Short) = statement.setShort(position, value)
    override fun getValue(position: Int, resultSet: ResultSet): Short = resultSet.getShort(position)
    override fun defaultValue() = 0.toShort()
}

open class NullableShortColumn(table: Table, name: String) :  NullableColumn<Short?>(name, table, Types.SMALLINT, Short::class.java){
    override fun setValue(position: Int, statement: PreparedStatement, value: Short?) = statement.setShort(position, value!!)
    override fun getValue(position: Int, resultSet: ResultSet): Short? = resultSet.getShort(position)
}