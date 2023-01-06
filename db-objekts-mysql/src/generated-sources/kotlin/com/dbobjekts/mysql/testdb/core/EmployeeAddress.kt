package com.dbobjekts.mysql.testdb.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.TableRowData
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object EmployeeAddress:Table<EmployeeAddressRow>("EMPLOYEE_ADDRESS"), HasUpdateBuilder<EmployeeAddressUpdateBuilder, EmployeeAddressInsertBuilder> {
    val employeeId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "employee_id", Employee.id)
    val addressId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "address_id", Address.id)
    val kind = com.dbobjekts.metadata.column.VarcharColumn(this, "kind")
    override val columns: List<AnyColumn> = listOf(employeeId,addressId,kind)
    override fun toValue(values: List<Any?>) = EmployeeAddressRow(values[0] as Long,values[1] as Long,values[2] as String)
    override fun metadata(): WriteQueryAccessors<EmployeeAddressUpdateBuilder, EmployeeAddressInsertBuilder> = WriteQueryAccessors(EmployeeAddressUpdateBuilder(), EmployeeAddressInsertBuilder())
}

class EmployeeAddressUpdateBuilder() : UpdateBuilderBase(EmployeeAddress) {
    fun employeeId(value: Long): EmployeeAddressUpdateBuilder = put(EmployeeAddress.employeeId, value)
    fun addressId(value: Long): EmployeeAddressUpdateBuilder = put(EmployeeAddress.addressId, value)
    fun kind(value: String): EmployeeAddressUpdateBuilder = put(EmployeeAddress.kind, value)

    override fun updateRow(rowData: TableRowData<*, *>): Long = 
      throw StatementBuilderException("Sorry, but you cannot use row-based updates for table EmployeeAddress. There must be exactly one column marked as primary key.")                
            
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
) : TableRowData<EmployeeAddressUpdateBuilder, EmployeeAddressInsertBuilder>(EmployeeAddress.metadata())
        