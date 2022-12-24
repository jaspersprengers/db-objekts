package com.dbobjekts.sampledbs.h2.hr

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Hobby:Table("HOBBY"), HasUpdateBuilder<HobbyUpdateBuilder, HobbyInsertBuilder> {
    val id = com.dbobjekts.metadata.column.VarcharColumn(this, "ID")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "NAME")
    override val columns: List<AnyColumn> = listOf(id, name)
    override fun metadata(): WriteQueryAccessors<HobbyUpdateBuilder, HobbyInsertBuilder> = WriteQueryAccessors(HobbyUpdateBuilder(), HobbyInsertBuilder())
}

class HobbyUpdateBuilder() : UpdateBuilderBase(Hobby) {
    fun id(value: String): HobbyUpdateBuilder = put(Hobby.id, value)
    fun name(value: String): HobbyUpdateBuilder = put(Hobby.name, value)
}

class HobbyInsertBuilder():InsertBuilderBase(){
       fun id(value: String): HobbyInsertBuilder = put(Hobby.id, value)
    fun name(value: String): HobbyInsertBuilder = put(Hobby.name, value)

    fun mandatoryColumns(id: String, name: String) : HobbyInsertBuilder {
      mandatory(Hobby.id, id)
      mandatory(Hobby.name, name)
      return this
    }

}

