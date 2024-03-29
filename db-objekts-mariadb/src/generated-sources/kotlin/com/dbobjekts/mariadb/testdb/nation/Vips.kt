package com.dbobjekts.mariadb.testdb.nation

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.TableRowData
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.LongColumn
import com.dbobjekts.metadata.column.VarcharColumn
import com.dbobjekts.statement.WriteQueryAccessors
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase

/**           
 * Auto-generated metadata object for db table nation.vips.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: vip_id
 *
 * Foreign keys to: 
 * References by: 
 */
object Vips:Table<VipsRow>("vips"), HasUpdateBuilder<VipsUpdateBuilder, VipsInsertBuilder> {
    /**
     * Represents db column nation.vips.vip_id
     */
    val vipId = LongColumn(this, "vip_id")
    /**
     * Represents db column nation.vips.name
     */
    val name = VarcharColumn(this, "name")
    override val columns: List<AnyColumn> = listOf(vipId,name)
    override fun toValue(values: List<Any?>) = VipsRow(values[0] as Long,values[1] as String)
    override fun metadata(): WriteQueryAccessors<VipsUpdateBuilder, VipsInsertBuilder> = WriteQueryAccessors(VipsUpdateBuilder(), VipsInsertBuilder())
}

class VipsUpdateBuilder() : UpdateBuilderBase(Vips) {
    fun vipId(value: Long): VipsUpdateBuilder = put(Vips.vipId, value)
    fun name(value: String): VipsUpdateBuilder = put(Vips.name, value)
    
    /**
     * FOR INTERNAL USE ONLY
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as VipsRow
      add(Vips.vipId, rowData.vipId)
      add(Vips.name, rowData.name)
      return where(Vips.vipId.eq(rowData.vipId))
    }    
        
}

class VipsInsertBuilder():InsertBuilderBase(){
    fun vipId(value: Long): VipsInsertBuilder = put(Vips.vipId, value)
    fun name(value: String): VipsInsertBuilder = put(Vips.name, value)

    fun mandatoryColumns(vipId: Long, name: String) : VipsInsertBuilder {
      mandatory(Vips.vipId, vipId)
      mandatory(Vips.name, name)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as VipsRow
      add(Vips.vipId, rowData.vipId)
      add(Vips.name, rowData.name)
      return execute()
    }    
        
}


data class VipsRow(
  val vipId: Long,
  val name: String    
) : TableRowData<VipsUpdateBuilder, VipsInsertBuilder>(Vips.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>(Pair(Vips.vipId, vipId))
}
        
