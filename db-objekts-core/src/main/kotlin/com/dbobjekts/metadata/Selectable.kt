package com.dbobjekts.metadata

import com.dbobjekts.api.AnyColumn

/**
 * Interface to denote the two types that can be passed to a select statement, i.e. Column<I> and Table<I>
 */
interface Selectable<I> {
    val columns: List<AnyColumn>
    fun toValue(values: List<Any?>): I
}
