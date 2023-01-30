package com.dbobjekts.statement.select

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.SortOrder
import com.dbobjekts.metadata.column.Aggregate

class OrderByClause(val column: AnyColumn, val sortOrder: SortOrder) {

    override fun toString(): String {
        val name = if (column.aggregateType != null) Aggregate.ALIAS else column.aliasDotName()
        return "$name ${sortOrder.name}"
    }
}
