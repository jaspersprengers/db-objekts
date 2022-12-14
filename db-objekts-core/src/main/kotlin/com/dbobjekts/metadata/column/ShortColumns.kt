package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

open class ShortColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<Short>(name, table, Short::class.java, aggregateType), IntegerNumericColumn {
    constructor(table: AnyTable, name: String) : this(table, name, null)
    override fun sum() = LongColumn(table, nameInTable, AggregateType.SUM)
    override fun avg() = DoubleColumn(table, nameInTable, AggregateType.AVG)
    override fun min() = LongColumn(table, nameInTable, AggregateType.MIN)
    override fun max() = LongColumn(table, nameInTable, AggregateType.MAX)

    override fun distinct() = ShortColumn(table, nameInTable, AggregateType.DISTINCT)

    override val nullable: NullableColumn<Short?> = NullableShortColumn(table, name, aggregateType)
    override fun setValue(position: Int, statement: PreparedStatement, value: Short) = statement.setShort(position, value)
    override fun getValue(position: Int, resultSet: ResultSet): Short = resultSet.getShort(position)
}

open class NullableShortColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<Short?>(name, table, Types.SMALLINT, Short::class.java, aggregateType), IntegerNumericColumn {
    constructor(table: AnyTable, name: String) : this(table, name, null)
    override fun sum() = LongColumn(table, nameInTable, AggregateType.SUM)
    override fun avg() = DoubleColumn(table, nameInTable, AggregateType.AVG)
    override fun min() = LongColumn(table, nameInTable, AggregateType.MIN)
    override fun max() = LongColumn(table, nameInTable, AggregateType.MAX)

    override fun distinct() = NullableShortColumn(table, nameInTable, AggregateType.DISTINCT)

    override fun setValue(position: Int, statement: PreparedStatement, value: Short?) = statement.setShort(position, value!!)
    override fun getValue(position: Int, resultSet: ResultSet): Short? = resultSet.getShort(position)
}
