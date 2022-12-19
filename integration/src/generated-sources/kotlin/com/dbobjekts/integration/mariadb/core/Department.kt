package com.dbobjekts.integration.mariadb.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Department:Table("DEPARTMENT"), HasUpdateBuilder<DepartmentUpdateBuilder, DepartmentInsertBuilder> {
    val id = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "id")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "name")
    override val columns: List<AnyColumn> = listOf(id,name)
    override fun updater(connection: ConnectionAdapter): DepartmentUpdateBuilder = DepartmentUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapter): DepartmentInsertBuilder = DepartmentInsertBuilder(connection)
}

class DepartmentUpdateBuilder(connection: ConnectionAdapter) : UpdateBuilderBase(Department, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun name(value: String): DepartmentUpdateBuilder = ct.put(Department.name, value)
}

class DepartmentInsertBuilder(connection: ConnectionAdapter):InsertBuilderBase(Department, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun name(value: String): DepartmentInsertBuilder = ct.put(Department.name, value)

    fun mandatoryColumns(name: String) : DepartmentInsertBuilder {
      ct.put(Department.name, name)
      return this
    }

}

