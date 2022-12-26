package com.dbobjekts.statement.update

import com.dbobjekts.api.AnyColumnAndValue
import com.dbobjekts.api.Semaphore
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.metadata.column.Column
import com.dbobjekts.metadata.column.NullableColumnAndValue
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.whereclause.SubClause

interface HasUpdateBuilder<U : UpdateBuilderBase, I : InsertBuilderBase> {
    /**
     * FOR DB-OBJEKTS INTERNAL USE ONLY.
     */
    fun metadata(): WriteQueryAccessors<U, I>

}

interface ColumnForWriteMapContainer<T> {

    fun <C> put(col: Column<C>, value: C?): T

    fun columnsForWrite(): List<AnyColumnAndValue>
}

class ColumnForWriteMapContainerImpl<T>(val builder: T) : ColumnForWriteMapContainer<T> {
    val data: HashSet<AnyColumnAndValue> = linkedSetOf<AnyColumnAndValue>()

    override fun <C> put(col: Column<C>, value: C?): T {
        if (value == null)
            data.add(NullableColumnAndValue(col, null))
        else
            data.add(col.create(value))
        return builder
    }

    override fun columnsForWrite(): List<AnyColumnAndValue> = data.toList()
}

abstract class UpdateBuilderBase(
    internal val table: Table) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    internal lateinit var connection: ConnectionAdapter
    internal lateinit var semaphore: Semaphore

    @Suppress("UNCHECKED_CAST")
    protected fun <B : UpdateBuilderBase, C> put(col: Column<C>, value: C?): B{
        ct.put(col, value)
        return this as B
    }

    /**
     * Opens the whereclause for this update statement.
     */
    fun where(whereClause: SubClause): Long {
        if (ct.data.isEmpty())
            throw IllegalStateException("Need at least one column to update")
        val stm = UpdateStatementExecutor(semaphore, connection, table, ct.data.toList())
        return stm.where(whereClause ).also { connection.statementLog.logResult(it) }
    }

}

