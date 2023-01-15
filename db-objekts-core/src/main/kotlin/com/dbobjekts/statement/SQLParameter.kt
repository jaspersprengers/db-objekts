package com.dbobjekts.statement

import com.dbobjekts.api.AnySqlParameter
import com.dbobjekts.metadata.column.Column
import com.dbobjekts.metadata.column.ColumnAndValue
import java.sql.PreparedStatement

/**
 * Represents a '?' parameter with its value and relative position in a SQL query. SELECT name from PERSON where age > ? and married = ?
 */
data class SqlParameter<T>(
    val oneBasedPosition: Int,
    val column: Column<T>,
    val value: T?
) {

    fun setValueOnStatement(preparedStatement: PreparedStatement) {
        column.putValue(oneBasedPosition, preparedStatement, value)
    }

    override fun toString(): String = column::class.java.getSimpleName() + value + "position " + oneBasedPosition


    companion object {

        fun <T> fromWhereClauseCondition(condition: Condition<T, *>): List<AnySqlParameter> =
            condition.valueOrColumn.values?.map { SqlParameter<T>(0, condition.column, it) } ?: listOf()

        fun <T> fromColumnValue(zeroBasedIndex: Int, colValue: ColumnAndValue<T>): SqlParameter<T> =
            SqlParameter<T>(zeroBasedIndex + 1, colValue.column, colValue.value)

    }
}

