package com.dbobjekts.testdb.acme.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.Entity
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Department:Table<DepartmentRow>("DEPARTMENT"), HasUpdateBuilder<DepartmentUpdateBuilder, DepartmentInsertBuilder> {
    val id = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "ID")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "NAME")
    override val columns: List<AnyColumn> = listOf(id,name)
    override fun toValue(values: List<Any?>) = DepartmentRow(values[0] as Long,values[1] as String)
    override fun metadata(): WriteQueryAccessors<DepartmentUpdateBuilder, DepartmentInsertBuilder> = WriteQueryAccessors(DepartmentUpdateBuilder(), DepartmentInsertBuilder())
}

class DepartmentUpdateBuilder() : UpdateBuilderBase(Department) {
    fun name(value: String): DepartmentUpdateBuilder = put(Department.name, value)
    override fun updateRow(entity: Entity<*, *>): Long = throw RuntimeException()
}

class DepartmentInsertBuilder():InsertBuilderBase(){
       fun name(value: String): DepartmentInsertBuilder = put(Department.name, value)
    override fun insertRow(entity: Entity<*, *>): Long = throw RuntimeException()
    fun mandatoryColumns(name: String) : DepartmentInsertBuilder {
      mandatory(Department.name, name)
      return this
    }

}

data class DepartmentRow(
    val id: Long,
    val name: String)
