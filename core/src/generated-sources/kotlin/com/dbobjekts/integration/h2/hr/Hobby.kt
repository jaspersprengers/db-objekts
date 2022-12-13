package com.dbobjekts.integration.h2.hr

import com.dbobjekts.AnyColumn
import com.dbobjekts.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase

object Hobby:Table("HOBBY"), HasUpdateBuilder<HobbyUpdateBuilder, HobbyInsertBuilder> {
    val id = com.dbobjekts.metadata.column.VarcharColumn(this, "ID")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "NAME")
    override val columns: List<AnyColumn> = listOf(id,name)
    override fun updater(connection: ConnectionAdapter): HobbyUpdateBuilder = HobbyUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapter): HobbyInsertBuilder = HobbyInsertBuilder(connection)
}

class HobbyUpdateBuilder(connection: ConnectionAdapter) : UpdateBuilderBase(Hobby, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun id(value: String): HobbyUpdateBuilder = ct.put(Hobby.id, value)
    fun name(value: String): HobbyUpdateBuilder = ct.put(Hobby.name, value)
}

class HobbyInsertBuilder(connection: ConnectionAdapter): InsertBuilderBase(Hobby, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun id(value: String): HobbyInsertBuilder = ct.put(Hobby.id, value)
    fun name(value: String): HobbyInsertBuilder = ct.put(Hobby.name, value)

    fun mandatoryColumns(id: String, name: String) : HobbyInsertBuilder {
      ct.put(Hobby.id, id)
      ct.put(Hobby.name, name)
      return this
    }

}

