package com.dbobjekts.metadata.column

import com.dbobjekts.metadata.ColumnFactory
import com.dbobjekts.statement.SqlParameter


abstract class HavingClause<T>(val symbol: String) {
    abstract val column: Column<T>
    abstract val value: T
    fun sqlParameter(oneBasedPosition: Int): SqlParameter<T> =
        SqlParameter(oneBasedPosition, column, value)
}

class HavingClauseForLong(condition: String, override val value: Long) : HavingClause<Long>(condition) {
    override val column = ColumnFactory.LONG
}

class HavingClauseForDouble(condition: String, override val value: Double) : HavingClause<Double>(condition) {
    override val column = ColumnFactory.DOUBLE
}

object Aggregate {
    val ALIAS = "AGGREGATE"

    internal fun havingClause(symbol: String): String = "having $ALIAS $symbol ?"

    fun lt(value: Long): HavingClauseForLong = HavingClauseForLong("<", value)
    fun gt(value: Long): HavingClauseForLong = HavingClauseForLong(">", value)
    fun eq(value: Long): HavingClauseForLong = HavingClauseForLong("=", value)

    fun lt(value: Double): HavingClauseForDouble = HavingClauseForDouble("<", value)
    fun gt(value: Double): HavingClauseForDouble = HavingClauseForDouble(">", value)
    fun eq(value: Double): HavingClauseForDouble = HavingClauseForDouble("=", value)
}
