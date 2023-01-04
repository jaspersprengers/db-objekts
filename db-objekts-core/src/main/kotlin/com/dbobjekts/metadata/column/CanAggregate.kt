package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyTable

internal interface CanAggregate {
    val aggregateType: AggregateType?

    val nameInTable: String
    val table: AnyTable

    fun toSQL(aliasDotName: String): String = aggregateType?.forColumn(aliasDotName) ?: aliasDotName

}

internal interface AggregateLongColumn : CanAggregate {

    fun sum() = LongColumn(table, nameInTable, AggregateType.SUM)
    fun avg() = DoubleColumn(table, nameInTable, AggregateType.AVG)
    fun min() = LongColumn(table, nameInTable, AggregateType.MIN)
    fun max() = LongColumn(table, nameInTable, AggregateType.MAX)

}

internal interface AggregateDoubleColumn : CanAggregate {

    fun sum() = DoubleColumn(table, nameInTable, AggregateType.SUM)
    fun avg() = DoubleColumn(table, nameInTable, AggregateType.AVG)
    fun min() = DoubleColumn(table, nameInTable, AggregateType.MIN)
    fun max() = DoubleColumn(table, nameInTable, AggregateType.MAX)

}
