package com.dbobjekts.integration.mariadb.catalog.core

import com.dbobjekts.AnyColumn
import com.dbobjekts.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Address:Table("ADDRESS"), HasUpdateBuilder<AddressUpdateBuilder, AddressInsertBuilder> {
    val id = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "id")
    val street = com.dbobjekts.metadata.column.VarcharColumn(this, "street")
    val countryId = com.dbobjekts.metadata.column.ForeignKeyVarcharColumn(this, "country_id", Country.id)
    val kind = com.dbobjekts.metadata.column.VarcharColumn(this, "kind")
    val employeeId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "employee_id", Employee.id)
    override val columns: List<AnyColumn> = listOf(id,street,countryId,kind,employeeId)
    override fun updater(connection: ConnectionAdapter): AddressUpdateBuilder = AddressUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapter): AddressInsertBuilder = AddressInsertBuilder(connection)
}

class AddressUpdateBuilder(connection: ConnectionAdapter) : UpdateBuilderBase(Address, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun street(value: String): AddressUpdateBuilder = ct.put(Address.street, value)
    fun countryId(value: String): AddressUpdateBuilder = ct.put(Address.countryId, value)
    fun kind(value: String): AddressUpdateBuilder = ct.put(Address.kind, value)
    fun employeeId(value: Long): AddressUpdateBuilder = ct.put(Address.employeeId, value)
}

class AddressInsertBuilder(connection: ConnectionAdapter):InsertBuilderBase(Address, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun street(value: String): AddressInsertBuilder = ct.put(Address.street, value)
    fun countryId(value: String): AddressInsertBuilder = ct.put(Address.countryId, value)
    fun kind(value: String): AddressInsertBuilder = ct.put(Address.kind, value)
    fun employeeId(value: Long): AddressInsertBuilder = ct.put(Address.employeeId, value)

    fun mandatoryColumns(street: String, countryId: String, kind: String, employeeId: Long) : AddressInsertBuilder {
      ct.put(Address.street, street)
      ct.put(Address.countryId, countryId)
      ct.put(Address.kind, kind)
      ct.put(Address.employeeId, employeeId)
      return this
    }

}

