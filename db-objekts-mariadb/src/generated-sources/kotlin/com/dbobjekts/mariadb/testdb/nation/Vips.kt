package com.dbobjekts.mariadb.testdb.nation

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.TableRowData
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Vips:Table<VipsRow>("vips"), HasUpdateBuilder<VipsUpdateBuilder, VipsInsertBuilder> {
    val vipId = com.dbobjekts.metadata.column.LongColumn(this, "vip_id")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "name")
    override val columns: List<AnyColumn> = listOf(vipId,name)
    override fun toValue(values: List<Any?>) = VipsRow(values[0] as Long,values[1] as String)
    override fun metadata(): WriteQueryAccessors<VipsUpdateBuilder, VipsInsertBuilder> = WriteQueryAccessors(VipsUpdateBuilder(), VipsInsertBuilder())
}

class VipsUpdateBuilder() : UpdateBuilderBase(Vips) {
    fun vipId(value: Long): VipsUpdateBuilder = put(Vips.vipId, value)
    fun name(value: String): VipsUpdateBuilder = put(Vips.name, value)

    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as VipsRow
      add(Vips.vipId, rowData.vipId)
      add(Vips.name, rowData.name)
      return where (Vips.vipId.eq(rowData.vipId))
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
) : TableRowData<VipsUpdateBuilder, VipsInsertBuilder>(Vips.metadata())
        
