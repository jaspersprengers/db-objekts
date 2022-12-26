package com.dbobjekts.mariadb.countries.nation

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object CountryLanguages:Table("country_languages"), HasUpdateBuilder<CountryLanguagesUpdateBuilder, CountryLanguagesInsertBuilder> {
    val countryId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "country_id", Countries.countryId)
    val languageId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "language_id", Languages.languageId)
    val official = com.dbobjekts.metadata.column.ByteColumn(this, "official")
    override val columns: List<AnyColumn> = listOf(countryId,languageId,official)
    override fun metadata(): WriteQueryAccessors<CountryLanguagesUpdateBuilder, CountryLanguagesInsertBuilder> = WriteQueryAccessors(CountryLanguagesUpdateBuilder(), CountryLanguagesInsertBuilder())
}

class CountryLanguagesUpdateBuilder() : UpdateBuilderBase(CountryLanguages) {
    fun countryId(value: Long): CountryLanguagesUpdateBuilder = put(CountryLanguages.countryId, value)
    fun languageId(value: Long): CountryLanguagesUpdateBuilder = put(CountryLanguages.languageId, value)
    fun official(value: Byte): CountryLanguagesUpdateBuilder = put(CountryLanguages.official, value)
}

class CountryLanguagesInsertBuilder():InsertBuilderBase(){
       fun countryId(value: Long): CountryLanguagesInsertBuilder = put(CountryLanguages.countryId, value)
    fun languageId(value: Long): CountryLanguagesInsertBuilder = put(CountryLanguages.languageId, value)
    fun official(value: Byte): CountryLanguagesInsertBuilder = put(CountryLanguages.official, value)

    fun mandatoryColumns(countryId: Long, languageId: Long, official: Byte) : CountryLanguagesInsertBuilder {
      mandatory(CountryLanguages.countryId, countryId)
      mandatory(CountryLanguages.languageId, languageId)
      mandatory(CountryLanguages.official, official)
      return this
    }

}

