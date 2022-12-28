package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

class ByteArrayColumn(table: Table, name: String) : NonNullableColumn<ByteArray>(name, table, ByteArray::class.java){
    override val nullable: NullableColumn<ByteArray?> = NullableByteArrayColumn(table, name)
    override fun getValue(position: Int, resultSet: ResultSet): ByteArray? = resultSet.getBytes(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: ByteArray) =
        statement.setBytes(position, value)
}

class NullableByteArrayColumn(table: Table, name: String) : NullableColumn<ByteArray?>(name, table, Types.BINARY, ByteArray::class.java){
    override fun getValue(position: Int, resultSet: ResultSet): ByteArray? = resultSet.getBytes(position)
    override fun setValue(position: Int, statement: PreparedStatement, value: ByteArray?) =
        statement.setBytes(position, value)
}
