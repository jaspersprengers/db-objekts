package com.dbobjekts.statement.update

import com.dbobjekts.api.*
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.column.Column
import com.dbobjekts.metadata.column.NullableColumnAndValue
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.whereclause.EmptyWhereClause
import com.dbobjekts.statement.whereclause.SubClause

interface HasUpdateBuilder<U : UpdateBuilderBase, I : InsertBuilderBase> {
    /**
     * FOR DB-OBJEKTS INTERNAL USE ONLY.
     */
    fun metadata(): WriteQueryAccessors<U, I>

}


class ColumnForWriteMapContainerImpl<T>(val builder: T) {
    val data: HashSet<AnyColumnAndValue> = linkedSetOf<AnyColumnAndValue>()

    fun <C> put(col: Column<C>, value: C?): T {
        if (value == null)
            data.add(NullableColumnAndValue(col, null))
        else
            data.add(col.create(value))
        return builder
    }
}

abstract class UpdateBuilderBase(
    internal val table: AnyTable) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    internal lateinit var connection: ConnectionAdapter
    internal lateinit var semaphore: Semaphore

    @Suppress("UNCHECKED_CAST")
    protected fun <B : UpdateBuilderBase, C> put(col: Column<C>, value: C?): B{
        add(col, value)
        return this as B
    }

    protected fun <C> add(col: Column<C>, value: C?){
        ct.put(col, value)
    }

    abstract fun updateRow(tableRowData: TableRowData<*,*>): Long

    /**
     * Opens the whereclause for this update statement.
     */
    fun where(whereClause: SubClause): Long {
        if (ct.data.isEmpty())
            throw StatementBuilderException("Need at least one column to update")
        val stm = UpdateStatementExecutor(semaphore, connection, table, ct.data.toList())
        return stm.where(whereClause ).also { connection.statementLog.logResult(it) }
    }
    /**
     * Executes the update statement for all rows in a table. Handle with care!
     */
    fun where(): Long = where(EmptyWhereClause)

}

