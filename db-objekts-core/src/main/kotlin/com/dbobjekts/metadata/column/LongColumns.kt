package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types


open class LongColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<Long>(table,name, Long::class.java, aggregateType), IntegerNumericColumn by IntegerNumericColumnCloner(table, name){
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun setValue(position: Int, statement: PreparedStatement, value: Long) = statement.setLong(position, value)
    override fun getValue(position: Int, resultSet: ResultSet): Long = resultSet.getLong(position)

}

open class NullableLongColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<Long?>(table,name, Types.NUMERIC, Long::class.java, aggregateType), IntegerNumericColumn by IntegerNumericColumnCloner(table, name){

    constructor(table: AnyTable, name: String) : this(table, name, null)

    //override //override fun distinct() = NullableLongColumn(table, nameInTable, AggregateType.DISTINCT)

    override fun setValue(position: Int, statement: PreparedStatement, value: Long?) = statement.setLong(position, value!!)
    override fun getValue(position: Int, resultSet: ResultSet): Long? = resultSet.getLong(position)
}
