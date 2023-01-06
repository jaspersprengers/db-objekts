package com.dbobjekts.mariadb.testdb.nation

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.TableRowData
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Continents:Table<ContinentsRow>("continents"), HasUpdateBuilder<ContinentsUpdateBuilder, ContinentsInsertBuilder> {
    val continentId = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "continent_id")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "name")
    override val columns: List<AnyColumn> = listOf(continentId,name)
    override fun toValue(values: List<Any?>) = ContinentsRow(values[0] as Long,values[1] as String)
    override fun metadata(): WriteQueryAccessors<ContinentsUpdateBuilder, ContinentsInsertBuilder> = WriteQueryAccessors(ContinentsUpdateBuilder(), ContinentsInsertBuilder())
}

class ContinentsUpdateBuilder() : UpdateBuilderBase(Continents) {
    fun name(value: String): ContinentsUpdateBuilder = put(Continents.name, value)

    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as ContinentsRow
      add(Continents.continentId, rowData.continentId)
      add(Continents.name, rowData.name)
      return where (Continents.continentId.eq(rowData.continentId))
    }    
        
}

class ContinentsInsertBuilder():InsertBuilderBase(){
    fun name(value: String): ContinentsInsertBuilder = put(Continents.name, value)

    fun mandatoryColumns(name: String) : ContinentsInsertBuilder {
      mandatory(Continents.name, name)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as ContinentsRow
      add(Continents.name, rowData.name)
      return execute()
    }    
        
}


data class ContinentsRow(
val continentId: Long = 0,
  val name: String    
) : TableRowData<ContinentsUpdateBuilder, ContinentsInsertBuilder>(Continents.metadata())
        
