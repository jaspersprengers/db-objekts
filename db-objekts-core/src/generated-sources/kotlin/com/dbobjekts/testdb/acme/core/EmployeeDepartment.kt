package com.dbobjekts.testdb.acme.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object EmployeeDepartment:Table<EmployeeDepartmentRow>("EMPLOYEE_DEPARTMENT"), HasUpdateBuilder<EmployeeDepartmentUpdateBuilder, EmployeeDepartmentInsertBuilder> {
    val employeeId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "EMPLOYEE_ID", Employee.id)
    val departmentId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "DEPARTMENT_ID", Department.id)
    override val columns: List<AnyColumn> = listOf(employeeId,departmentId)
    override fun toValue(values: List<Any?>): EmployeeDepartmentRow = EmployeeDepartmentRow()

    override fun metadata(): WriteQueryAccessors<EmployeeDepartmentUpdateBuilder, EmployeeDepartmentInsertBuilder> = WriteQueryAccessors(EmployeeDepartmentUpdateBuilder(), EmployeeDepartmentInsertBuilder())
}
class EmployeeDepartmentRow()

class EmployeeDepartmentUpdateBuilder() : UpdateBuilderBase(EmployeeDepartment) {
    fun employeeId(value: Long): EmployeeDepartmentUpdateBuilder = put(EmployeeDepartment.employeeId, value)
    fun departmentId(value: Long): EmployeeDepartmentUpdateBuilder = put(EmployeeDepartment.departmentId, value)
}

class EmployeeDepartmentInsertBuilder():InsertBuilderBase(){
       fun employeeId(value: Long): EmployeeDepartmentInsertBuilder = put(EmployeeDepartment.employeeId, value)
    fun departmentId(value: Long): EmployeeDepartmentInsertBuilder = put(EmployeeDepartment.departmentId, value)

    fun mandatoryColumns(employeeId: Long, departmentId: Long) : EmployeeDepartmentInsertBuilder {
      mandatory(EmployeeDepartment.employeeId, employeeId)
      mandatory(EmployeeDepartment.departmentId, departmentId)
      return this
    }

}

