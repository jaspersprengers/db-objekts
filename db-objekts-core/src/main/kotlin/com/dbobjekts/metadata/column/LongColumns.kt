package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types


open class LongColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<Long>(name, table, Long::class.java, aggregateType) {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun distinct() = LongColumn(table, nameInTable, AggregateType.DISTINCT)
    fun sum() = LongColumn(table, nameInTable, AggregateType.SUM)
    fun avg() = DoubleColumn(table, nameInTable, AggregateType.AVG)
    fun min() = LongColumn(table, nameInTable, AggregateType.MIN)
    fun max() = LongColumn(table, nameInTable, AggregateType.MAX)
    override val nullable: NullableColumn<Long?> = NullableLongColumn(table, name)

    override fun setValue(position: Int, statement: PreparedStatement, value: Long) = statement.setLong(position, value)
    override fun getValue(position: Int, resultSet: ResultSet): Long = resultSet.getLong(position)

}

open class NullableLongColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<Long?>(name, table, Types.NUMERIC, Long::class.java, aggregateType) {

    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun distinct() = NullableLongColumn(table, nameInTable, AggregateType.DISTINCT)
    fun sum() = LongColumn(table, nameInTable, AggregateType.SUM)
    fun avg() = DoubleColumn(table, nameInTable, AggregateType.AVG)
    fun min() = LongColumn(table, nameInTable, AggregateType.MIN)
    fun max() = LongColumn(table, nameInTable, AggregateType.MAX)
    override fun setValue(position: Int, statement: PreparedStatement, value: Long?) = statement.setLong(position, value!!)
    override fun getValue(position: Int, resultSet: ResultSet): Long? = resultSet.getLong(position)
}
