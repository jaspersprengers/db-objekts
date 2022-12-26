package com.dbobjekts.mariadb.countries.nation

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object RegionAreas:Table("region_areas"), HasUpdateBuilder<RegionAreasUpdateBuilder, RegionAreasInsertBuilder> {
    val regionName = com.dbobjekts.metadata.column.VarcharColumn(this, "region_name")
    val regionArea = com.dbobjekts.metadata.column.BigDecimalColumn(this, "region_area")
    override val columns: List<AnyColumn> = listOf(regionName,regionArea)
    override fun metadata(): WriteQueryAccessors<RegionAreasUpdateBuilder, RegionAreasInsertBuilder> = WriteQueryAccessors(RegionAreasUpdateBuilder(), RegionAreasInsertBuilder())
}

class RegionAreasUpdateBuilder() : UpdateBuilderBase(RegionAreas) {
    fun regionName(value: String): RegionAreasUpdateBuilder = put(RegionAreas.regionName, value)
    fun regionArea(value: java.math.BigDecimal): RegionAreasUpdateBuilder = put(RegionAreas.regionArea, value)
}

class RegionAreasInsertBuilder():InsertBuilderBase(){
       fun regionName(value: String): RegionAreasInsertBuilder = put(RegionAreas.regionName, value)
    fun regionArea(value: java.math.BigDecimal): RegionAreasInsertBuilder = put(RegionAreas.regionArea, value)

    fun mandatoryColumns(regionName: String, regionArea: java.math.BigDecimal) : RegionAreasInsertBuilder {
      mandatory(RegionAreas.regionName, regionName)
      mandatory(RegionAreas.regionArea, regionArea)
      return this
    }

}

