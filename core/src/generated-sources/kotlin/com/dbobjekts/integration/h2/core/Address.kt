package com.dbobjekts.integration.h2.core

import com.dbobjekts.AnyColumn
import com.dbobjekts.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Address:Table("ADDRESS"), HasUpdateBuilder<AddressUpdateBuilder, AddressInsertBuilder> {
    val id = com.dbobjekts.metadata.column.SequenceKeyLongColumn(this, "ID", "core.ADDRESS_SEQ")
    val street = com.dbobjekts.metadata.column.VarcharColumn(this, "STREET")
    val countryId = com.dbobjekts.metadata.column.ForeignKeyVarcharColumn(this, "COUNTRY_ID", Country.id)
    val createdDt = com.dbobjekts.metadata.column.TimeStampColumn(this, "CREATED_DT")
    val modifiedDt = com.dbobjekts.metadata.column.NullableTimeStampColumn(this, "MODIFIED_DT")
    override val columns: List<AnyColumn> = listOf(id,street,countryId,createdDt,modifiedDt)
    override fun updater(connection: ConnectionAdapter): AddressUpdateBuilder = AddressUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapter): AddressInsertBuilder = AddressInsertBuilder(connection)
}

class AddressUpdateBuilder(connection: ConnectionAdapter) : UpdateBuilderBase(Address, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun street(value: String): AddressUpdateBuilder = ct.put(Address.street, value)
    fun countryId(value: String): AddressUpdateBuilder = ct.put(Address.countryId, value)
    fun createdDt(value: java.time.Instant): AddressUpdateBuilder = ct.put(Address.createdDt, value)
    fun modifiedDt(value: java.time.Instant?): AddressUpdateBuilder = ct.put(Address.modifiedDt, value)
}

class AddressInsertBuilder(connection: ConnectionAdapter):InsertBuilderBase(Address, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun street(value: String): AddressInsertBuilder = ct.put(Address.street, value)
    fun countryId(value: String): AddressInsertBuilder = ct.put(Address.countryId, value)
    fun createdDt(value: java.time.Instant): AddressInsertBuilder = ct.put(Address.createdDt, value)
    fun modifiedDt(value: java.time.Instant?): AddressInsertBuilder = ct.put(Address.modifiedDt, value)

    fun mandatoryColumns(street: String, countryId: String, createdDt: java.time.Instant) : AddressInsertBuilder {
      ct.put(Address.street, street)
      ct.put(Address.countryId, countryId)
      ct.put(Address.createdDt, createdDt)
      return this
    }

}

