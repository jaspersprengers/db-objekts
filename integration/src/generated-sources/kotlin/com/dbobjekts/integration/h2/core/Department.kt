package com.dbobjekts.integration.h2.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyColumnAndValue
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.WriteQueryAccessors
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Department:Table("DEPARTMENT"), HasUpdateBuilder<DepartmentUpdateBuilder, DepartmentInsertBuilder> {
    val id = com.dbobjekts.metadata.column.SequenceKeyLongColumn(this, "ID", "DEPARTMENT_SEQ")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "NAME")
    override val columns: List<AnyColumn> = listOf(id,name)
    override val metadata: WriteQueryAccessors<DepartmentUpdateBuilder, DepartmentInsertBuilder> = WriteQueryAccessors(DepartmentUpdateBuilder(), DepartmentInsertBuilder())
}

class DepartmentUpdateBuilder() : UpdateBuilderBase(Department) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override fun data(): Set<AnyColumnAndValue> = ct.data
    override fun clear(){ct.data.clear()}

    fun name(value: String): DepartmentUpdateBuilder = ct.put(Department.name, value)
}

class DepartmentInsertBuilder():InsertBuilderBase(){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override fun data(): Set<AnyColumnAndValue> = ct.data
    override fun clear(){ct.data.clear()}

    fun name(value: String): DepartmentInsertBuilder = ct.put(Department.name, value)

    fun mandatoryColumns(name: String) : DepartmentInsertBuilder {
      ct.put(Department.name, name)
      return this
    }

}

