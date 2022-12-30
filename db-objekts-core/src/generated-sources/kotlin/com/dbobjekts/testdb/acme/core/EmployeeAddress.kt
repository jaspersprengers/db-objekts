package com.dbobjekts.testdb.acme.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object EmployeeAddress:Table<EmployeeAddressRow>("EMPLOYEE_ADDRESS"), HasUpdateBuilder<EmployeeAddressUpdateBuilder, EmployeeAddressInsertBuilder> {
    val employeeId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "EMPLOYEE_ID", Employee.id)
    val addressId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "ADDRESS_ID", Address.id)
    val kind = com.dbobjekts.testdb.AddressTypeAsStringColumn(this, "KIND")
    override val columns: List<AnyColumn> = listOf(employeeId,addressId,kind)
    override fun toValue(values: List<Any?>) = EmployeeAddressRow(values[0] as Long,values[1] as Long,values[2] as com.dbobjekts.testdb.AddressType)
    override fun metadata(): WriteQueryAccessors<EmployeeAddressUpdateBuilder, EmployeeAddressInsertBuilder> = WriteQueryAccessors(EmployeeAddressUpdateBuilder(), EmployeeAddressInsertBuilder())
}

class EmployeeAddressUpdateBuilder() : UpdateBuilderBase(EmployeeAddress) {
    fun employeeId(value: Long): EmployeeAddressUpdateBuilder = put(EmployeeAddress.employeeId, value)
    fun addressId(value: Long): EmployeeAddressUpdateBuilder = put(EmployeeAddress.addressId, value)
    fun kind(value: com.dbobjekts.testdb.AddressType): EmployeeAddressUpdateBuilder = put(EmployeeAddress.kind, value)
}

class EmployeeAddressInsertBuilder():InsertBuilderBase(){
       fun employeeId(value: Long): EmployeeAddressInsertBuilder = put(EmployeeAddress.employeeId, value)
    fun addressId(value: Long): EmployeeAddressInsertBuilder = put(EmployeeAddress.addressId, value)
    fun kind(value: com.dbobjekts.testdb.AddressType): EmployeeAddressInsertBuilder = put(EmployeeAddress.kind, value)

    fun mandatoryColumns(employeeId: Long, addressId: Long, kind: com.dbobjekts.testdb.AddressType) : EmployeeAddressInsertBuilder {
      mandatory(EmployeeAddress.employeeId, employeeId)
      mandatory(EmployeeAddress.addressId, addressId)
      mandatory(EmployeeAddress.kind, kind)
      return this
    }

}

data class EmployeeAddressRow(
    val employeeId: Long,
    val addressId: Long,
    val kind: com.dbobjekts.testdb.AddressType)
