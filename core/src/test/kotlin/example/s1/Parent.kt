package com.dbobjekts.example.s1

import com.dbobjekts.AnyColumn
import com.dbobjekts.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase

object Parent:Table("parent"), HasUpdateBuilder<ParentUpdateBuilder, ParentInsertBuilder> {
    val id = com.dbobjekts.metadata.column.AutoKeyIntegerColumn(this, "id")
    val kind = com.dbobjekts.example.custom.AddressTypeColumn(this, "kind")
    override val columns: List<AnyColumn> = listOf(id,kind)
    override fun updater(connection: ConnectionAdapter): ParentUpdateBuilder = ParentUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapter): ParentInsertBuilder = ParentInsertBuilder(connection)
}

class ParentUpdateBuilder(connection: ConnectionAdapter) : UpdateBuilderBase(Parent, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun kind(value: com.dbobjekts.example.custom.AddressType?): ParentUpdateBuilder = ct.put(Parent.kind, value)
}

class ParentInsertBuilder(connection: ConnectionAdapter): InsertBuilderBase(Parent, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun kind(value: com.dbobjekts.example.custom.AddressType?): ParentInsertBuilder = ct.put(Parent.kind, value)

}

