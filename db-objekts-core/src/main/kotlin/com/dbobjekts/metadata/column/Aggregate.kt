package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.ColumnFactory
import com.dbobjekts.statement.And
import com.dbobjekts.statement.ConditionJoinType
import com.dbobjekts.statement.Or
import com.dbobjekts.statement.SqlParameter


abstract class HavingClause<T : Number>() {
    private val conditions = mutableListOf<Condition<*, *>>()
    internal fun add(condition: Condition<T, *>) {
        conditions += condition
    }

    abstract val column: Column<T>

    internal fun toSQL(): String {
        if (conditions.isEmpty())
            return ""
        val indexed = conditions.mapIndexed { i, c -> Pair(i, c) }
        val sb = StringBuilder("having ")
        indexed.forEach { (ind, cond) ->
            sb.append("${Aggregate.ALIAS} ${cond.symbol} ?")
            if (ind < indexed.size - 1) {
                val next = indexed[ind + 1]
                sb.append(" ${next.second.joinType} ")
            }
        }
        return sb.toString()
    }

    internal fun parameters(fromPosition: Int): List<SqlParameter<T>> {
        return conditions.mapIndexed { i, c ->
            @Suppress("UNCHECKED_CAST")
            SqlParameter(fromPosition + i, column, c.value as T)
        }
    }

}

class HavingClauseForLong(symbol: String, value: Long) : HavingClause<Long>() {
    override val column = ColumnFactory.LONG

    init {
        add(Condition(this, And).withCondition(symbol, value))
    }

    fun and(): Condition<Long, HavingClauseForLong> {
        return Condition(this, And)
    }

    fun or(): Condition<Long, HavingClauseForLong> {
        return Condition(this, Or)
    }
}

class HavingClauseForDouble(symbol: String, value: Double) : HavingClause<Double>() {
    override val column = ColumnFactory.DOUBLE

    init {
        add(Condition(this, And).withCondition(symbol, value))
    }

    fun and(): Condition<Double, HavingClauseForDouble> {
        return Condition(this, And)
    }

    fun or(): Condition<Double, HavingClauseForDouble> {
        return Condition(this, Or)
    }
}

data class Condition<T : Number, C : HavingClause<T>>(private val clause: C,
                                                      val joinType: ConditionJoinType) {
    internal lateinit var symbol: String
    internal lateinit var value: T

    internal fun withCondition(symbol: String, value: T): Condition<T, C> {
        this.symbol = symbol
        this.value = value
        return this
    }

    fun lt(value: T): C = add("<", value)
    fun gt(value: T): C = add(">", value)
    fun le(value: T): C = add("<=", value)
    fun ge(value: T): C = add(">=", value)
    fun eq(value: T): C = add("=", value)
    fun ne(value: T): C = add("<>", value)


    private fun add(symbol: String, value: T): C {
        this.symbol = symbol
        this.value = value
        clause.add(this)
        return clause
    }
}

object Aggregate {
    val ALIAS = "AGGREGATE"

    /**
     * Less-than operator for aggregated column
     */
    fun lt(value: Long): HavingClauseForLong = HavingClauseForLong("<", value)
    /**
     * Greater -than operator for aggregated column
     */
    fun gt(value: Long): HavingClauseForLong = HavingClauseForLong(">", value)
    /**
     * Less-than-or-equal operator for aggregated column
     */
    fun le(value: Long): HavingClauseForLong = HavingClauseForLong("<=", value)
    /**
     * Greater-than-or-equal operator for aggregated column
     */
    fun ge(value: Long): HavingClauseForLong = HavingClauseForLong(">=", value)
    /**
     * Equals operator for aggregated column
     */
    fun eq(value: Long): HavingClauseForLong = HavingClauseForLong("=", value)
    /**
     * Not-equals operator for aggregated column
     */
    fun ne(value: Long): HavingClauseForLong = HavingClauseForLong("<>", value)

    /**
     * Less-than operator for aggregated column
     */
    fun lt(value: Double): HavingClauseForDouble = HavingClauseForDouble("<", value)
    /**
     * Greater-than operator for aggregated column
     */
    fun gt(value: Double): HavingClauseForDouble = HavingClauseForDouble(">", value)
    /**
     * Less-than-or-equal operator for aggregated column
     */
    fun le(value: Double): HavingClauseForDouble = HavingClauseForDouble("<=", value)
    /**
     * Greater-than-or-equal operator for aggregated column
     */
    fun ge(value: Double): HavingClauseForDouble = HavingClauseForDouble(">=", value)
    /**
     * Equals operator for aggregated column
     */
    fun eq(value: Double): HavingClauseForDouble = HavingClauseForDouble("=", value)
    /**
     * Not-equals operator for aggregated column
     */
    fun ne(value: Double): HavingClauseForDouble = HavingClauseForDouble("<>", value)

    internal fun containsOneGroupByAggregate(columns: List<AnyColumn>): Boolean =
        columns.filter { it.aggregateType?.usesGroupBy ?: false }.count() == 1

}
