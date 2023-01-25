package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

class ByteArrayColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<ByteArray>(table,name, ByteArray::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    //override //override fun distinct() = ByteArrayColumn(table, nameInTable, AggregateType.DISTINCT)

    override fun getValue(position: Int, resultSet: ResultSet): ByteArray? = resultSet.getBytes(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: ByteArray) =
        statement.setBytes(position, value)
}

class NullableByteArrayColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<ByteArray?>(table,name, Types.BINARY, ByteArray::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    //override //override fun distinct() = NullableByteArrayColumn(table, nameInTable, AggregateType.DISTINCT)

    override fun getValue(position: Int, resultSet: ResultSet): ByteArray? = resultSet.getBytes(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: ByteArray?) =
        statement.setBytes(position, value)
}
