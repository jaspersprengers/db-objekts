package com.dbobjekts.integration.h2.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyColumnAndValue
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.WriteQueryAccessors
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object EmployeeAddress:Table("EMPLOYEE_ADDRESS"), HasUpdateBuilder<EmployeeAddressUpdateBuilder, EmployeeAddressInsertBuilder> {
    val employeeId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "EMPLOYEE_ID", Employee.id)
    val addressId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "ADDRESS_ID", Address.id)
    val kind = com.dbobjekts.integration.h2.custom.AddressTypeAsStringColumn(this, "KIND")
    override val columns: List<AnyColumn> = listOf(employeeId,addressId,kind)
    override val metadata: WriteQueryAccessors<EmployeeAddressUpdateBuilder, EmployeeAddressInsertBuilder> = WriteQueryAccessors(EmployeeAddressUpdateBuilder(), EmployeeAddressInsertBuilder())
}

class EmployeeAddressUpdateBuilder() : UpdateBuilderBase(EmployeeAddress) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override fun data(): Set<AnyColumnAndValue> = ct.data
    override fun clear(){ct.data.clear()}

    fun employeeId(value: Long): EmployeeAddressUpdateBuilder = ct.put(EmployeeAddress.employeeId, value)
    fun addressId(value: Long): EmployeeAddressUpdateBuilder = ct.put(EmployeeAddress.addressId, value)
    fun kind(value: com.dbobjekts.integration.h2.custom.AddressType): EmployeeAddressUpdateBuilder = ct.put(EmployeeAddress.kind, value)
}

class EmployeeAddressInsertBuilder():InsertBuilderBase(){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override fun data(): Set<AnyColumnAndValue> = ct.data
    override fun clear(){ct.data.clear()}

    fun employeeId(value: Long): EmployeeAddressInsertBuilder = ct.put(EmployeeAddress.employeeId, value)
    fun addressId(value: Long): EmployeeAddressInsertBuilder = ct.put(EmployeeAddress.addressId, value)
    fun kind(value: com.dbobjekts.integration.h2.custom.AddressType): EmployeeAddressInsertBuilder = ct.put(EmployeeAddress.kind, value)

    fun mandatoryColumns(employeeId: Long, addressId: Long, kind: com.dbobjekts.integration.h2.custom.AddressType) : EmployeeAddressInsertBuilder {
      ct.put(EmployeeAddress.employeeId, employeeId)
      ct.put(EmployeeAddress.addressId, addressId)
      ct.put(EmployeeAddress.kind, kind)
      return this
    }

}

