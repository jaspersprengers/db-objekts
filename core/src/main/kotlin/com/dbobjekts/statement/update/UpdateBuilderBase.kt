package com.dbobjekts.statement.update

import com.dbobjekts.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapterImpl
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.Column
import com.dbobjekts.metadata.column.NullableColumnAndValue
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.whereclause.EmptyWhereClause
import com.dbobjekts.statement.whereclause.SubClause

interface HasUpdateBuilder<U : UpdateBuilderBase, I : InsertBuilderBase> {
    fun updater(connection: ConnectionAdapterImpl): U

    fun inserter(connection: ConnectionAdapterImpl): I
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
    internal val table: Table,
    internal val connection: ConnectionAdapterImpl
) {
    abstract protected fun data(): Set<AnyColumnAndValue>
    private var whereClause: SubClause? = null

    //it's important that the order of insertion is observed, and the same column can't be added twice.
    fun where(subclause: SubClause): UpdateBuilderBase {
        whereClause = subclause
        return this
    }

    fun execute(): Long {
        if (data().isEmpty())
            throw IllegalStateException("Need at least one column to update")
        val stm = UpdateStatementExecutor(connection, table, data().toList())
        return stm.where(whereClause ?: EmptyWhereClause)
    }

}

