package com.dbobjekts.integration.mariadb.nation

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Languages:Table("languages"), HasUpdateBuilder<LanguagesUpdateBuilder, LanguagesInsertBuilder> {
    val languageId = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "language_id")
    val language = com.dbobjekts.metadata.column.VarcharColumn(this, "language")
    override val columns: List<AnyColumn> = listOf(languageId,language)
    override fun metadata(): WriteQueryAccessors<LanguagesUpdateBuilder, LanguagesInsertBuilder> = WriteQueryAccessors(LanguagesUpdateBuilder(), LanguagesInsertBuilder())
}

class LanguagesUpdateBuilder() : UpdateBuilderBase(Languages) {
    fun language(value: String): LanguagesUpdateBuilder = put(Languages.language, value)
}

class LanguagesInsertBuilder():InsertBuilderBase(){
       fun language(value: String): LanguagesInsertBuilder = put(Languages.language, value)

    fun mandatoryColumns(language: String) : LanguagesInsertBuilder {
      mandatory(Languages.language, language)
      return this
    }

}

