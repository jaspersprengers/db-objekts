package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Types

open class IntegerColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NonNullableColumn<Int>(name, table, Int::class.java, aggregateType), AggregateLongColumn {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun distinct() = IntegerColumn(table, nameInTable, AggregateType.DISTINCT)

    override val nullable: NullableColumn<Int?> = NullableIntegerColumn(table, name, null)
    override fun setValue(position: Int, statement: PreparedStatement, value: Int) = statement.setInt(position, value)
    override fun getValue(position: Int, resultSet: ResultSet): Int = resultSet.getInt(position)
}

open class NullableIntegerColumn(table: AnyTable, name: String, aggregateType: AggregateType?) :
    NullableColumn<Int?>(name, table, Types.INTEGER, Int::class.java, aggregateType), AggregateLongColumn {
    constructor(table: AnyTable, name: String) : this(table, name, null)

    override fun distinct() = NullableIntegerColumn(table, nameInTable, AggregateType.DISTINCT)

    override fun setValue(position: Int, statement: PreparedStatement, value: Int?) = statement.setInt(position, value!!)
    override fun getValue(position: Int, resultSet: ResultSet): Int? = resultSet.getInt(position)
}
