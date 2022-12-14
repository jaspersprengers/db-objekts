package com.dbobjekts.integration.h2.hr

import com.dbobjekts.AnyColumn
import com.dbobjekts.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Hobby:Table("HOBBY"), HasUpdateBuilder<HobbyUpdateBuilder, HobbyInsertBuilder> {
    val id = com.dbobjekts.metadata.column.VarcharColumn(this, "ID")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "NAME")
    val createdDt = com.dbobjekts.metadata.column.TimeStampColumn(this, "CREATED_DT")
    val modifiedDt = com.dbobjekts.metadata.column.NullableTimeStampColumn(this, "MODIFIED_DT")
    override val columns: List<AnyColumn> = listOf(id,name,createdDt,modifiedDt)
    override fun updater(connection: ConnectionAdapter): HobbyUpdateBuilder = HobbyUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapter): HobbyInsertBuilder = HobbyInsertBuilder(connection)
}

class HobbyUpdateBuilder(connection: ConnectionAdapter) : UpdateBuilderBase(Hobby, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun id(value: String): HobbyUpdateBuilder = ct.put(Hobby.id, value)
    fun name(value: String): HobbyUpdateBuilder = ct.put(Hobby.name, value)
    fun createdDt(value: java.time.Instant): HobbyUpdateBuilder = ct.put(Hobby.createdDt, value)
    fun modifiedDt(value: java.time.Instant?): HobbyUpdateBuilder = ct.put(Hobby.modifiedDt, value)
}

class HobbyInsertBuilder(connection: ConnectionAdapter):InsertBuilderBase(Hobby, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun id(value: String): HobbyInsertBuilder = ct.put(Hobby.id, value)
    fun name(value: String): HobbyInsertBuilder = ct.put(Hobby.name, value)
    fun createdDt(value: java.time.Instant): HobbyInsertBuilder = ct.put(Hobby.createdDt, value)
    fun modifiedDt(value: java.time.Instant?): HobbyInsertBuilder = ct.put(Hobby.modifiedDt, value)

    fun mandatoryColumns(id: String, name: String, createdDt: java.time.Instant) : HobbyInsertBuilder {
      ct.put(Hobby.id, id)
      ct.put(Hobby.name, name)
      ct.put(Hobby.createdDt, createdDt)
      return this
    }

}

