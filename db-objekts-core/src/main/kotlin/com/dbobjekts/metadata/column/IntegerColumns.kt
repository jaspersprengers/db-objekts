package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

open class IntegerColumn(table: Table, name: String) : NonNullableColumn<Int>(name, table, Int::class.java){
    override val nullable: NullableColumn<Int?> = NullableIntegerColumn(table, name)
    override fun setValue(position: Int, statement: PreparedStatement, value: Int) = statement.setInt(position, value)
    override fun getValue(position: Int, resultSet: ResultSet): Int = resultSet.getInt(position)
}

open class NullableIntegerColumn(table: Table, name: String) :  NullableColumn<Int?>(name, table, Types.INTEGER, Int::class.java){
    override fun setValue(position: Int, statement: PreparedStatement, value: Int?) = statement.setInt(position, value!!)
    override fun getValue(position: Int, resultSet: ResultSet): Int? = resultSet.getInt(position)
}
