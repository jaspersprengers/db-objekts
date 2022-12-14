package com.dbobjekts.integration.h2.core

import com.dbobjekts.AnyColumn
import com.dbobjekts.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object EmployeeAddress:Table("EMPLOYEE_ADDRESS"), HasUpdateBuilder<EmployeeAddressUpdateBuilder, EmployeeAddressInsertBuilder> {
    val employeeId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "EMPLOYEE_ID", Employee.id)
    val addressId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "ADDRESS_ID", Address.id)
    val kind = com.dbobjekts.integration.h2.custom.AddressTypeColumn(this, "KIND")
    val createdDt = com.dbobjekts.metadata.column.TimeStampColumn(this, "CREATED_DT")
    val modifiedDt = com.dbobjekts.metadata.column.NullableTimeStampColumn(this, "MODIFIED_DT")
    override val columns: List<AnyColumn> = listOf(employeeId,addressId,kind,createdDt,modifiedDt)
    override fun updater(connection: ConnectionAdapter): EmployeeAddressUpdateBuilder = EmployeeAddressUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapter): EmployeeAddressInsertBuilder = EmployeeAddressInsertBuilder(connection)
}

class EmployeeAddressUpdateBuilder(connection: ConnectionAdapter) : UpdateBuilderBase(EmployeeAddress, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun employeeId(value: Long): EmployeeAddressUpdateBuilder = ct.put(EmployeeAddress.employeeId, value)
    fun addressId(value: Long): EmployeeAddressUpdateBuilder = ct.put(EmployeeAddress.addressId, value)
    fun kind(value: com.dbobjekts.integration.h2.custom.AddressType?): EmployeeAddressUpdateBuilder = ct.put(EmployeeAddress.kind, value)
    fun createdDt(value: java.time.Instant): EmployeeAddressUpdateBuilder = ct.put(EmployeeAddress.createdDt, value)
    fun modifiedDt(value: java.time.Instant?): EmployeeAddressUpdateBuilder = ct.put(EmployeeAddress.modifiedDt, value)
}

class EmployeeAddressInsertBuilder(connection: ConnectionAdapter):InsertBuilderBase(EmployeeAddress, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun employeeId(value: Long): EmployeeAddressInsertBuilder = ct.put(EmployeeAddress.employeeId, value)
    fun addressId(value: Long): EmployeeAddressInsertBuilder = ct.put(EmployeeAddress.addressId, value)
    fun kind(value: com.dbobjekts.integration.h2.custom.AddressType?): EmployeeAddressInsertBuilder = ct.put(EmployeeAddress.kind, value)
    fun createdDt(value: java.time.Instant): EmployeeAddressInsertBuilder = ct.put(EmployeeAddress.createdDt, value)
    fun modifiedDt(value: java.time.Instant?): EmployeeAddressInsertBuilder = ct.put(EmployeeAddress.modifiedDt, value)

    fun mandatoryColumns(employeeId: Long, addressId: Long, createdDt: java.time.Instant) : EmployeeAddressInsertBuilder {
      ct.put(EmployeeAddress.employeeId, employeeId)
      ct.put(EmployeeAddress.addressId, addressId)
      ct.put(EmployeeAddress.createdDt, createdDt)
      return this
    }

}

