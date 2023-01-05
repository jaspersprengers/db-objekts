package com.dbobjekts.statement.whereclause

import com.dbobjekts.metadata.column.Column
import com.dbobjekts.statement.*
import com.dbobjekts.vendors.Vendor


open class SubClause(
    override var joinType: ConditionJoinType = And,
    internal var isRoot: Boolean = false
) : WhereClauseComponent{

    private val buffer = mutableListOf<WhereClauseComponent>()

    internal fun elements(): List<WhereClauseComponent> = buffer.toList()

    /**
     * Adds a nested AND condition to the where clause. Example:
     * ```kotlin
     * transaction.select(Book.isbn).where(Book.authorId.eq(5)
     *                 .and(Book.title.startsWith("Harry Potter").or(Book.published.lt(LocalDate.of(1980,1,1)))))
     *                 .first()
     * ```
     * Which produces the following SQL:
     * ```sql
     * select b.ISBN from LIBRARY.BOOK b where b.AUTHOR_ID = ? and (b.TITLE like ? or (b.PUBLISHED < ?))
     * ```
     */
    fun and(subClause: SubClause): SubClause {
        buffer.add(subClause)
        return this
    }

    /**
     * Adds a nested OR condition to the whereclause. Example:
     * ```kotlin
     *    transaction.select(Book.isbn).where(Book.authorId.eq(5)
     *         .or(Book.title.startsWith("Harry Potter").and(Book.published.gt(LocalDate.of(1998,1,1)))))
     *         .firstOrNull()
     * ```
     * Which produces the following SQL:
     * ```sql
     * select b.ISBN from LIBRARY.BOOK b where b.AUTHOR_ID = ? or (b.TITLE like ? and (b.PUBLISHED > ?))
     * ```
     */
    fun or(subClause: SubClause): SubClause {
        subClause.joinType = Or
        buffer.add(subClause)
        return this
    }

    /**
     * Adds an AND condition to the whereclause. Example:
     * ```kotlin
     * transaction.select(Book.isbn).where(Book.authorId.eq(5).and(Book.title).startsWith("Harry Potter")).firstOrNull()
     * ```
     */
    fun <C> and(column: Column<C>): Condition<C, SubClause> {
        val condition = Condition<C, SubClause>(this, column, And)
        buffer.add(condition)
        return condition
    }

    /**
     * Adds an OR condition to the whereclause. Example
     * ```kotlin
     * transaction.select(Book.isbn).where(Book.authorId.eq(5).or(Book.title).startsWith("Harry Potter")).firstOrNull()
     * ```
     */
    fun <C> or(column: Column<C>): Condition<C, SubClause> {
        val condition = Condition<C, SubClause>(this, column, Or)
        buffer.add(condition)
        return condition
    }

    internal fun <C> addCondition(
        column: Column<C>,
        joinType: ConditionJoinType = And,
        symbol: String,
        values: List<C>?,
    col: Column<C>?): Condition<C, SubClause>  {
        val condition = Condition<C, SubClause>(this, column, joinType, symbol, values, col)
        buffer.add(condition)
        return condition
    }

    override val keyword: String = ""

    override fun getChildren(): List<WhereClauseComponent> = elements().toList()

    override fun toSQL(options: SQLOptions): String {
        val str: String = with(elements()) {
            map {
                val isNotFirst = indexOf(it) > 0
                (if (isNotFirst) it.keyword else "") + it.toSQL(options)
            }.joinToString("")
        }
        val prefix = if (isRoot) "" else " $joinType "
        return "$prefix($str)"
    }

    private val clause: SubClause = this
}
