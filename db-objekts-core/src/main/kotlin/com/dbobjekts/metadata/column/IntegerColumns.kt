package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

open class IntegerColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<Int>(table,name, Int::class.java, aggregateType), IntegerNumericColumn by IntegerNumericColumnCloner(table,  name){
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun setValue(position: Int, statement: PreparedStatement, value: Int) = statement.setInt(position, value)
    override fun getValue(position: Int, resultSet: ResultSet): Int = resultSet.getInt(position)
}

open class NullableIntegerColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<Int?>(table,name, Types.INTEGER, Int::class.java, aggregateType), IntegerNumericColumn by IntegerNumericColumnCloner(table,  name){
    constructor(table: AnyTable, name: String) : this(table, name, null)

    //override //override fun distinct() = NullableIntegerColumn(table, nameInTable, AggregateType.DISTINCT)

    override fun setValue(position: Int, statement: PreparedStatement, value: Int?) = statement.setInt(position, value!!)
    override fun getValue(position: Int, resultSet: ResultSet): Int? = resultSet.getInt(position)
}
