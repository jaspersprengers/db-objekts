package com.dbobjekts.integration.h2.core

import com.dbobjekts.AnyColumn
import com.dbobjekts.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapterImpl
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Department:Table("DEPARTMENT"), HasUpdateBuilder<DepartmentUpdateBuilder, DepartmentInsertBuilder> {
    val id = com.dbobjekts.metadata.column.SequenceKeyLongColumn(this, "ID", "DEPARTMENT_SEQ")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "NAME")
    override val columns: List<AnyColumn> = listOf(id,name)
    override fun updater(connection: ConnectionAdapterImpl): DepartmentUpdateBuilder = DepartmentUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapterImpl): DepartmentInsertBuilder = DepartmentInsertBuilder(connection)
}

class DepartmentUpdateBuilder(connection: ConnectionAdapterImpl) : UpdateBuilderBase(Department, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun name(value: String): DepartmentUpdateBuilder = ct.put(Department.name, value)
}

class DepartmentInsertBuilder(connection: ConnectionAdapterImpl):InsertBuilderBase(Department, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun name(value: String): DepartmentInsertBuilder = ct.put(Department.name, value)

    fun mandatoryColumns(name: String) : DepartmentInsertBuilder {
      ct.put(Department.name, name)
      return this
    }

}

