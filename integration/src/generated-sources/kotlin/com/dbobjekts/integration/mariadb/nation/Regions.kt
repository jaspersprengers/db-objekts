package com.dbobjekts.integration.mariadb.nation

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Regions:Table("regions"), HasUpdateBuilder<RegionsUpdateBuilder, RegionsInsertBuilder> {
    val regionId = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "region_id")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "name")
    val continentId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "continent_id", Continents.continentId)
    override val columns: List<AnyColumn> = listOf(regionId,name,continentId)
    override fun metadata(): WriteQueryAccessors<RegionsUpdateBuilder, RegionsInsertBuilder> = WriteQueryAccessors(RegionsUpdateBuilder(), RegionsInsertBuilder())
}

class RegionsUpdateBuilder() : UpdateBuilderBase(Regions) {
    fun name(value: String): RegionsUpdateBuilder = put(Regions.name, value)
    fun continentId(value: Long): RegionsUpdateBuilder = put(Regions.continentId, value)
}

class RegionsInsertBuilder():InsertBuilderBase(){
       fun name(value: String): RegionsInsertBuilder = put(Regions.name, value)
    fun continentId(value: Long): RegionsInsertBuilder = put(Regions.continentId, value)

    fun mandatoryColumns(name: String, continentId: Long) : RegionsInsertBuilder {
      mandatory(Regions.name, name)
      mandatory(Regions.continentId, continentId)
      return this
    }

}

