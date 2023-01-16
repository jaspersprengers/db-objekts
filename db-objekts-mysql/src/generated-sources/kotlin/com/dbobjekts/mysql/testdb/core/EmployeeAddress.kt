package com.dbobjekts.mysql.testdb.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.TableRowData
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.ForeignKeyLongColumn
import com.dbobjekts.metadata.column.VarcharColumn
import com.dbobjekts.statement.WriteQueryAccessors
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase

/**           
 * Auto-generated metadata object for db table core.EMPLOYEE_ADDRESS.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: none
 *
 * Foreign keys to: 
 * References by: core.EMPLOYEE,core.ADDRESS
 */
object EmployeeAddress:Table<EmployeeAddressRow>("EMPLOYEE_ADDRESS"), HasUpdateBuilder<EmployeeAddressUpdateBuilder, EmployeeAddressInsertBuilder> {
    /**
     * Represents db column core.EMPLOYEE_ADDRESS.employee_id
     *
     * Foreign key to core.EMPLOYEE.id
     */
    val employeeId = ForeignKeyLongColumn(this, "employee_id", Employee.id)
    /**
     * Represents db column core.EMPLOYEE_ADDRESS.address_id
     *
     * Foreign key to core.ADDRESS.id
     */
    val addressId = ForeignKeyLongColumn(this, "address_id", Address.id)
    /**
     * Represents db column core.EMPLOYEE_ADDRESS.kind
     */
    val kind = VarcharColumn(this, "kind")
    override val columns: List<AnyColumn> = listOf(employeeId,addressId,kind)
    override fun toValue(values: List<Any?>) = EmployeeAddressRow(values[0] as Long,values[1] as Long,values[2] as String)
    override fun metadata(): WriteQueryAccessors<EmployeeAddressUpdateBuilder, EmployeeAddressInsertBuilder> = WriteQueryAccessors(EmployeeAddressUpdateBuilder(), EmployeeAddressInsertBuilder())
}

class EmployeeAddressUpdateBuilder() : UpdateBuilderBase(EmployeeAddress) {
    fun employeeId(value: Long): EmployeeAddressUpdateBuilder = put(EmployeeAddress.employeeId, value)
    fun addressId(value: Long): EmployeeAddressUpdateBuilder = put(EmployeeAddress.addressId, value)
    fun kind(value: String): EmployeeAddressUpdateBuilder = put(EmployeeAddress.kind, value)

    /**
     * Warning: this method will throw a StatementBuilderException at runtime because EmployeeAddress does not have a primary key.
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long = 
      throw com.dbobjekts.api.exception.StatementBuilderException("Sorry, but you cannot use row-based updates for table EmployeeAddress. At least one column must be marked as primary key.")                
            
}

class EmployeeAddressInsertBuilder():InsertBuilderBase(){
    fun employeeId(value: Long): EmployeeAddressInsertBuilder = put(EmployeeAddress.employeeId, value)
    fun addressId(value: Long): EmployeeAddressInsertBuilder = put(EmployeeAddress.addressId, value)
    fun kind(value: String): EmployeeAddressInsertBuilder = put(EmployeeAddress.kind, value)

    fun mandatoryColumns(employeeId: Long, addressId: Long, kind: String) : EmployeeAddressInsertBuilder {
      mandatory(EmployeeAddress.employeeId, employeeId)
      mandatory(EmployeeAddress.addressId, addressId)
      mandatory(EmployeeAddress.kind, kind)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as EmployeeAddressRow
      add(EmployeeAddress.employeeId, rowData.employeeId)
      add(EmployeeAddress.addressId, rowData.addressId)
      add(EmployeeAddress.kind, rowData.kind)
      return execute()
    }    
        
}


data class EmployeeAddressRow(
  val employeeId: Long,
  val addressId: Long,
  val kind: String    
) : TableRowData<EmployeeAddressUpdateBuilder, EmployeeAddressInsertBuilder>(EmployeeAddress.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>()
}
        
