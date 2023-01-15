package com.dbobjekts.mariadb.testdb.nation

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyTable
import com.dbobjekts.api.TableRowData
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.BigDecimalColumn
import com.dbobjekts.metadata.column.VarcharColumn
import com.dbobjekts.metadata.joins.JoinBase
import com.dbobjekts.metadata.joins.JoinType
import com.dbobjekts.metadata.joins.TableJoinChain
import com.dbobjekts.statement.WriteQueryAccessors
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase

/**           
 * Auto-generated metadata object for db table nation.region_areas.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: region_name
 *
 * Foreign keys to: 
 * References by: 
 */
object RegionAreas:Table<RegionAreasRow>("region_areas"), HasUpdateBuilder<RegionAreasUpdateBuilder, RegionAreasInsertBuilder> {
    /**
     * Represents db column nation.region_areas.region_name
     */
    val regionName = VarcharColumn(this, "region_name")
    /**
     * Represents db column nation.region_areas.region_area
     */
    val regionArea = BigDecimalColumn(this, "region_area")
    override val columns: List<AnyColumn> = listOf(regionName,regionArea)
    override fun toValue(values: List<Any?>) = RegionAreasRow(values[0] as String,values[1] as java.math.BigDecimal)
    override fun metadata(): WriteQueryAccessors<RegionAreasUpdateBuilder, RegionAreasInsertBuilder> = WriteQueryAccessors(RegionAreasUpdateBuilder(), RegionAreasInsertBuilder())

}

class RegionAreasJoinChain(table: AnyTable, joins: List<JoinBase> = listOf()) : TableJoinChain(table, joins) {
    
}


class RegionAreasUpdateBuilder() : UpdateBuilderBase(RegionAreas) {
    fun regionName(value: String): RegionAreasUpdateBuilder = put(RegionAreas.regionName, value)
    fun regionArea(value: java.math.BigDecimal): RegionAreasUpdateBuilder = put(RegionAreas.regionArea, value)
    
    /**
     * FOR INTERNAL USE ONLY
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as RegionAreasRow
      add(RegionAreas.regionName, rowData.regionName)
      add(RegionAreas.regionArea, rowData.regionArea)
      return where(RegionAreas.regionName.eq(rowData.regionName))
    }    
        
}

class RegionAreasInsertBuilder():InsertBuilderBase(){
    fun regionName(value: String): RegionAreasInsertBuilder = put(RegionAreas.regionName, value)
    fun regionArea(value: java.math.BigDecimal): RegionAreasInsertBuilder = put(RegionAreas.regionArea, value)

    fun mandatoryColumns(regionName: String, regionArea: java.math.BigDecimal) : RegionAreasInsertBuilder {
      mandatory(RegionAreas.regionName, regionName)
      mandatory(RegionAreas.regionArea, regionArea)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as RegionAreasRow
      add(RegionAreas.regionName, rowData.regionName)
      add(RegionAreas.regionArea, rowData.regionArea)
      return execute()
    }    
        
}


data class RegionAreasRow(
  val regionName: String,
  val regionArea: java.math.BigDecimal    
) : TableRowData<RegionAreasUpdateBuilder, RegionAreasInsertBuilder>(RegionAreas.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>(Pair(RegionAreas.regionName, regionName))
}
        
