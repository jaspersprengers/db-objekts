package com.dbobjekts.statement.insert

import com.dbobjekts.api.TableRowData
import com.dbobjekts.api.Semaphore
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.column.Column
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl

abstract class InsertBuilderBase() {
    internal lateinit var connection: ConnectionAdapter
    internal lateinit var semaphore: Semaphore

    private val ct = ColumnForWriteMapContainerImpl(this)

    @Suppress("UNCHECKED_CAST")
    protected fun <B : InsertBuilderBase, C> put(col: Column<C>, value: C?): B {
        add(col, value)
        return this as B
    }

    protected fun <C> add(col: Column<C>, value: C?) {
        ct.put(col, value)
    }

    protected fun <C> mandatory(col: Column<C>, value: C?) {
        ct.put(col, value)
    }

    abstract fun insertRow(tableRowData: TableRowData<*, *>): Long

    /**
     * Executes the insert statement and persists a new row to the table.
     * @return the primary key of the new row if they are auto-generated. Otherwise, returns the result of the underlying PreparedStatement#executeUpdate call
     */
    fun execute(): Long = InsertStatementExecutor(semaphore, connection, ct.data.toList(), connection.vendor.properties).execute()
        .also { connection.statementLog.logResult(it) }
}
