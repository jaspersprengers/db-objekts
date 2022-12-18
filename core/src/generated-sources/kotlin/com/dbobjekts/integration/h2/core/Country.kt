package com.dbobjekts.integration.h2.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Country:Table("COUNTRY"), HasUpdateBuilder<CountryUpdateBuilder, CountryInsertBuilder> {
    val id = com.dbobjekts.metadata.column.VarcharColumn(this, "ID")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "NAME")
    override val columns: List<AnyColumn> = listOf(id,name)
    override fun updater(connection: ConnectionAdapter): CountryUpdateBuilder = CountryUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapter): CountryInsertBuilder = CountryInsertBuilder(connection)
}

class CountryUpdateBuilder(connection: ConnectionAdapter) : UpdateBuilderBase(Country, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun id(value: String): CountryUpdateBuilder = ct.put(Country.id, value)
    fun name(value: String): CountryUpdateBuilder = ct.put(Country.name, value)
}

class CountryInsertBuilder(connection: ConnectionAdapter):InsertBuilderBase(Country, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun id(value: String): CountryInsertBuilder = ct.put(Country.id, value)
    fun name(value: String): CountryInsertBuilder = ct.put(Country.name, value)

    fun mandatoryColumns(id: String, name: String) : CountryInsertBuilder {
      ct.put(Country.id, id)
      ct.put(Country.name, name)
      return this
    }

}

