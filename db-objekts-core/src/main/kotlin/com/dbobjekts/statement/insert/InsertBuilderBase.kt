package com.dbobjekts.statement.insert

import com.dbobjekts.api.TableRowData
import com.dbobjekts.statement.Semaphore
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.column.Column
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl

/**
 * A concrete `InsertBuilderBase` is auto-generated for each table and provides setter methods for each column to supply the values that need to be inserted.
 *
 * It is the class returned by the call to `insert(..)` in the following example.
 *
 * ```kotlin
 *  tr.insert(EmployeeAddressRow(johnsId, addressId, AddressType.HOME))
 * ```
 */
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

    abstract fun insertRow(rowData: TableRowData<*, *>): Long

    /**
     * Executes the insert statement and persists a new row to the table.
     * @return the primary key of the new row if it is auto-generated. Otherwise, it returns the result of the underlying [java.sql.PreparedStatement.execute] call
     */
    fun execute(): Long = InsertStatementExecutor(semaphore, connection, ct.data.toList(), connection.vendor.properties).execute()
        .also { connection.statementLog.logResult(it) }
}
