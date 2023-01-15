package com.dbobjekts.statement

import com.dbobjekts.api.AnyColumnAndValue
import com.dbobjekts.api.AnyTable
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.metadata.joins.JoinChain
import com.dbobjekts.metadata.joins.ManualJoinChain

import com.dbobjekts.metadata.joins.DerivedJoin
import com.dbobjekts.metadata.joins.DerivedJoinChainBuilder
import com.dbobjekts.statement.whereclause.EmptyWhereClause
import com.dbobjekts.statement.whereclause.SubClause
import com.dbobjekts.statement.whereclause.WhereClause
import com.dbobjekts.util.StatementLogger

abstract class StatementBase<W>(
    protected val semaphore: Semaphore,
    internal val connection: ConnectionAdapter
) {

    internal open val catalog: Catalog = connection.catalog()
    internal val statementLog: StatementLogger = connection.statementLog
    internal var tables: MutableList<AnyTable> = mutableListOf<AnyTable>()
    internal abstract val statementType: String

    private var _joinChain: JoinChain? = null
    protected lateinit var _whereClause: WhereClause

    internal fun registerJoinChain(joinChain: JoinChain) {
        _joinChain = joinChain
    }

    internal fun joinChainSQL(useOuterJoins: Boolean = false): JoinChain =  _joinChain ?: DerivedJoinChainBuilder(
        catalog = catalog,
        drivingTable = tables.firstOrNull()
            ?: throw StatementBuilderException("Cannot build query: no tables to select"),
        tables = tables.toList(),
        useOuterJoins = useOuterJoins
    ).build()

    internal fun registerTable(table: AnyTable) {
        if (!tables.contains(catalog.assertContainsTable(table)))
            tables.add(table)
    }

    internal fun withWhereClause(clause: SubClause) {
        this._whereClause = WhereClause(clause)
    }

    internal fun whereClauseIsSpecified() = this::_whereClause.isInitialized

    open internal fun getWhereClause(): WhereClause =
        if (!whereClauseIsSpecified()) WhereClause(EmptyWhereClause) else _whereClause

    internal fun registerTablesInColumn(values: List<AnyColumnAndValue>) {
        val tables = values.map { it.column.table }.toSet()
        if (tables.size != 1) throw StatementBuilderException("Parameter should contain exactly one table but was ${tables.size}")
        tables.forEach { registerTable(it) }
    }

    internal fun getTable(): AnyTable =
        if (tables.isEmpty()) throw StatementBuilderException("Expected at least one table for query") else tables.first()

}
