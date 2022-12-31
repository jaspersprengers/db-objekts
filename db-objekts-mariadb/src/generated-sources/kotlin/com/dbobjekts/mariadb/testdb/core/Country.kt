package com.dbobjekts.mariadb.testdb.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.Entity
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Country:Table<CountryRow>("COUNTRY"), HasUpdateBuilder<CountryUpdateBuilder, CountryInsertBuilder> {
    val id = com.dbobjekts.metadata.column.VarcharColumn(this, "id")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "name")
    override val columns: List<AnyColumn> = listOf(id,name)
    override fun toValue(values: List<Any?>) = CountryRow(values[0] as String,values[1] as String)
    override fun metadata(): WriteQueryAccessors<CountryUpdateBuilder, CountryInsertBuilder> = WriteQueryAccessors(CountryUpdateBuilder(), CountryInsertBuilder())
}

class CountryUpdateBuilder() : UpdateBuilderBase(Country) {
    fun id(value: String): CountryUpdateBuilder = put(Country.id, value)
    fun name(value: String): CountryUpdateBuilder = put(Country.name, value)

    override fun updateRow(entity: Entity<*, *>): Long {
      entity as CountryRow
      add(Country.id, entity.id)
      add(Country.name, entity.name)
      return where (Country.id.eq(entity.id))
    }    
        
}

class CountryInsertBuilder():InsertBuilderBase(){
    fun id(value: String): CountryInsertBuilder = put(Country.id, value)
    fun name(value: String): CountryInsertBuilder = put(Country.name, value)

    fun mandatoryColumns(id: String, name: String) : CountryInsertBuilder {
      mandatory(Country.id, id)
      mandatory(Country.name, name)
      return this
    }


    override fun insertRow(entity: Entity<*, *>): Long {
      entity as CountryRow
      add(Country.id, entity.id)
      add(Country.name, entity.name)
      return execute()
    }    
        
}


data class CountryRow(
  val id: String,
  val name: String    
) : Entity<CountryUpdateBuilder, CountryInsertBuilder>(Country.metadata())
        
