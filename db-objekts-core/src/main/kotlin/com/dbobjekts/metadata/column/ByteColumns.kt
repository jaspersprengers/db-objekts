package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types


open class ByteColumn(table: Table, name: String) : NonNullableColumn<Byte>(name, table, Byte::class.java) {
    override val nullable: NullableColumn<Byte?> = NullableByteColumn(table, name)
    override fun setValue(position: Int, statement: PreparedStatement, value: Byte) = statement.setByte(position, value)
    override fun getValue(position: Int, resultSet: ResultSet): Byte = resultSet.getByte(position)
}

open class NullableByteColumn(table: Table, name: String) : NullableColumn<Byte?>(name, table, Types.TINYINT, Byte::class.java) {
    override fun setValue(position: Int, statement: PreparedStatement, value: Byte?) = statement.setByte(position, value!!)
    override fun getValue(position: Int, resultSet: ResultSet): Byte? = resultSet.getByte(position)
}
