package com.dbobjekts.statement

import com.dbobjekts.api.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.TableJoinChain
import com.dbobjekts.metadata.TableJoinChainBuilder
import com.dbobjekts.statement.whereclause.EmptyWhereClause
import com.dbobjekts.statement.whereclause.SubClause
import com.dbobjekts.statement.whereclause.WhereClause
import com.dbobjekts.util.StatementLogger

abstract class StatementBase<W>(internal val connection: ConnectionAdapter) {

    internal open val catalog: Catalog = connection.catalog()
    internal val statementLog: StatementLogger = connection.statementLog

    internal var tables: MutableList<Table> = mutableListOf<Table>()
    private var _drivingTable: Table? = null
    private var _joinChain: TableJoinChain? = null
    protected lateinit var _whereClause: WhereClause

    internal fun registerJoinChain(joinChain: TableJoinChain) {
        _joinChain = joinChain
    }

    internal fun registerDrivingTable(table: Table) {
        _drivingTable = table
    }

    internal fun joinChain(): TableJoinChain =
        _joinChain ?: TableJoinChainBuilder(
            catalog = catalog,
            drivingTable = _drivingTable ?: tables.firstOrNull() ?: throw IllegalStateException("Cannot build query: no tables to select"),
            tables = tables.toList()
        ).build()


    internal fun registerTable(table: Table) {
        if (!tables.contains(catalog.assertContainsTable(table)))
            tables.add(table)
    }

    internal fun withWhereClause(clause: SubClause) {
        this._whereClause = WhereClause(clause, connection.vendor)
    }

    internal fun whereClauseIsSpecified() = this::_whereClause.isInitialized

    open internal fun getWhereClause(): WhereClause =
        if (!whereClauseIsSpecified()) WhereClause(EmptyWhereClause, connection.vendor) else _whereClause

    internal fun registerTablesInColumn(values: List<AnyColumnAndValue>) {
        val tables = values.map { it.column.table }.toSet()
        if (tables.size != 1) throw IllegalStateException("Parameter should contain exactly one table but was ${tables.size}")
        tables.forEach { registerTable(it) }
    }

    internal fun getTable(): Table =
        if (tables.isEmpty()) throw IllegalStateException("Expected at least one table for query") else tables.first()

}
