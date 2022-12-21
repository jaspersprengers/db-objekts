package com.dbobjekts.statement.select

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.joins.TableJoinChain
import com.dbobjekts.metadata.TableOrJoin
import com.dbobjekts.metadata.column.Column
import com.dbobjekts.statement.ColumnInResultRow
import com.dbobjekts.api.ResultRow
import com.dbobjekts.api.Semaphore
import com.dbobjekts.statement.StatementBase
import com.dbobjekts.statement.whereclause.EmptyWhereClause
import com.dbobjekts.statement.whereclause.SubClause

class SelectStatementExecutor<T, RSB : ResultRow<T>>(
    semaphore: Semaphore,
    connection: ConnectionAdapter,
    internal val columns: List<AnyColumn>,
    internal val selectResultSet: RSB
) : StatementBase<SelectStatementExecutor<T, RSB>>(semaphore, connection) {

    override val statementType = "select"

    init {
        semaphore.claim("select")
        columns.forEach { registerTable(it.table) }
    }

    private var limitRows: Int? = null
    private var orderByClauses = mutableListOf<OrderByClause<*>>()

    fun from(joinChain: TableOrJoin): SelectStatementExecutor<T, RSB> {
        when (val obj = joinChain) {
            is TableJoinChain -> registerJoinChain(obj)
            is Table -> registerDrivingTable(obj)
            else -> throw IllegalStateException("Unsupported operation: argument must be subclass of TableJoinChain or Table")
        }
        return this
    }

    fun limit(maxRows: Int): SelectStatementExecutor<T, RSB> {
        limitRows = if (maxRows < 1) 1 else maxRows
        return this
    }

    private fun <C> addOrderByClause(column: Column<C>, ascending: Boolean) {
        orderByClauses.add(OrderByClause<C>(column, ascending))
    }

    fun orderAsc(vararg columns: AnyColumn): SelectStatementExecutor<T, RSB> {
        columns.forEach { addOrderByClause(it, ascending = true) }
        return this
    }

    fun orderDesc(vararg columns: AnyColumn): SelectStatementExecutor<T, RSB> {
        columns.forEach { addOrderByClause(it, ascending = false) }
        return this
    }

    fun where(condition: SubClause): SelectStatementExecutor<T, RSB> {
        withWhereClause(condition)
        return this
    }

    fun where(): SelectStatementExecutor<T, RSB> = where(EmptyWhereClause)

    fun first(): T = execute().first().also { semaphore.clear(); statementLog.logResult(it) }

    fun firstOrNull(): T? = execute().firstOrNull().also { semaphore.clear(); statementLog.logResult(it) }

    fun asList(): List<T> = execute().asList().also { semaphore.clear(); statementLog.logResult(it) }

    private fun columnsToFetch(): List<ColumnInResultRow> =
        columns.mapIndexed { index, column -> ColumnInResultRow(1 + index, column) }

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
        try {
            getWhereClause().getFlattenedConditions().forEach { registerTable(it.column.table) }
            val builder = SelectStatementSqlBuilder()
            builder.withWhereClause(getWhereClause())
            builder.withJoinChain(joinChain())
                .withOrderByClause(orderByClauses.toList())
                .withColumnsToSelect(columnsToFetch())
                .build()
            limitRows?.let { builder.withLimitClause(limitRows!!, { r: Int -> connection.vendorSpecificProperties.getLimitClause(r) }) }
            return builder.build()
        } catch (e: Exception) {
            return "CANNOT SERIALIZE"
        }
    }

    override fun toString() = toSQL()

}

