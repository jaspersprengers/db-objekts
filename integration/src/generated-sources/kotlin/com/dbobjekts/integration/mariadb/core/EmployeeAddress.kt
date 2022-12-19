package com.dbobjekts.integration.mariadb.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object EmployeeAddress:Table("EMPLOYEE_ADDRESS"), HasUpdateBuilder<EmployeeAddressUpdateBuilder, EmployeeAddressInsertBuilder> {
    val employeeId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "employee_id", Employee.id)
    val addressId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "address_id", Address.id)
    val kind = com.dbobjekts.metadata.column.VarcharColumn(this, "kind")
    override val columns: List<AnyColumn> = listOf(employeeId,addressId,kind)
    override fun updater(connection: ConnectionAdapter): EmployeeAddressUpdateBuilder = EmployeeAddressUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapter): EmployeeAddressInsertBuilder = EmployeeAddressInsertBuilder(connection)
}

class EmployeeAddressUpdateBuilder(connection: ConnectionAdapter) : UpdateBuilderBase(EmployeeAddress, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun employeeId(value: Long): EmployeeAddressUpdateBuilder = ct.put(EmployeeAddress.employeeId, value)
    fun addressId(value: Long): EmployeeAddressUpdateBuilder = ct.put(EmployeeAddress.addressId, value)
    fun kind(value: String): EmployeeAddressUpdateBuilder = ct.put(EmployeeAddress.kind, value)
}

class EmployeeAddressInsertBuilder(connection: ConnectionAdapter):InsertBuilderBase(EmployeeAddress, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun employeeId(value: Long): EmployeeAddressInsertBuilder = ct.put(EmployeeAddress.employeeId, value)
    fun addressId(value: Long): EmployeeAddressInsertBuilder = ct.put(EmployeeAddress.addressId, value)
    fun kind(value: String): EmployeeAddressInsertBuilder = ct.put(EmployeeAddress.kind, value)

    fun mandatoryColumns(employeeId: Long, addressId: Long, kind: String) : EmployeeAddressInsertBuilder {
      ct.put(EmployeeAddress.employeeId, employeeId)
      ct.put(EmployeeAddress.addressId, addressId)
      ct.put(EmployeeAddress.kind, kind)
      return this
    }

}

