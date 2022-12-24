package com.dbobjekts.statement.select

import com.dbobjekts.metadata.column.Column

class OrderByClause<C>(val column: Column<C>, val ascending: Boolean) {
    val columnName: String = column.nameInTable

    val direction: String = if (ascending) "ASC" else "DESC"

    override fun toString(): String = "${column.table.alias()}.$columnName $direction"
}
