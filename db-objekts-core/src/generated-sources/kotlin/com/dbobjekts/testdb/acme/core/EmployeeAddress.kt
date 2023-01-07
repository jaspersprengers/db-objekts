package com.dbobjekts.testdb.acme.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.TableRowData
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase


/**           
 * Metadata object for db table EMPLOYEE_ADDRESS.
 *
 * Primary key: none
 *
 * Foreign keys: [CORE.EMPLOYEE_ADDRESS.EMPLOYEE_ID to CORE.EMPLOYEE.ID, CORE.EMPLOYEE_ADDRESS.ADDRESS_ID to CORE.ADDRESS.ID] 
 */
object EmployeeAddress:Table<EmployeeAddressRow>("EMPLOYEE_ADDRESS"), HasUpdateBuilder<EmployeeAddressUpdateBuilder, EmployeeAddressInsertBuilder> {
    /**
     * Represents db column CORE.EMPLOYEE_ADDRESS.EMPLOYEE_ID
     *
     * Foreign key to CORE.EMPLOYEE.ID
     */
    val employeeId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "EMPLOYEE_ID", Employee.id)
    /**
     * Represents db column CORE.EMPLOYEE_ADDRESS.ADDRESS_ID
     *
     * Foreign key to CORE.ADDRESS.ID
     */
    val addressId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "ADDRESS_ID", Address.id)
    /**
     * Represents db column CORE.EMPLOYEE_ADDRESS.KIND
     */
    val kind = com.dbobjekts.fixture.columns.AddressTypeAsStringColumn(this, "KIND")
    override val columns: List<AnyColumn> = listOf(employeeId,addressId,kind)
    override fun toValue(values: List<Any?>) = EmployeeAddressRow(values[0] as Long,values[1] as Long,values[2] as com.dbobjekts.fixture.columns.AddressType)
    override fun metadata(): WriteQueryAccessors<EmployeeAddressUpdateBuilder, EmployeeAddressInsertBuilder> = WriteQueryAccessors(EmployeeAddressUpdateBuilder(), EmployeeAddressInsertBuilder())
}

class EmployeeAddressUpdateBuilder() : UpdateBuilderBase(EmployeeAddress) {
    fun employeeId(value: Long): EmployeeAddressUpdateBuilder = put(EmployeeAddress.employeeId, value)
    fun addressId(value: Long): EmployeeAddressUpdateBuilder = put(EmployeeAddress.addressId, value)
    fun kind(value: com.dbobjekts.fixture.columns.AddressType): EmployeeAddressUpdateBuilder = put(EmployeeAddress.kind, value)

    /**
     * Warning: this method will throw an Exception at runtime because the tables misses a single primary key. 
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long = 
      throw StatementBuilderException("Sorry, but you cannot use row-based updates for table EmployeeAddress. There must be exactly one column marked as primary key.")                
            
}

class EmployeeAddressInsertBuilder():InsertBuilderBase(){
    fun employeeId(value: Long): EmployeeAddressInsertBuilder = put(EmployeeAddress.employeeId, value)
    fun addressId(value: Long): EmployeeAddressInsertBuilder = put(EmployeeAddress.addressId, value)
    fun kind(value: com.dbobjekts.fixture.columns.AddressType): EmployeeAddressInsertBuilder = put(EmployeeAddress.kind, value)

    fun mandatoryColumns(employeeId: Long, addressId: Long, kind: com.dbobjekts.fixture.columns.AddressType) : EmployeeAddressInsertBuilder {
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
  val kind: com.dbobjekts.fixture.columns.AddressType    
) : TableRowData<EmployeeAddressUpdateBuilder, EmployeeAddressInsertBuilder>(EmployeeAddress.metadata())
        
