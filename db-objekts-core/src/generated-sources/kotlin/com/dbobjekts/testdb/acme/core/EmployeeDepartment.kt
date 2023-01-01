package com.dbobjekts.testdb.acme.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.TableRowData
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object EmployeeDepartment:Table<EmployeeDepartmentRow>("EMPLOYEE_DEPARTMENT"), HasUpdateBuilder<EmployeeDepartmentUpdateBuilder, EmployeeDepartmentInsertBuilder> {
    val employeeId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "EMPLOYEE_ID", Employee.id)
    val departmentId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "DEPARTMENT_ID", Department.id)
    override val columns: List<AnyColumn> = listOf(employeeId,departmentId)
    override fun toValue(values: List<Any?>) = EmployeeDepartmentRow(values[0] as Long,values[1] as Long)
    override fun metadata(): WriteQueryAccessors<EmployeeDepartmentUpdateBuilder, EmployeeDepartmentInsertBuilder> = WriteQueryAccessors(EmployeeDepartmentUpdateBuilder(), EmployeeDepartmentInsertBuilder())
}

class EmployeeDepartmentUpdateBuilder() : UpdateBuilderBase(EmployeeDepartment) {
    fun employeeId(value: Long): EmployeeDepartmentUpdateBuilder = put(EmployeeDepartment.employeeId, value)
    fun departmentId(value: Long): EmployeeDepartmentUpdateBuilder = put(EmployeeDepartment.departmentId, value)

    override fun updateRow(rowData: TableRowData<*, *>): Long = 
      throw StatementBuilderException("Sorry, but you cannot use row-based updates for table EmployeeDepartment. There must be exactly one column marked as primary key.")                
            
}

class EmployeeDepartmentInsertBuilder():InsertBuilderBase(){
    fun employeeId(value: Long): EmployeeDepartmentInsertBuilder = put(EmployeeDepartment.employeeId, value)
    fun departmentId(value: Long): EmployeeDepartmentInsertBuilder = put(EmployeeDepartment.departmentId, value)

    fun mandatoryColumns(employeeId: Long, departmentId: Long) : EmployeeDepartmentInsertBuilder {
      mandatory(EmployeeDepartment.employeeId, employeeId)
      mandatory(EmployeeDepartment.departmentId, departmentId)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as EmployeeDepartmentRow
      add(EmployeeDepartment.employeeId, rowData.employeeId)
      add(EmployeeDepartment.departmentId, rowData.departmentId)
      return execute()
    }    
        
}


data class EmployeeDepartmentRow(
  val employeeId: Long,
  val departmentId: Long    
) : TableRowData<EmployeeDepartmentUpdateBuilder, EmployeeDepartmentInsertBuilder>(EmployeeDepartment.metadata())
        
