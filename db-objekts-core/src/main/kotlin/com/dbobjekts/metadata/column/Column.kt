package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyTable
import com.dbobjekts.api.exception.DBObjektsException
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.metadata.Selectable
import com.dbobjekts.statement.And
import com.dbobjekts.statement.whereclause.SubClause
import com.dbobjekts.util.ValidateDBObjectName
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
    internal val nameInTable: String,
    internal val table: AnyTable,
    internal val valueClass: Class<*>,
    internal val aggregateType: AggregateType?
) : Selectable<I> {

    init {
        if (!ValidateDBObjectName(nameInTable))
            throw DBObjektsException("Not a valid column name: '$nameInTable'")
    }

    fun count(): LongColumn = LongColumn(table, nameInTable, AggregateType.COUNT)

    fun countDistinct(): LongColumn = LongColumn(table, nameInTable, AggregateType.COUNT_DISTINCT)


    /**
     * operator for equality condition. Results in SQL: my_column = ?
     */
    fun eq(value: I): SubClause= if (value == null) throw StatementBuilderException("Cannot supply null argument. Use isNull()") else createSimpleCondition(value, "=")

    /**
     * operator for nullability check. Results in SQL my_column IS NULL
     */
    fun isNull(): SubClause= createIsNullCondition("is null")

    /**
     * operator for column-to-column condition: Results in SQL: column1 = column2
     */
    fun eq(column: Column<I>): SubClause= createColumnCondition(column, "=")

    /**
     * Not-equals comparison operator. Results in SQL: my_column <> ?
     *
     * @param value a non-null value
     */
    fun ne(value: I): SubClause= if (value == null) throw StatementBuilderException("Cannot supply null argument. Use isNotNull()") else createSimpleCondition(value, "<>")

    fun isNotNull(): SubClause= createIsNullCondition("is not null")

    /**
     * operator for column-to-column condition: Results in SQL: column1 <> column2
     */
    fun ne(column: Column<I>): SubClause= createColumnCondition(column, "<>")

    /**
     * Less-than comparison operator. Results in SQL: my_column < ?
     */
    fun lt(value: I): SubClause= createSimpleCondition(value, "<")

    /**
     * Less-than comparison operator. Results in SQL: my_column1 < my_column2
     */
    fun lt(column: Column<I>): SubClause= createColumnCondition(column, "<")

    /**
     * Less than or equal comparison operator. Results in SQL: my_column <= ?
     */
    fun le(value: I): SubClause= createSimpleCondition(value, "<=")

    /**
     * Less than or equal comparison operator. Results in SQL: my_column1 <= my_column2
     */
    fun le(column: Column<I>): SubClause= createColumnCondition(column, "<=")

    /**
     * Greater-than comparison operator. Results in SQL: my_column1 > ?
     */
    fun gt(value: I): SubClause= createSimpleCondition(value, ">")

    /**
     * Greater than comparison operator. Results in SQL: my_column1 > my_column2
     */
    fun gt(column: Column<I>): SubClause= createColumnCondition(column, ">")

    /**
     * Greater than or equal comparison operator. Results in SQL: my_column1 >= ?
     */
    fun ge(value: I): SubClause= createSimpleCondition(value, ">=")

    /**
     * Greater than or equal comparison operator. Results in SQL: my_column1 >= my_column2
     */
    fun ge(column: Column<I>): SubClause= createColumnCondition(column, ">=")

    /**
     * IN operator. Results in SQL: my_column1 IN (1,3,5)
     */
    fun `in`(vararg values: I): SubClause= createInCondition("IN", values.toList())

    /**
     * IN operator. Identical to in and isIn. Results in SQL: my_column1 IN (1,3,5)
     */
    fun within(vararg values: I): SubClause= createInCondition("IN", values.toList())

    /**
     * IN operator. Identical to in and within. Results in SQL: my_column1 IN (1,3,5)
     */
    fun isIn(vararg values: I): SubClause= createInCondition("IN", values.toList())

    /**
     * NOT IN operator. Results in SQL: my_column1 NOT IN (1,3,5)
     */
    fun notIn(vararg values: I): SubClause= createInCondition("NOT IN", values.toList())

    /**
     * LIKE operator to find matches beginning with a string value. Results in SQL: my_column1 like ? and parameter ''john%''
     */
    fun startsWith(value: String): SubClause= createLikeCondition(value + "%", "like")

    /**
     * LIKE operator to find matches ending in a string value. Results in SQL: my_column1 like ? and parameter ''%john''
     */
    fun endsWith(value: String): SubClause= createLikeCondition("%" + value, "like")

    /**
     * LIKE operator to find records containing a certain value. Results in SQL: my_column1 like ? and parameter ''%john%''
     */
    fun contains(value: String): SubClause= createLikeCondition("%" + value + "%", "like")

    private fun createSimpleCondition(value: I, sql: String): SubClause=
        createSubClause(sql, listOf(value), null)


    private fun createInCondition(sql: String, values: List<I>): SubClause=
        createSubClause(sql, values, null)


    private fun createIsNullCondition(sql: String): SubClause=
        createSubClause(sql, null, null)


    private fun createColumnCondition(col: Column<I>, sql: String): SubClause=
        createSubClause(sql, null, col)


    @Suppress("UNCHECKED_CAST")
    private fun createLikeCondition(v: String, sql: String): SubClause=
        createSubClause(sql, listOf(v as I), null)

    //fun createSubClause(symbol: String, values: List<I>?, secondColumn: Column<I>?): W




    internal abstract fun create(value: I?): ColumnAndValue<I>

    private fun createSubClause(symbol: String, values: List<I>?, secondColumn: Column<I>?): SubClause {
        val clause = SubClause()
        clause.addCondition(this, And, symbol, values, secondColumn)
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

    override fun toString() = "${table.tableName}.$nameInTable"

    internal fun serialize(): String = "$nameInTable ${javaClass.simpleName}"

}

