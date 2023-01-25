package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types


open class ByteColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<Byte>(table,name, Byte::class.java, aggregateType), IntegerNumericColumn by IntegerNumericColumnCloner(table, name) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    //override //override fun distinct() = ByteColumn(table, nameInTable, AggregateType.DISTINCT)

    override fun setValue(position: Int, statement: PreparedStatement, value: Byte) = statement.setByte(position, value)
    override fun getValue(position: Int, resultSet: ResultSet): Byte = resultSet.getByte(position)
}

open class NullableByteColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<Byte?>(table,name, Types.TINYINT, Byte::class.java, aggregateType), IntegerNumericColumn by IntegerNumericColumnCloner(table, name) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    //override //override fun distinct() = NullableByteColumn(table, nameInTable, AggregateType.DISTINCT)

    override fun setValue(position: Int, statement: PreparedStatement, value: Byte?) = statement.setByte(position, value!!)
    override fun getValue(position: Int, resultSet: ResultSet): Byte? = resultSet.getByte(position)
}
