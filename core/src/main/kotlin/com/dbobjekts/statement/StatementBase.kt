package com.dbobjekts.statement

import com.dbobjekts.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.TableJoinChain
import com.dbobjekts.metadata.TableJoinChainBuilder
import com.dbobjekts.statement.whereclause.EmptyWhereClause
import com.dbobjekts.statement.whereclause.SubClause
import com.dbobjekts.statement.whereclause.WhereClause
import com.dbobjekts.util.QueryLogger

abstract class StatementBase<W>(val connection: ConnectionAdapter) {

    open val catalog: Catalog = connection.catalog()
    val logger: QueryLogger = connection.queryLogger

    var tables: MutableList<Table> = mutableListOf<Table>()
    private var _drivingTable: Table? = null
    private var _joinChain: TableJoinChain? = null
    protected lateinit var _whereClause: WhereClause

     fun registerJoinChain(joinChain: TableJoinChain) {
        _joinChain = joinChain
    }

     fun registerDrivingTable(table: Table) {
        _drivingTable = table
    }

    fun joinChain(): TableJoinChain =
        _joinChain?:TableJoinChainBuilder(catalog = catalog,
                drivingTable = _drivingTable?:tables.firstOrNull()?:throw IllegalStateException("Cannot build query: no tables to select"),
                tables = tables.toList()).build()


     fun registerTable(table: Table) {
        if (!tables.contains(table))
            tables.add(table)
    }

     fun withWhereClause(clause: SubClause) {
        this._whereClause = WhereClause(clause, connection.vendor)
    }

     fun getWhereClause(): WhereClause = if (!this::_whereClause.isInitialized) WhereClause(EmptyWhereClause, connection.vendor) else _whereClause

     fun registerTablesInColumn(values: List<AnyColumnAndValue>) {
        val tables = values.map {it.column.table}.toSet()
        if (tables.size != 1) throw IllegalStateException("Parameter should contain exactly one table but was ${tables.size}")
        tables.forEach {registerTable(it)}
    }

    fun getTable(): Table = if (tables.isEmpty()) throw IllegalStateException("Expected at least one table for query") else tables.first()

}
