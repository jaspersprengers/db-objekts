package com.dbobjekts.mariadb.countries.nation

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Vips:Table("vips"), HasUpdateBuilder<VipsUpdateBuilder, VipsInsertBuilder> {
    val vipId = com.dbobjekts.metadata.column.LongColumn(this, "vip_id")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "name")
    override val columns: List<AnyColumn> = listOf(vipId,name)
    override fun metadata(): WriteQueryAccessors<VipsUpdateBuilder, VipsInsertBuilder> = WriteQueryAccessors(VipsUpdateBuilder(), VipsInsertBuilder())
}

class VipsUpdateBuilder() : UpdateBuilderBase(Vips) {
    fun vipId(value: Long): VipsUpdateBuilder = put(Vips.vipId, value)
    fun name(value: String): VipsUpdateBuilder = put(Vips.name, value)
}

class VipsInsertBuilder():InsertBuilderBase(){
       fun vipId(value: Long): VipsInsertBuilder = put(Vips.vipId, value)
    fun name(value: String): VipsInsertBuilder = put(Vips.name, value)

    fun mandatoryColumns(vipId: Long, name: String) : VipsInsertBuilder {
      mandatory(Vips.vipId, vipId)
      mandatory(Vips.name, name)
      return this
    }

}

