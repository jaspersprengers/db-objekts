package com.dbobjekts.integration.h2.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyColumnAndValue
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.WriteQueryAccessors
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object EmployeeDepartment:Table("EMPLOYEE_DEPARTMENT"), HasUpdateBuilder<EmployeeDepartmentUpdateBuilder, EmployeeDepartmentInsertBuilder> {
    val employeeId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "EMPLOYEE_ID", Employee.id)
    val departmentId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "DEPARTMENT_ID", Department.id)
    override val columns: List<AnyColumn> = listOf(employeeId,departmentId)
    override val metadata: WriteQueryAccessors<EmployeeDepartmentUpdateBuilder, EmployeeDepartmentInsertBuilder> = WriteQueryAccessors(EmployeeDepartmentUpdateBuilder(), EmployeeDepartmentInsertBuilder())
}

class EmployeeDepartmentUpdateBuilder() : UpdateBuilderBase(EmployeeDepartment) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override fun data(): Set<AnyColumnAndValue> = ct.data
    override fun clear(){ct.data.clear()}

    fun employeeId(value: Long): EmployeeDepartmentUpdateBuilder = ct.put(EmployeeDepartment.employeeId, value)
    fun departmentId(value: Long): EmployeeDepartmentUpdateBuilder = ct.put(EmployeeDepartment.departmentId, value)
}

class EmployeeDepartmentInsertBuilder():InsertBuilderBase(){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override fun data(): Set<AnyColumnAndValue> = ct.data
    override fun clear(){ct.data.clear()}

    fun employeeId(value: Long): EmployeeDepartmentInsertBuilder = ct.put(EmployeeDepartment.employeeId, value)
    fun departmentId(value: Long): EmployeeDepartmentInsertBuilder = ct.put(EmployeeDepartment.departmentId, value)

    fun mandatoryColumns(employeeId: Long, departmentId: Long) : EmployeeDepartmentInsertBuilder {
      ct.put(EmployeeDepartment.employeeId, employeeId)
      ct.put(EmployeeDepartment.departmentId, departmentId)
      return this
    }

}

