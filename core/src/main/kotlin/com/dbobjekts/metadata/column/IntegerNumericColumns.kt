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

open class ShortColumn(table: Table, name: String) : NonNullableColumn<Short>(name, table, Short::class.java){
    override val nullable: NullableColumn<Short?> = NullableShortColumn(table, name)
    override fun setValue(position: Int, statement: PreparedStatement, value: Short) = statement.setShort(position, value)
    override fun getValue(position: Int, resultSet: ResultSet): Short = resultSet.getShort(position)
}

open class NullableShortColumn(table: Table, name: String) :  NullableColumn<Short?>(name, table, Types.SMALLINT, Short::class.java){
    override fun setValue(position: Int, statement: PreparedStatement, value: Short?) = statement.setShort(position, value!!)
    override fun getValue(position: Int, resultSet: ResultSet): Short? = resultSet.getShort(position)
}

open class LongColumn(table: Table, name: String) : NonNullableColumn<Long>(name, table, Long::class.java){
    override val nullable: NullableColumn<Long?> = NullableLongColumn(table, name)
    override fun setValue(position: Int, statement: PreparedStatement, value: Long) = statement.setLong(position, value)
    override fun getValue(position: Int, resultSet: ResultSet): Long = resultSet.getLong(position)
}

open class NullableLongColumn(table: Table, name: String) :  NullableColumn<Long?>(name, table, Types.NUMERIC, Long::class.java){
    override fun setValue(position: Int, statement: PreparedStatement, value: Long?) = statement.setLong(position, value!!)
    override fun getValue(position: Int, resultSet: ResultSet): Long? = resultSet.getLong(position)
}

open class ByteColumn(table: Table, name: String) : NonNullableColumn<Byte>(name, table, Byte::class.java){
    override val nullable: NullableColumn<Byte?> = NullableByteColumn(table, name)
    override fun setValue(position: Int, statement: PreparedStatement, value: Byte) = statement.setByte(position, value)
    override fun getValue(position: Int, resultSet: ResultSet): Byte = resultSet.getByte(position)
}

open class NullableByteColumn(table: Table, name: String) :  NullableColumn<Byte?>(name, table, Types.TINYINT, Byte::class.java){
    override fun setValue(position: Int, statement: PreparedStatement, value: Byte?) = statement.setByte(position, value!!)
    override fun getValue(position: Int, resultSet: ResultSet): Byte? = resultSet.getByte(position)
}

