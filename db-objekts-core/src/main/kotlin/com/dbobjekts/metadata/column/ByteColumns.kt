package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types


open class ByteColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<Byte>(name, table, Byte::class.java, aggregateType), IntegerNumericColumn {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun sum() = LongColumn(table, nameInTable, AggregateType.SUM)
    override fun avg() = DoubleColumn(table, nameInTable, AggregateType.AVG)
    override fun min() = LongColumn(table, nameInTable, AggregateType.MIN)
    override fun max() = LongColumn(table, nameInTable, AggregateType.MAX)

    override fun distinct() = ByteColumn(table, nameInTable, AggregateType.DISTINCT)

    override val nullable: NullableColumn<Byte?> = NullableByteColumn(table, name, aggregateType)
    override fun setValue(position: Int, statement: PreparedStatement, value: Byte) = statement.setByte(position, value)
    override fun getValue(position: Int, resultSet: ResultSet): Byte = resultSet.getByte(position)
}

open class NullableByteColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<Byte?>(name, table, Types.TINYINT, Byte::class.java, aggregateType), IntegerNumericColumn {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun sum() = LongColumn(table, nameInTable, AggregateType.SUM)
    override fun avg() = DoubleColumn(table, nameInTable, AggregateType.AVG)
    override fun min() = LongColumn(table, nameInTable, AggregateType.MIN)
    override fun max() = LongColumn(table, nameInTable, AggregateType.MAX)

    override fun distinct() = NullableByteColumn(table, nameInTable, AggregateType.DISTINCT)

    override fun setValue(position: Int, statement: PreparedStatement, value: Byte?) = statement.setByte(position, value!!)
    override fun getValue(position: Int, resultSet: ResultSet): Byte? = resultSet.getByte(position)
}
