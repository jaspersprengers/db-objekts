package com.dbobjekts.statement

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.metadata.column.Column
import com.dbobjekts.statement.whereclause.SubClause
import com.dbobjekts.statement.whereclause.WhereClauseComponent
import com.dbobjekts.util.StringUtil

class ValueOrColumn<T>(val values: List<T>? = null, val column: AnyColumn? = null) {
    fun getParameterCharactersForValues(): String =
        if (column != null) {
            column.aliasDotName()
        } else if (values == null) {
            ""
        } else if (values.size == 1) {
            "?"
        } else "(${values.map { "?" }.joinToString(",")})"
    companion object {
        fun <T> forValues(values: List<T>) = ValueOrColumn<T>(values = values)
        @Suppress("UNCHECKED_CAST")
        fun <T> forNullValues() = ValueOrColumn<Unit>()  as ValueOrColumn<T>
        fun <T> forColumn(column: AnyColumn) = ValueOrColumn<T>(column = column)
    }
}

data class Condition<I, W : WhereClauseComponent>(
    val parent: W,
    val column: Column<I>,
    override val joinType: ConditionJoinType = And
) : WhereClauseComponent() {

    internal lateinit var symbol: String
    internal lateinit var valueOrColumn: ValueOrColumn<I>

    private fun getParameterCharactersForValues(): String = valueOrColumn.getParameterCharactersForValues()

    override fun toString(): String = column.nameInTable + symbol + valueOrColumn

    /**
     * operator for equality condition. Results in SQL: my_column = ?
     */
    fun eq(value: I): W =
        if (value == null) isNull() else createSimpleCondition(
            value,
            "="
        )

    /**
     * operator for equality condition. Results in SQL: my_column = my_column2
     *
     * If the column permits null values, the call is delegated to isNotNull()
     */
    fun eq(column: AnyColumn): W = createColumnCondition(column,"=")

    /**
     * operator for not-equality condition. Results in SQL: my_column <> my_column2
     *
     * If the column permits null values, the call is delegated to isNotNull()
     */
    fun ne(column: AnyColumn): W = createColumnCondition(column,"!=")

    /**
     * operator for less-than condition. Results in SQL: my_column < my_column2
     */
    fun lt(column: AnyColumn): W = createColumnCondition(column,"<")

    /**
     * operator for greater-than condition. Results in SQL: my_column > my_column2
     */
    fun gt(column: AnyColumn): W = createColumnCondition(column,">")

    /**
     * operator for less-than-or-equal condition. Results in SQL: my_column <= my_column2
     */
    fun le(column: AnyColumn): W = createColumnCondition(column,"<=")

    /**
     * operator for greater-than-or-equal condition. Results in SQL: my_column >= my_column2
     */
    fun ge(column: AnyColumn): W = createColumnCondition(column,">=")

    /**
     * operator for nullability check. Results in SQL my_column IS NULL
     */
    fun isNull(): W = createIsNullCondition("is null")

    /**
     * Not-equals comparison operator. Results in SQL: my_column <> ?
     *
     * @param value a non-null value
     */
    fun ne(value: I): W =
        if (value == null) isNotNull() else createSimpleCondition(
            value,
            "<>"
        )

    fun isNotNull(): W = createIsNullCondition("is not null")

    /**
     * Less-than comparison operator. Results in SQL: my_column < ?
     */
    fun lt(value: I): W = createSimpleCondition(value, "<")

    /**
     * Less than or equal comparison operator. Results in SQL: my_column <= ?
     */
    fun le(value: I): W = createSimpleCondition(value, "<=")

    /**
     * Greater-than comparison operator. Results in SQL: my_column1 > ?
     */
    fun gt(value: I): W = createSimpleCondition(value, ">")

    /**
     * Greater than or equal comparison operator. Results in SQL: my_column1 >= ?
     */
    fun ge(value: I): W = createSimpleCondition(value, ">=")

    /**
     * IN operator. Results in SQL: my_column1 IN (1,3,5)
     */
    fun `in`(vararg values: I): W = createInCondition("IN", values.toList())

    /**
     * IN operator. Results in SQL: my_column1 IN (1,3,5)
     */
    fun within(vararg values: I): W = createInCondition("IN", values.toList())

    /**
     * NOT IN operator. Results in SQL: my_column1 NOT IN (1,3,5)
     */
    fun notIn(vararg values: I): W = createInCondition("NOT IN", values.toList())

    /**
     * LIKE operator to find matches beginning with a string value. Results in SQL: my_column1 like ? and parameter ''john%''
     */
    fun startsWith(value: String): W = createLikeCondition(value + "%", "like")

    /**
     * LIKE operator to find matches ending in a string value. Results in SQL: my_column1 like ? and parameter ''%john''
     */
    fun endsWith(value: String): W = createLikeCondition("%" + value, "like")

    /**
     * LIKE operator to find records containing a certain value. Results in SQL: my_column1 like ? and parameter ''%john%''
     */
    fun contains(value: String): W = createLikeCondition("%" + value + "%", "like")

    private fun createColumnCondition(column: AnyColumn, sql: String): W =
        createSubClause(sql, ValueOrColumn.forColumn(column))

    private fun createSimpleCondition(value: I, sql: String): W =
        createSubClause(sql, ValueOrColumn.forValues(listOf(value)))


    private fun createInCondition(sql: String, values: List<I>): W =
        createSubClause(sql, ValueOrColumn.forValues(values))


    private fun createIsNullCondition(sql: String): W =
        createSubClause(sql, ValueOrColumn.forNullValues())

    @Suppress("UNCHECKED_CAST")
    private fun createLikeCondition(v: String, sql: String): W =
        createSubClause(sql, ValueOrColumn.forValues(listOf(v as I)))

    private fun createSubClause(
        symbol: String,
        valueOrColumn: ValueOrColumn<I>
    ): W {
        this.symbol = symbol
        this.valueOrColumn = valueOrColumn
        return parent
    }

    override val keyword: String = " $joinType "

    override fun getChildren(): List<WhereClauseComponent> = listOf()

    override fun toSQL(options: SQLOptions): String {

        fun columnComponent(): String = if (options.includeAlias) column.aliasDotName() else column.nameInTable

        return StringUtil.concat(
            listOf(
                columnComponent(),
                symbol,
                getParameterCharactersForValues()
            )
        )
    }

}
