package com.dbobjekts.integration.h2.core


import com.dbobjekts.AnyColumn
import com.dbobjekts.AnyColumnAndValue
import com.dbobjekts.integration.h2.custom.AddressType
import com.dbobjekts.integration.h2.custom.AddressTypeColumn
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase

object EmployeeAddress:Table("EMPLOYEE_ADDRESS"), HasUpdateBuilder<EmployeeAddressUpdateBuilder, EmployeeAddressInsertBuilder> {
    val employeeId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "EMPLOYEE_ID", Employee.id)
    val addressId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "ADDRESS_ID", Address.id)
    val kind = AddressTypeColumn(this, "KIND")
    override val columns: List<AnyColumn> = listOf(employeeId,addressId,kind)
    override fun updater(connection: ConnectionAdapter): EmployeeAddressUpdateBuilder = EmployeeAddressUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapter): EmployeeAddressInsertBuilder = EmployeeAddressInsertBuilder(connection)
}

class EmployeeAddressUpdateBuilder(connection: ConnectionAdapter) : UpdateBuilderBase(EmployeeAddress, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun employeeId(value: Long): EmployeeAddressUpdateBuilder = ct.put(EmployeeAddress.employeeId, value)
    fun addressId(value: Long): EmployeeAddressUpdateBuilder = ct.put(EmployeeAddress.addressId, value)
    fun kind(value: AddressType?): EmployeeAddressUpdateBuilder = ct.put(EmployeeAddress.kind, value)
}

class EmployeeAddressInsertBuilder(connection: ConnectionAdapter): InsertBuilderBase(EmployeeAddress, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun employeeId(value: Long): EmployeeAddressInsertBuilder = ct.put(EmployeeAddress.employeeId, value)
    fun addressId(value: Long): EmployeeAddressInsertBuilder = ct.put(EmployeeAddress.addressId, value)
    fun kind(value: AddressType?): EmployeeAddressInsertBuilder = ct.put(EmployeeAddress.kind, value)

    fun mandatoryColumns(employeeId: Long, addressId: Long) : EmployeeAddressInsertBuilder {
      ct.put(EmployeeAddress.employeeId, employeeId)
      ct.put(EmployeeAddress.addressId, addressId)
      return this
    }

}

