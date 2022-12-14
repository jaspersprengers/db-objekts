package com.dbobjekts.integration.h2.core

import com.dbobjekts.AnyColumn
import com.dbobjekts.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Department:Table("DEPARTMENT"), HasUpdateBuilder<DepartmentUpdateBuilder, DepartmentInsertBuilder> {
    val id = com.dbobjekts.metadata.column.SequenceKeyLongColumn(this, "ID", "core.DEPARTMENT_SEQ")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "NAME")
    val createdDt = com.dbobjekts.metadata.column.TimeStampColumn(this, "CREATED_DT")
    val modifiedDt = com.dbobjekts.metadata.column.NullableTimeStampColumn(this, "MODIFIED_DT")
    override val columns: List<AnyColumn> = listOf(id,name,createdDt,modifiedDt)
    override fun updater(connection: ConnectionAdapter): DepartmentUpdateBuilder = DepartmentUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapter): DepartmentInsertBuilder = DepartmentInsertBuilder(connection)
}

class DepartmentUpdateBuilder(connection: ConnectionAdapter) : UpdateBuilderBase(Department, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun name(value: String): DepartmentUpdateBuilder = ct.put(Department.name, value)
    fun createdDt(value: java.time.Instant): DepartmentUpdateBuilder = ct.put(Department.createdDt, value)
    fun modifiedDt(value: java.time.Instant?): DepartmentUpdateBuilder = ct.put(Department.modifiedDt, value)
}

class DepartmentInsertBuilder(connection: ConnectionAdapter):InsertBuilderBase(Department, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun name(value: String): DepartmentInsertBuilder = ct.put(Department.name, value)
    fun createdDt(value: java.time.Instant): DepartmentInsertBuilder = ct.put(Department.createdDt, value)
    fun modifiedDt(value: java.time.Instant?): DepartmentInsertBuilder = ct.put(Department.modifiedDt, value)

    fun mandatoryColumns(name: String, createdDt: java.time.Instant) : DepartmentInsertBuilder {
      ct.put(Department.name, name)
      ct.put(Department.createdDt, createdDt)
      return this
    }

}
