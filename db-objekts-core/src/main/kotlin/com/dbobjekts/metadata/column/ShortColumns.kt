package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

open class ShortColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<Short>(table,name, Short::class.java, aggregateType), IntegerNumericColumn by IntegerNumericColumnCloner(table,  name){
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun setValue(position: Int, statement: PreparedStatement, value: Short) = statement.setShort(position, value)
    override fun getValue(position: Int, resultSet: ResultSet): Short = resultSet.getShort(position)
}

open class NullableShortColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<Short?>(table,name, Types.SMALLINT, Short::class.java, aggregateType), IntegerNumericColumn by IntegerNumericColumnCloner(table,  name){
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun setValue(position: Int, statement: PreparedStatement, value: Short?) = statement.setShort(position, value!!)
    override fun getValue(position: Int, resultSet: ResultSet): Short? = resultSet.getShort(position)
}
