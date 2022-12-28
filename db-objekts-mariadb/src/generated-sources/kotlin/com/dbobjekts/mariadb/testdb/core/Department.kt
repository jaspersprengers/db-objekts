package com.dbobjekts.mariadb.testdb.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Department:Table("DEPARTMENT"), HasUpdateBuilder<DepartmentUpdateBuilder, DepartmentInsertBuilder> {
    val id = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "id")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "name")
    override val columns: List<AnyColumn> = listOf(id,name)
    override fun metadata(): WriteQueryAccessors<DepartmentUpdateBuilder, DepartmentInsertBuilder> = WriteQueryAccessors(DepartmentUpdateBuilder(), DepartmentInsertBuilder())
}

class DepartmentUpdateBuilder() : UpdateBuilderBase(Department) {
    fun name(value: String): DepartmentUpdateBuilder = put(Department.name, value)
}

class DepartmentInsertBuilder():InsertBuilderBase(){
       fun name(value: String): DepartmentInsertBuilder = put(Department.name, value)

    fun mandatoryColumns(name: String) : DepartmentInsertBuilder {
      mandatory(Department.name, name)
      return this
    }

}

