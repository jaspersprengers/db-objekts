package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.Table
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

open class IntegerColumn(table: Table, name: String) : NonNullableColumn<Int>(name, table){
    override val nullable: NullableColumn<Int?> = NullableIntegerColumn(table, name)
    override fun setValue(position: Int, statement: PreparedStatement, value: Int) = statement.setInt(position, value)
    override fun getValue(position: Int, resultSet: ResultSet): Int = resultSet.getInt(position)
    override val columnClass: Class<*> = IntegerColumn::class.java
    override val valueClass: Class<*> = Int::class.java
}

open class NullableIntegerColumn(table: Table, name: String) :  NullableColumn<Int?>(name, table, Types.INTEGER){
    override fun setValue(position: Int, statement: PreparedStatement, value: Int?) = statement.setInt(position, value!!)
    override fun getValue(position: Int, resultSet: ResultSet): Int? = resultSet.getInt(position)
    override val columnClass: Class<*> = IntegerColumn::class.java
    override val valueClass: Class<*> = Int::class.java
}

open class LongColumn(table: Table, name: String) : NonNullableColumn<Long>(name, table){
    override val nullable: NullableColumn<Long?> = NullableLongColumn(table, name)
    override fun setValue(position: Int, statement: PreparedStatement, value: Long) = statement.setLong(position, value)
    override fun getValue(position: Int, resultSet: ResultSet): Long = resultSet.getLong(position)
    override val columnClass: Class<*> = LongColumn::class.java
    override val valueClass: Class<*> = Long::class.java
}

open class NullableLongColumn(table: Table, name: String) :  NullableColumn<Long?>(name, table, Types.NUMERIC){
    override fun setValue(position: Int, statement: PreparedStatement, value: Long?) = statement.setLong(position, value!!)
    override fun getValue(position: Int, resultSet: ResultSet): Long? = resultSet.getLong(position)
    override val columnClass: Class<*> = NullableLongColumn::class.java
    override val valueClass: Class<*> = Long::class.java
}

open class ByteColumn(table: Table, name: String) : NonNullableColumn<Byte>(name, table){
    override val nullable: NullableColumn<Byte?> = NullableByteColumn(table, name)
    override fun setValue(position: Int, statement: PreparedStatement, value: Byte) = statement.setByte(position, value)
    override fun getValue(position: Int, resultSet: ResultSet): Byte = resultSet.getByte(position)
    override val columnClass: Class<*> = ByteColumn::class.java
    override val valueClass: Class<*> = Byte::class.java
}

open class NullableByteColumn(table: Table, name: String) :  NullableColumn<Byte?>(name, table, Types.TINYINT){
    override fun setValue(position: Int, statement: PreparedStatement, value: Byte?) = statement.setByte(position, value!!)
    override fun getValue(position: Int, resultSet: ResultSet): Byte? = resultSet.getByte(position)
    override val columnClass: Class<*> = NullableByteColumn::class.java
    override val valueClass: Class<*> = Byte::class.java
}

