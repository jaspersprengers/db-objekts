package com.dbobjekts.statement.select

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyTable
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.joins.TableJoinChain
import com.dbobjekts.metadata.TableOrJoin
import com.dbobjekts.metadata.column.Column
import com.dbobjekts.statement.ColumnInResultRow
import com.dbobjekts.api.ResultRow
import com.dbobjekts.api.Semaphore
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.metadata.Selectable
import com.dbobjekts.statement.StatementBase
import com.dbobjekts.statement.whereclause.EmptyWhereClause
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

    init {
        selectResultSet.selectables = selectables
        columns = selectables.flatMap { it.columns }
        semaphore.claim("select")
        columns.forEach { registerTable(it.table) }
    }

    private var limitRows: Int? = null
    private var orderByClauses = mutableListOf<OrderByClause<*>>()

    /**
     * Used when you need fine-grained control over the table join syntax (outer joins), or when you are referring columns that do not
     * have a direct foreign key relationship, nor have an n-n join relationship. Example:
     * ```kotlin
     *  transaction.select(Employee.name).from(Employee.leftJoin(Hobby))
     * ```
     * In this example, not every `employee` has a record in the `hobby` table
     */
    fun from(joinChain: TableOrJoin): SelectStatementExecutor<T, RSB> {
        when (val obj = joinChain) {
            is TableJoinChain -> registerJoinChain(obj)
            is AnyTable -> registerDrivingTable(obj)
            else -> throw StatementBuilderException("Unsupported operation: argument must be subclass of TableJoinChain or Table")
        }
        return this
    }

    /**
     * Limits the number of rows fetched by adding a LIMIT clause to the sql statement.
     * @param the maximum number of rows to be returned
     */
    fun limit(maxRows: Int): SelectStatementExecutor<T, RSB> {
        limitRows = if (maxRows < 1) 1 else maxRows
        return this
    }

    private fun <C> addOrderByClause(column: Column<C>, ascending: Boolean) {
        orderByClauses.add(OrderByClause<C>(column, ascending))
    }

    /**
     * Sorts results in ascending order based on the columns provided. Sorting is done in the where clause through an ORDER BY clause
     * Can be combined with [orderDesc]
     * Example:
     * ```kotlin
     *  transaction.select(Employee.name).orderAsc(Employee.married, Employee.name).orderDesc(Employee.salary)
     * ```
     *
     */
    fun orderAsc(vararg columns: AnyColumn): SelectStatementExecutor<T, RSB> {
        columns.forEach { addOrderByClause(it, ascending = true) }
        return this
    }

    /**
     * Sorts results in descending order based on the columns provided. Sorting is done in the where clause through an ORDER BY clause.
     * Can be combined with [orderAsc]
     * Example:
     * ```kotlin
     *  transaction.select(Employee.name).orderDesc(e.salary, e.name).orderAsc(Employee.married)
     * ```
     *
     */
    fun orderDesc(vararg columns: AnyColumn): SelectStatementExecutor<T, RSB> {
        columns.forEach { addOrderByClause(it, ascending = false) }
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
    fun first(): T = execute().first().also { semaphore.clear(); statementLog.logResult(it) }

    /**
     * Executes the select statement, fetches all rows and returns the first result, or null if there is no match.
     * For better performance, use this only when you expect a single result, or use the [limit] clause in addition.
     */
    fun firstOrNull(): T? = execute().firstOrNull().also { semaphore.clear(); statementLog.logResult(it) }

    /**
     * Executes the select statement, fetches all rows and returns them as a list of tuples
     */
    fun asList(): List<T> = execute().asList().also { semaphore.clear(); statementLog.logResult(it) }

    private fun columnsToFetch(): List<ColumnInResultRow> =
        columns.mapIndexed { index, column -> ColumnInResultRow(1 + index, column) }

    /**
     * Executes the select query and lets you step through the results with a custom function that receives the current row data
     * and returns a Boolean to indicate whether to proceed or not. Example:
     * ```kotlin
     *     transaction.select(e.name).forEachRow({ row ->
     *        buffer.add(row)
     *        !buffer.memoryFull()
     *     })
     *  ```
     *
     * This can be useful for huge result sets that would run into memory problems when fetched at once into a list.
     */
    fun forEachRow(currentRow: (T) -> Boolean) {
        val sql = toSQL()
        val params = getWhereClause().getParameters()
        semaphore.clear();
        connection.prepareAndExecuteForSelectWithRowIterator<T, RSB>(
            sql,
            params,
            columnsToFetch(),
            selectResultSet,
            currentRow
        )
    }

    private fun execute(): RSB {
        val sql = toSQL()
        val params = getWhereClause().getParameters()
        return connection.prepareAndExecuteForSelect<RSB>(
            sql,
            params,
            columnsToFetch(),
            selectResultSet
        )
    }

    private fun toSQL(): String {
        getWhereClause().getFlattenedConditions().forEach { registerTable(it.column.table) }
        val builder = SelectStatementSqlBuilder()
        builder.withWhereClause(getWhereClause())
        builder.withJoinChain(buildJoinChain(useOuterJoins))
            .withOrderByClause(orderByClauses.toList())
            .withColumnsToSelect(columnsToFetch())
            .build()
        limitRows?.let { builder.withLimitClause(limitRows!!, { r: Int -> connection.vendorSpecificProperties.getLimitClause(r) }) }
        return builder.build()
    }

    override fun toString() = toSQL()

}

