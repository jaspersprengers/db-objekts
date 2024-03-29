package com.dbobjekts.testdb.acme.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.TableRowData
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.ForeignKeyLongColumn
import com.dbobjekts.statement.WriteQueryAccessors
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase

/**           
 * Auto-generated metadata object for db table CORE.EMPLOYEE_DEPARTMENT.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: [EMPLOYEE_ID, DEPARTMENT_ID]
 *
 * Foreign keys to: 
 * References by: CORE.EMPLOYEE,CORE.DEPARTMENT
 */
object EmployeeDepartment:Table<EmployeeDepartmentRow>("EMPLOYEE_DEPARTMENT"), HasUpdateBuilder<EmployeeDepartmentUpdateBuilder, EmployeeDepartmentInsertBuilder> {
    /**
     * Represents db column CORE.EMPLOYEE_DEPARTMENT.EMPLOYEE_ID
     *
     * Foreign key to CORE.EMPLOYEE.ID
     */
    val employeeId = ForeignKeyLongColumn(this, "EMPLOYEE_ID", Employee.id)
    /**
     * Represents db column CORE.EMPLOYEE_DEPARTMENT.DEPARTMENT_ID
     *
     * Foreign key to CORE.DEPARTMENT.ID
     */
    val departmentId = ForeignKeyLongColumn(this, "DEPARTMENT_ID", Department.id)
    override val columns: List<AnyColumn> = listOf(employeeId,departmentId)
    override fun toValue(values: List<Any?>) = EmployeeDepartmentRow(values[0] as Long,values[1] as Long)
    override fun metadata(): WriteQueryAccessors<EmployeeDepartmentUpdateBuilder, EmployeeDepartmentInsertBuilder> = WriteQueryAccessors(EmployeeDepartmentUpdateBuilder(), EmployeeDepartmentInsertBuilder())
}

class EmployeeDepartmentUpdateBuilder() : UpdateBuilderBase(EmployeeDepartment) {
    fun employeeId(value: Long): EmployeeDepartmentUpdateBuilder = put(EmployeeDepartment.employeeId, value)
    fun departmentId(value: Long): EmployeeDepartmentUpdateBuilder = put(EmployeeDepartment.departmentId, value)
    
    /**
     * FOR INTERNAL USE ONLY
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as EmployeeDepartmentRow
      add(EmployeeDepartment.employeeId, rowData.employeeId)
      add(EmployeeDepartment.departmentId, rowData.departmentId)
      return where(EmployeeDepartment.employeeId.eq(rowData.employeeId).and(EmployeeDepartment.departmentId.eq(rowData.departmentId)))
    }    
        
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
) : TableRowData<EmployeeDepartmentUpdateBuilder, EmployeeDepartmentInsertBuilder>(EmployeeDepartment.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>(Pair(EmployeeDepartment.employeeId, employeeId),Pair(EmployeeDepartment.departmentId, departmentId))
}
        
