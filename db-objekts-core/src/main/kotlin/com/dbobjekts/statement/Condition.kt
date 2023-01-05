package com.dbobjekts.statement

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.metadata.column.Column
import com.dbobjekts.statement.whereclause.WhereClauseComponent
import com.dbobjekts.util.StringUtil

data class Condition<I, W : WhereClauseComponent>(
    val parent: W,
    val column: Column<I>,
    override val joinType: ConditionJoinType = And,
    internal var symbol: String? = null,
     var values: List<I>? = null,
     var columnCondition: Column<I>? = null
) : WhereClauseComponent {

    private fun getParameterCharactersForValues(): String =
        if (values == null) "" else (if (values!!.size == 1) "?" else "(${values!!.map { "?" }.joinToString(",")})")


    override fun toString(): String = column.nameInTable + symbol + (values ?: "null")

    /**
     * operator for equality condition. Results in SQL: my_column = ?
     */
    fun eq(value: I): W = if (value == null) throw StatementBuilderException("Cannot supply null argument. Use isNull()") else createSimpleCondition(value, "=")

    /**
     * operator for nullability check. Results in SQL my_column IS NULL
     */
    fun isNull(): W = createIsNullCondition("is null")

    /**
     * operator for column-to-column condition: Results in SQL: column1 = column2
     */
    fun eq(column: Column<I>): W = createColumnCondition(column, "=")

    /**
     * Not-equals comparison operator. Results in SQL: my_column <> ?
     *
     * @param value a non-null value
     */
    fun ne(value: I): W = if (value == null) throw StatementBuilderException("Cannot supply null argument. Use isNotNull()") else createSimpleCondition(value, "<>")

    fun isNotNull(): W = createIsNullCondition("is not null")

    /**
     * operator for column-to-column condition: Results in SQL: column1 <> column2
     */
    fun ne(column: Column<I>): W = createColumnCondition(column, "<>")

    /**
     * Less-than comparison operator. Results in SQL: my_column < ?
     */
    fun lt(value: I): W = createSimpleCondition(value, "<")

    /**
     * Less-than comparison operator. Results in SQL: my_column1 < my_column2
     */
    fun lt(column: Column<I>): W = createColumnCondition(column, "<")

    /**
     * Less than or equal comparison operator. Results in SQL: my_column <= ?
     */
    fun le(value: I): W = createSimpleCondition(value, "<=")

    /**
     * Less than or equal comparison operator. Results in SQL: my_column1 <= my_column2
     */
    fun le(column: Column<I>): W = createColumnCondition(column, "<=")

    /**
     * Greater-than comparison operator. Results in SQL: my_column1 > ?
     */
    fun gt(value: I): W = createSimpleCondition(value, ">")

    /**
     * Greater than comparison operator. Results in SQL: my_column1 > my_column2
     */
    fun gt(column: Column<I>): W = createColumnCondition(column, ">")

    /**
     * Greater than or equal comparison operator. Results in SQL: my_column1 >= ?
     */
    fun ge(value: I): W = createSimpleCondition(value, ">=")

    /**
     * Greater than or equal comparison operator. Results in SQL: my_column1 >= my_column2
     */
    fun ge(column: Column<I>): W = createColumnCondition(column, ">=")

    /**
     * IN operator. Results in SQL: my_column1 IN (1,3,5)
     */
    fun `in`(vararg values: I): W = createInCondition("IN", values.toList())

    /**
     * IN operator. Identical to in and isIn. Results in SQL: my_column1 IN (1,3,5)
     */
    fun within(vararg values: I): W = createInCondition("IN", values.toList())

    /**
     * IN operator. Identical to in and within. Results in SQL: my_column1 IN (1,3,5)
     */
    fun isIn(vararg values: I): W = createInCondition("IN", values.toList())

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

    private fun createSimpleCondition(value: I, sql: String): W =
        createSubClause(sql, listOf(value), null)


    private fun createInCondition(sql: String, values: List<I>): W =
        createSubClause(sql, values, null)


    private fun createIsNullCondition(sql: String): W =
        createSubClause(sql, null, null)


    private fun createColumnCondition(col: Column<I>, sql: String): W =
        createSubClause(sql, null, col)


    @Suppress("UNCHECKED_CAST")
    private fun createLikeCondition(v: String, sql: String): W =
        createSubClause(sql, listOf(v as I), null)

    private fun createSubClause(
        symbol: String,
        values: List<I>?,
        secondColumn: Column<I>?
    ): W {
        this.symbol = symbol
        this.values = values
        return parent
    }

    override val keyword: String = " $joinType "

    override fun getChildren(): List<WhereClauseComponent> = listOf()

    override fun toSQL(options: SQLOptions): String {

        fun columnComponent(): String = if (options.includeAlias) column.aliasDotName() else column.nameInTable

        fun getOptionalAlias(col: AnyColumn) = "${col.table.alias()}.${col.nameInTable}"

        val rightOperand: String =
            if (columnCondition != null) getOptionalAlias(columnCondition!!) else getParameterCharactersForValues()

        return StringUtil.concat(
            listOf(
                columnComponent(),
                symbol ?: throw StatementBuilderException("This Condition is not finished"),
                rightOperand
            )
        )
    }

}
