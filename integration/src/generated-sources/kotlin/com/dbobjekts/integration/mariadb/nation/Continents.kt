package com.dbobjekts.integration.mariadb.nation

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Continents:Table("continents"), HasUpdateBuilder<ContinentsUpdateBuilder, ContinentsInsertBuilder> {
    val continentId = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "continent_id")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "name")
    override val columns: List<AnyColumn> = listOf(continentId,name)
    override fun metadata(): WriteQueryAccessors<ContinentsUpdateBuilder, ContinentsInsertBuilder> = WriteQueryAccessors(ContinentsUpdateBuilder(), ContinentsInsertBuilder())
}

class ContinentsUpdateBuilder() : UpdateBuilderBase(Continents) {
    fun name(value: String): ContinentsUpdateBuilder = put(Continents.name, value)
}

class ContinentsInsertBuilder():InsertBuilderBase(){
       fun name(value: String): ContinentsInsertBuilder = put(Continents.name, value)

    fun mandatoryColumns(name: String) : ContinentsInsertBuilder {
      mandatory(Continents.name, name)
      return this
    }

}

