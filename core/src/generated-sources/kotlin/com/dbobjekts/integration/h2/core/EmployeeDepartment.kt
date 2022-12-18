package com.dbobjekts.integration.h2.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object EmployeeDepartment:Table("EMPLOYEE_DEPARTMENT"), HasUpdateBuilder<EmployeeDepartmentUpdateBuilder, EmployeeDepartmentInsertBuilder> {
    val employeeId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "EMPLOYEE_ID", Employee.id)
    val departmentId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "DEPARTMENT_ID", Department.id)
    override val columns: List<AnyColumn> = listOf(employeeId,departmentId)
    override fun updater(connection: ConnectionAdapter): EmployeeDepartmentUpdateBuilder = EmployeeDepartmentUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapter): EmployeeDepartmentInsertBuilder = EmployeeDepartmentInsertBuilder(connection)
}

class EmployeeDepartmentUpdateBuilder(connection: ConnectionAdapter) : UpdateBuilderBase(EmployeeDepartment, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun employeeId(value: Long): EmployeeDepartmentUpdateBuilder = ct.put(EmployeeDepartment.employeeId, value)
    fun departmentId(value: Long): EmployeeDepartmentUpdateBuilder = ct.put(EmployeeDepartment.departmentId, value)
}

class EmployeeDepartmentInsertBuilder(connection: ConnectionAdapter):InsertBuilderBase(EmployeeDepartment, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun employeeId(value: Long): EmployeeDepartmentInsertBuilder = ct.put(EmployeeDepartment.employeeId, value)
    fun departmentId(value: Long): EmployeeDepartmentInsertBuilder = ct.put(EmployeeDepartment.departmentId, value)

    fun mandatoryColumns(employeeId: Long, departmentId: Long) : EmployeeDepartmentInsertBuilder {
      ct.put(EmployeeDepartment.employeeId, employeeId)
      ct.put(EmployeeDepartment.departmentId, departmentId)
      return this
    }

}

