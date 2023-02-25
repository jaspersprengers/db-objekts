package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyTable
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.metadata.Selectable
import com.dbobjekts.statement.And
import com.dbobjekts.statement.ValueOrColumn
import com.dbobjekts.statement.whereclause.SubClause
import com.dbobjekts.util.ObjectNameValidator
import java.sql.PreparedStatement
import java.sql.ResultSet

/**
 * Base of objects representing the column of a database table. ColumnClasses are associated with an sqlType indicating to the JDBC driver how to map the database type to a Kotlin type.
 * The correct sqlType is particularly relevant when setting null values.
 *
 * @param nameInTable    the column name in the corresponding database table
 * @tparam I the Kotlin type that the db data type maps to
 */
abstract class Column<I>(
    /**
     * Reference to the containing [Table]
     */
    val table: AnyTable,
    /**
     * The name of this column in the database table
     */
    val nameInTable: String,
    internal val valueClass: Class<*>,
    internal val aggregateType: AggregateType?
) : Selectable<I> {

    fun count(): LongColumn = LongColumn(table, nameInTable, AggregateType.COUNT)

    fun countDistinct(): LongColumn = LongColumn(table, nameInTable, AggregateType.COUNT_DISTINCT)

    /**
     * operator for equality condition. Results in SQL: my_column = ?
     */
    fun eq(value: I): SubClause =
        if (value == null) isNull() else createSimpleCondition(
            value,
            "="
        )

    /**
     * operator for equality condition. Results in SQL: my_column = my_column2
     */
    fun eq(column: AnyColumn): SubClause = createColumnCondition(column, "=")

    /**
     * operator for not-equality condition. Results in SQL: my_column <> my_column2
     */
    fun ne(column: AnyColumn): SubClause = createColumnCondition(column, "!=")

    /**
     * operator for less-than condition. Results in SQL: my_column < my_column2
     */
    fun lt(column: AnyColumn): SubClause = createColumnCondition(column, "<")

    /**
     * operator for greater-than condition. Results in SQL: my_column > my_column2
     */
    fun gt(column: AnyColumn): SubClause = createColumnCondition(column, ">")

    /**
     * operator for less-than-or-equal condition. Results in SQL: my_column <= my_column2
     */
    fun le(column: AnyColumn): SubClause = createColumnCondition(column, "<=")
    /*
     * operator for greater-than-or-equal condition. Results in SQL: my_column >= my_column2
     */
    fun ge(column: AnyColumn): SubClause = createColumnCondition(column, ">=")

    /**
     * operator for nullability check. Results in SQL my_column IS NULL
     */
    fun isNull(): SubClause = createIsNullCondition("is null")

    /**
     * Not-equals comparison operator. Results in SQL: my_column <> ?
     *
     * @param value a non-null value
     */
    fun ne(value: I): SubClause =
        if (value == null) isNotNull() else createSimpleCondition(
            value,
            "<>"
        )

    fun isNotNull(): SubClause = createIsNullCondition("is not null")

    /**
     * Less-than comparison operator. Results in SQL: my_column < ?
     */
    fun lt(value: I): SubClause = createSimpleCondition(value, "<")

    /**
     * Less than or equal comparison operator. Results in SQL: my_column <= ?
     */
    fun le(value: I): SubClause = createSimpleCondition(value, "<=")

    /**
     * Greater-than comparison operator. Results in SQL: my_column1 > ?
     */
    fun gt(value: I): SubClause = createSimpleCondition(value, ">")

    /**
     * Greater than or equal comparison operator. Results in SQL: my_column1 >= ?
     */
    fun ge(value: I): SubClause = createSimpleCondition(value, ">=")

    /**
     * IN operator. Results in SQL: my_column1 IN (1,3,5)
     */
    fun `in`(vararg values: I): SubClause = createInCondition("IN", values.toList())

    /**
     * IN operator. Results in SQL: my_column1 IN (1,3,5)
     */
    fun within(vararg values: I): SubClause = createInCondition("IN", values.toList())

    /**
     * NOT IN operator. Results in SQL: my_column1 NOT IN (1,3,5)
     */
    fun notIn(vararg values: I): SubClause = createInCondition("NOT IN", values.toList())

    /**
     * LIKE operator to find matches beginning with a string value. Results in SQL: my_column1 like ? and parameter ''john%''
     */
    fun startsWith(value: String): SubClause = createLikeCondition(value + "%")

    /**
     * LIKE operator to find matches ending in a string value. Results in SQL: my_column1 like ? and parameter ''%john''
     */
    fun endsWith(value: String): SubClause = createLikeCondition("%" + value)

    /**
     * LIKE operator to find records containing a certain value. Results in SQL: my_column1 like ? and parameter ''%john%''
     */
    fun contains(value: String): SubClause = createLikeCondition("%" + value + "%")

    private fun createSimpleCondition(value: I, sql: String): SubClause =
        createSubClause(sql, ValueOrColumn.forValues(listOf(value)))

    private fun createColumnCondition(column: AnyColumn, sql: String): SubClause =
        createSubClause(sql, ValueOrColumn.forColumn(column))


    private fun createInCondition(sql: String, values: List<I>): SubClause =
        createSubClause(sql, ValueOrColumn.forValues(values))


    private fun createIsNullCondition(sql: String): SubClause =
        createSubClause(sql, ValueOrColumn.forNullValues())

    @Suppress("UNCHECKED_CAST")
    private fun createLikeCondition(v: String): SubClause =
        createSubClause("like", ValueOrColumn.forValues(listOf(v as I)))

    internal abstract fun create(value: I?): ColumnAndValue<I>

    private fun createSubClause(symbol: String, valueOrColumn: ValueOrColumn<I>): SubClause {
        val clause = SubClause()
        clause.addCondition(this, And, symbol, valueOrColumn)
        return clause
    }

    override val columns: List<AnyColumn> = listOf(this)

    @Suppress("UNCHECKED_CAST")
    override fun toValue(values: List<Any?>) = values.get(0) as I

    internal abstract fun retrieveValue(position: Int, rs: ResultSet): I?

    internal abstract fun putValue(position: Int, statement: PreparedStatement, value: I?)

    internal val tableDotName: String = table.tableName.value + "." + nameInTable

    internal fun aliasDotName(): String = table.alias() + "." + nameInTable

    internal fun forSelect(): String = aggregateType?.forColumn(aliasDotName()) ?: aliasDotName()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Column<*>) return false
        if (nameInTable != other.nameInTable) return false
        if (table != other.table) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nameInTable.hashCode()
        result = 31 * result + table.hashCode()
        return result
    }

    open internal fun simpleClassName(): String = this::class.java.simpleName

    open internal fun qualifiedClassName(): String = this::class.java.packageName + "." + this::class.java.simpleName

    override fun toString() = "${table.tableName}.$nameInTable"

    internal fun serialize(): String = "$nameInTable ${this::class.java.simpleName}"

}

