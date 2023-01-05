package com.dbobjekts.metadata

import com.dbobjekts.api.AnyColumn

/**
 * FOR INTERNAL USE ONLY
 * Interface to denote the two types that can be passed to a select statement, i.e. Column<I> and Table<I>
 */
interface Selectable<I> {
    /**
     * FOR INTERNAL USE ONLY
     * The columns that make up this selectable element. Either one in that case of a single column, or
     * all the columns in a table when the Selectable is a Table.
     */
    val columns: List<AnyColumn>

    /**
     * FOR INTERNAL USE ONLY
     * Converts a list of retrieved values to a type safe value
     */
    fun toValue(values: List<Any?>): I
}
