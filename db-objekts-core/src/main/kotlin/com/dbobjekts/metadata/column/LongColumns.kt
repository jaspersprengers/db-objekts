package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types


open class LongColumn(table: Table, name: String) : NonNullableColumn<Long>(name, table, Long::class.java){
    override val nullable: NullableColumn<Long?> = NullableLongColumn(table, name)
    override fun setValue(position: Int, statement: PreparedStatement, value: Long) = statement.setLong(position, value)
    override fun getValue(position: Int, resultSet: ResultSet): Long = resultSet.getLong(position)
}

open class NullableLongColumn(table: Table, name: String) :  NullableColumn<Long?>(name, table, Types.NUMERIC, Long::class.java){
    override fun setValue(position: Int, statement: PreparedStatement, value: Long?) = statement.setLong(position, value!!)
    override fun getValue(position: Int, resultSet: ResultSet): Long? = resultSet.getLong(position)
}
