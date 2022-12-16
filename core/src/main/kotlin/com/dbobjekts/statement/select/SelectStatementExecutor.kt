package com.dbobjekts.statement.select

import com.dbobjekts.AnyColumn
import com.dbobjekts.SQL
import com.dbobjekts.jdbc.ConnectionAdapterImpl
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.TableJoinChain
import com.dbobjekts.metadata.TableOrJoin
import com.dbobjekts.metadata.column.Column
import com.dbobjekts.result.ColumnInResultRow
import com.dbobjekts.result.ResultRow
import com.dbobjekts.statement.StatementBase
import com.dbobjekts.statement.whereclause.EmptyWhereClause
import com.dbobjekts.statement.whereclause.SubClause

class SelectStatementExecutor<T, RSB : ResultRow<T>>(
    connection: ConnectionAdapterImpl,
    internal val columns: List<AnyColumn>,
    internal val selectResultSet: RSB
) : StatementBase<SelectStatementExecutor<T, RSB>>(connection){

    init {
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

    fun noWhereClause(): SelectStatementExecutor<T, RSB> = where(EmptyWhereClause)

    fun first(): T = execute().first()

    fun firstOrNull(): T? = execute().firstOrNull()

    fun asList(): List<T> = execute().asList()

    private fun columnsToFetch(): List<ColumnInResultRow> =
        columns.mapIndexed { index, column -> ColumnInResultRow(1 + index, column) }

    fun forEachRow(currentRow: (T) -> Boolean) {
        val sql = toSQL()
        val params = getWhereClause().getParameters()
        logger.logStatement(sql, params)
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
        logger.logStatement(sql, params)
        return connection.prepareAndExecuteForSelect<RSB>(
            sql,
            params,
            columnsToFetch(),
            selectResultSet
        )
    }

    private fun toSQL(): SQL {
        getWhereClause().getFlattenedConditions().forEach { registerTable(it.column.table) }
        val builder = SelectStatementSqlBuilder()
        builder.withWhereClause(getWhereClause())
        builder.withJoinChain(joinChain())
            .withOrderByClause(orderByClauses.toList())
            .withColumnsToSelect(columnsToFetch())
            .build()
        limitRows?.let { builder.withLimitClause(limitRows!!, { r: Int -> connection.vendorSpecificProperties.getLimitClause(r) }) }
        return builder.build()
    }

    override fun toString() = toSQL().toString()

}

