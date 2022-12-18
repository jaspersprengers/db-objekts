package com.dbobjekts.integration.h2.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Gadget:Table("GADGET"), HasUpdateBuilder<GadgetUpdateBuilder, GadgetInsertBuilder> {
    val id = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "ID")
    val name = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "NAME")
    override val columns: List<AnyColumn> = listOf(id,name)
    override fun updater(connection: ConnectionAdapter): GadgetUpdateBuilder = GadgetUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapter): GadgetInsertBuilder = GadgetInsertBuilder(connection)
}

class GadgetUpdateBuilder(connection: ConnectionAdapter) : UpdateBuilderBase(Gadget, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun name(value: String?): GadgetUpdateBuilder = ct.put(Gadget.name, value)
}

class GadgetInsertBuilder(connection: ConnectionAdapter):InsertBuilderBase(Gadget, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun name(value: String?): GadgetInsertBuilder = ct.put(Gadget.name, value)

}

