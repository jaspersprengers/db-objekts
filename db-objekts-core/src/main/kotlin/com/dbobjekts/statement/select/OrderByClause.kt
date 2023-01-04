package com.dbobjekts.statement.select

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.column.Aggregate

class OrderByClause(val column: AnyColumn, ascending: Boolean) {

    val direction: String = if (ascending) "ASC" else "DESC"

    override fun toString(): String {
        val name = if (column.aggregateType != null) Aggregate.ALIAS else column.aliasDotName()
        return "$name $direction"
    }
}
