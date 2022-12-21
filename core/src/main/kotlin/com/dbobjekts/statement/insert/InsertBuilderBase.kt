package com.dbobjekts.statement.insert

import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.column.Column
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.util.Errors

abstract class InsertBuilderBase() {
    internal lateinit var connection: ConnectionAdapter
    private val ct = ColumnForWriteMapContainerImpl(this)
    protected fun <B : InsertBuilderBase, C> put(col: Column<C>, value: C?): B{
        ct.put(col, value)
        return this as B
    }
    protected fun <C> mandatory(col: Column<C>, value: C?){
        ct.put(col, value)
    }
    internal fun validate() = Errors.require(ct.data.isNotEmpty(), "You must supply at least one column to insert")

    fun execute(): Long = InsertStatementExecutor(connection, ct.data.toList(), connection.vendor.properties).execute().also { connection.statementLog.logResult(it) }
}
