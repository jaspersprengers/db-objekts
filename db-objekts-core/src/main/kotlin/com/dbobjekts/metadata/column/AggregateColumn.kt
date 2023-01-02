package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyColumn

internal interface AggregateColumn {
    val aggregateType: AggregateType?

    fun write(column: AnyColumn): String =
        aggregateType?.forColumn(column) ?: column.forSelect()

}
