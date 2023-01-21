package com.dbobjekts.demo.db.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.TableRowData
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.ForeignKeyLongColumn
import com.dbobjekts.statement.WriteQueryAccessors
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase

/**           
 * Auto-generated metadata object for db table core.EMPLOYEE_DEPARTMENT.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: none
 *
 * Foreign keys to: 
 * References by: core.EMPLOYEE,core.DEPARTMENT
 */
object EmployeeDepartment:Table<EmployeeDepartmentRow>("EMPLOYEE_DEPARTMENT"), HasUpdateBuilder<EmployeeDepartmentUpdateBuilder, EmployeeDepartmentInsertBuilder> {
    /**
     * Represents db column core.EMPLOYEE_DEPARTMENT.employee_id
     *
     * Foreign key to core.EMPLOYEE.id
     */
    val employeeId = ForeignKeyLongColumn(this, "employee_id", Employee.id)
    /**
     * Represents db column core.EMPLOYEE_DEPARTMENT.department_id
     *
     * Foreign key to core.DEPARTMENT.id
     */
    val departmentId = ForeignKeyLongColumn(this, "department_id", Department.id)
    override val columns: List<AnyColumn> = listOf(employeeId,departmentId)
    override fun toValue(values: List<Any?>) = EmployeeDepartmentRow(values[0] as Long,values[1] as Long)
    override fun metadata(): WriteQueryAccessors<EmployeeDepartmentUpdateBuilder, EmployeeDepartmentInsertBuilder> = WriteQueryAccessors(EmployeeDepartmentUpdateBuilder(), EmployeeDepartmentInsertBuilder())
}

class EmployeeDepartmentUpdateBuilder() : UpdateBuilderBase(EmployeeDepartment) {
    fun employeeId(value: Long): EmployeeDepartmentUpdateBuilder = put(EmployeeDepartment.employeeId, value)
    fun departmentId(value: Long): EmployeeDepartmentUpdateBuilder = put(EmployeeDepartment.departmentId, value)

    /**
     * Warning: this method will throw a StatementBuilderException at runtime because EmployeeDepartment does not have a primary key.
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long = 
      throw com.dbobjekts.api.exception.StatementBuilderException("Sorry, but you cannot use row-based updates for table EmployeeDepartment. At least one column must be marked as primary key.")                
            
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
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>()
}
        