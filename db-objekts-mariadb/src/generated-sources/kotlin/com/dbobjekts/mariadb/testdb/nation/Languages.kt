package com.dbobjekts.mariadb.testdb.nation

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.TableRowData
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Languages:Table<LanguagesRow>("languages"), HasUpdateBuilder<LanguagesUpdateBuilder, LanguagesInsertBuilder> {
    val languageId = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "language_id")
    val language = com.dbobjekts.metadata.column.VarcharColumn(this, "language")
    override val columns: List<AnyColumn> = listOf(languageId,language)
    override fun toValue(values: List<Any?>) = LanguagesRow(values[0] as Long,values[1] as String)
    override fun metadata(): WriteQueryAccessors<LanguagesUpdateBuilder, LanguagesInsertBuilder> = WriteQueryAccessors(LanguagesUpdateBuilder(), LanguagesInsertBuilder())
}

class LanguagesUpdateBuilder() : UpdateBuilderBase(Languages) {
    fun language(value: String): LanguagesUpdateBuilder = put(Languages.language, value)

    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as LanguagesRow
      add(Languages.languageId, rowData.languageId)
      add(Languages.language, rowData.language)
      return where (Languages.languageId.eq(rowData.languageId))
    }    
        
}

class LanguagesInsertBuilder():InsertBuilderBase(){
    fun language(value: String): LanguagesInsertBuilder = put(Languages.language, value)

    fun mandatoryColumns(language: String) : LanguagesInsertBuilder {
      mandatory(Languages.language, language)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as LanguagesRow
      add(Languages.language, rowData.language)
      return execute()
    }    
        
}


data class LanguagesRow(
val languageId: Long = 0,
  val language: String    
) : TableRowData<LanguagesUpdateBuilder, LanguagesInsertBuilder>(Languages.metadata())
        
