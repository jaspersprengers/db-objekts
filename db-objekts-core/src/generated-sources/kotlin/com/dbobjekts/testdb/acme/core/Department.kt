package com.dbobjekts.testdb.acme.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.Entity
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Department:Table<DepartmentRow>("DEPARTMENT"), HasUpdateBuilder<DepartmentUpdateBuilder, DepartmentInsertBuilder> {
    val id = com.dbobjekts.metadata.column.SequenceKeyLongColumn(this, "ID", "DEPARTMENT_SEQ")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "NAME")
    override val columns: List<AnyColumn> = listOf(id,name)
    override fun toValue(values: List<Any?>) = DepartmentRow(values[0] as Long,values[1] as String)
    override fun metadata(): WriteQueryAccessors<DepartmentUpdateBuilder, DepartmentInsertBuilder> = WriteQueryAccessors(DepartmentUpdateBuilder(), DepartmentInsertBuilder())
}

class DepartmentUpdateBuilder() : UpdateBuilderBase(Department) {
    fun name(value: String): DepartmentUpdateBuilder = put(Department.name, value)

    override fun updateRow(entity: Entity<*, *>): Long {
      entity as DepartmentRow
      add(Department.id, entity.id)
      add(Department.name, entity.name)
      return where (Department.id.eq(entity.id))
    }    
        
}

class DepartmentInsertBuilder():InsertBuilderBase(){
    fun name(value: String): DepartmentInsertBuilder = put(Department.name, value)

    fun mandatoryColumns(name: String) : DepartmentInsertBuilder {
      mandatory(Department.name, name)
      return this
    }


    override fun insertRow(entity: Entity<*, *>): Long {
      entity as DepartmentRow
      add(Department.name, entity.name)
      return execute()
    }    
        
}


data class DepartmentRow(
val id: Long = 0,
  val name: String    
) : Entity<DepartmentUpdateBuilder, DepartmentInsertBuilder>(Department.metadata())
        
