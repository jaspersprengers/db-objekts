package com.dbobjekts.statement.select

import com.dbobjekts.api.*
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Selectable
import com.dbobjekts.metadata.column.*
import com.dbobjekts.metadata.joins.JoinChain
import com.dbobjekts.statement.ColumnInResultRow
import com.dbobjekts.statement.Semaphore
import com.dbobjekts.statement.SqlParameter
import com.dbobjekts.statement.StatementBase
import com.dbobjekts.statement.whereclause.SubClause

class SelectStatementExecutor<T, RSB : ResultRow<T>>(
    semaphore: Semaphore,
    connection: ConnectionAdapter,
    selectables: List<Selectable<*>>,
    internal val selectResultSet: RSB
) : StatementBase<SelectStatementExecutor<T, RSB>>(semaphore, connection) {

    override val statementType = "select"
    internal val columns: List<AnyColumn>
    private var useOuterJoins = false
    private var havingClause: HavingClause<*>? = null

    init {
        selectResultSet.selectables = selectables
        columns = selectables.flatMap { it.columns }
        semaphore.claim("select")
        columns.forEach {
            registerTable(it.table)
        }
    }

    private var limitRows: Int? = null
    private var orderByClauses = mutableListOf<OrderByClause>()

    /**
     * Used when you need fine-grained control over the table join syntax (outer joins), or when you are referring columns that do not
     * have a direct foreign key relationship, nor have an n-n join relationship. Example:
     * ```kotlin
     *  transaction.select(Employee.name).from(Employee.leftJoin(Hobby))
     * ```
     * In this example, not every `employee` has a record in the `hobby` table
     */
    fun from(joinChain: JoinChain): SelectStatementExecutor<T, RSB> {
        registerJoinChain(joinChain)
        return this
    }

    /**
     * Limits the number of rows fetched by adding a vendor-specific clause to the sql statement.
     * @param the maximum number of rows to be returned
     */
    fun limit(maxRows: Int): SelectStatementExecutor<T, RSB> {
        limitRows = if (maxRows < 1) 1 else maxRows
        return this
    }

    private fun addOrderByClause(column: AnyColumn, sortOrder: SortOrder) {
        val sameAsAggregate =
            columns.firstOrNull { it.aggregateType != null && it.tableDotName == column.tableDotName }
        orderByClauses.add(OrderByClause(sameAsAggregate ?: column, sortOrder))
    }

    /**
     * Sorts results based on the direction (ASC/DESC) the columns provided. Sorting is done in the where clause through an ORDER BY clause
     * Can be combined with [orderAsc] or  [orderDesc]
     * Example:
     * ```kotlin
     *  transaction.select(Employee.name).order(SortOrder.ASC, Employee.married, Employee.name)
     * ```
     */
    fun order(sortOrder: SortOrder, vararg columns: AnyColumn): SelectStatementExecutor<T, RSB> {
        columns.forEach { addOrderByClause(it, sortOrder) }
        return this
    }

    /**
     * Sorts results in ascending order based on the columns provided. Sorting is done in the where clause through an ORDER BY clause
     * Can be combined with [orderDesc]
     *
     * Equivalent to `order(SortOrder.ASC, columns)`
     * Example:
     * ```kotlin
     *  transaction.select(Employee.name).orderAsc(Employee.married, Employee.name).orderDesc(Employee.salary)
     * ```
     *
     */
    fun orderAsc(vararg columns: AnyColumn): SelectStatementExecutor<T, RSB> {
        columns.forEach { addOrderByClause(it, SortOrder.ASC) }
        return this
    }

    /**
     * Sorts results in descending order based on the columns provided. Sorting is done in the where clause through an ORDER BY clause.
     * Can be combined with [orderAsc]
     *
     * Equivalent to `order(SortOrder.DESC, columns)
     *
     * Example:
     * ```kotlin
     *  transaction.select(Employee.name).orderDesc(e.salary, e.name).orderAsc(Employee.married)
     * ```
     *
     */
    fun orderDesc(vararg columns: AnyColumn): SelectStatementExecutor<T, RSB> {
        columns.forEach { addOrderByClause(it, SortOrder.DESC) }
        return this
    }

    /**
     * Opens the whereclause for this select statement. Example:
     * ```kotlin
     * transaction.select(Book.isbn).where(Book.published.lt(LocalDate.of(1980,1,1))).asList()
     * ```
     *  If you want to select all rows without a where clause, simply omit it.
     *  ```kotlin
     *  transaction.select(Book.isbn).asList()
     *  ```
     */
    fun where(condition: SubClause): SelectStatementExecutor<T, RSB> {
        withWhereClause(condition)
        return this
    }

    /**
     * Signal that all tables involved in the select statement are joined using outer joins.
     * This has the consequence that non-nullable columns may yield null if there is no corresponding row in the outer join.
     * In the following example we list all books in the library and the loans, but it a book has not been loaned, `dateLoaned` will be null.
     * You must use the nullable counterparts of the non-null columns, like so:
     * ```kotlin
     *   transaction.select(Item.isbn, Loan.dateLoaned.nullable).useOuterJoins().asList()
     * ```
     */
    fun useOuterJoins(): SelectStatementExecutor<T, RSB> {
        useOuterJoins = true
        return this
    }

    /**
     * Executes the select statement, fetches all rows and returns the first result.
     * For better performance, use this only when you expect a single result, or use the [limit] clause in addition.
     * @throws com.dbobjekts.api.exception.StatementExecutionException if there are no results. To prevent this, use [firstOrNull]
     */
    fun first(): T {
        try {
            return execute().first().also { statementLog.logResult(it) }
        } finally {
            semaphore.clear()
        }
    }

    /**
     * Executes the select statement, fetches all rows and returns the first result, or null if there is no match.
     * For better performance, use this only when you expect a single result, or use the [limit] clause in addition.
     */
    fun firstOrNull(): T? {
        try {
            return execute().firstOrNull().also { statementLog.logResult(it) }
        } finally {
            semaphore.clear()
        }
    }

    /**
     * Executes the select statement, fetches all rows and returns them as a list of tuples
     */
    fun asList(): List<T> {
        try {
            return execute().asList().also { statementLog.logResult(it) }
        } finally {
            semaphore.clear()
        }
    }

    /**
     * Executes the select statement, fetches all rows and returns a slice of the result set.
     *
     * WARNING: do not use this in conjunction with a LIMIT clause.
     */
    fun asSlice(skip: Long, limit: Long): List<T> {
        try {
            return execute(Slice(skip, limit)).asList().also { statementLog.logResult(it) }
        } finally {
            semaphore.clear()
        }
    }

    private fun columnsToFetch(): List<ColumnInResultRow> =
        columns.mapIndexed { index, column -> ColumnInResultRow(1 + index, column) }

    /**
     * Executes the select query and lets you step through the results with a custom predicate that receives the current row data
     * and returns a Boolean to indicate whether to proceed or not. Example:
     * ```kotlin
     *     transaction.select(e.name).forEachRow({ index, row ->
     *        buffer.add(row)
     *        !buffer.memoryFull()
     *     })
     *  ```
     *
     * This can be useful to prevent memory overruns with very large result sets
     */
    fun forEachRow(predicate: (Int, T) -> Boolean) {
        val sql = toSQL()
        val params = getWhereClause().getParameters()
        semaphore.clear();

        connection.prepareAndExecuteForSelectWithRowIterator<T, RSB>(
            sql,
            params,
            columnsToFetch(),
            selectResultSet,
            predicate
        )
    }

    /**
     * Returns a [ResultSetIterator], which implements [Iterator].
     *
     * This delegates to the underlying [ResultSet] for each call to `next()`, which makes it more memory-efficient for very large data sets by not loading all rows into a single list.
     *
     * WARNING: You cannot return a [ResultSetIterator] from a transaction block, as the underlying [Connection] will be closed.
     *
     * ```kotlin
     * // this will fail at runtime
     * val iterator = tm { it.select(Employee).iterator() }
     * ```
     */
    fun iterator(): ResultSetIterator<T, RSB> {
        val sql = toSQL()
        val params = getWhereClause().getParameters()
        semaphore.clear();
        return connection.createRowIterator(
            sql,
            params,
            columnsToFetch(),
            selectResultSet
        )
    }

    /**
     * Adds optional restrictions on the values of an aggregated column (sum, min, max, etc). A [HavingClause] is created with the [Aggregate] object.
     *
     * Conditions can be chained, but nesting is not allowed. The left operand is always the aggregated column, while the right side is a Long or Double.
     *
     *
     * The following query selects the name and number of certificates for every employee has either one or two certificates.
     *
     * ```kotlin
     *  it.select(e.name, ce.name.count())
     *    .having(Aggregate.gt(0).and().lt(3))
     * ```
     */
    fun having(havingClause: HavingClause<*>): SelectStatementExecutor<T, RSB> {
        if (!Aggregate.containsOneGroupByAggregate(columns)) {
            throw StatementBuilderException("Use of the 'having' clause requires exactly one column to be designated as an aggregate with sum(), min(), max(), avg(), count() or distinct().")
        }
        this.havingClause = havingClause
        return this
    }

    private fun execute(slice: Slice? = null): RSB {
        val sql = toSQL()
        val params: List<SqlParameter<*>> = getWhereClause().getParameters()
        val merged: List<SqlParameter<*>> = havingClause?.let {
            val max = if (params.isEmpty()) 0 else params.map { it.oneBasedPosition }.max()
            params + it.parameters(max + 1)
        } ?: params
        return connection.prepareAndExecuteForSelect<RSB>(
            sql,
            merged,
            columnsToFetch(),
            selectResultSet,
            slice
        )
    }

    private fun toSQL(): String {
        getWhereClause().getFlattenedConditions().forEach { registerTable(it.column.table) }
        val builder = SelectStatementSqlBuilder()
        builder.withWhereClause(getWhereClause())
        builder.withJoinChain(joinChainSQL(useOuterJoins).toSQL())
            .withOrderByClause(orderByClauses.toList())
            .withColumnsToSelect(columnsToFetch())
            .withHavingClause(havingClause?.toSQL())
            .build()
        limitRows?.let { builder.withLimitClause(limitRows!!, { r: Int -> connection.vendorSpecificProperties.getLimitClause(r) }) }
        return builder.build()
    }

    override fun toString() = toSQL()

}

