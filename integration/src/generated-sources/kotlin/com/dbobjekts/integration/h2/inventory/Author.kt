package com.dbobjekts.integration.h2.inventory

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Author:Table("AUTHOR"), HasUpdateBuilder<AuthorUpdateBuilder, AuthorInsertBuilder> {
    val id = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "ID")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "NAME")
    val surname = com.dbobjekts.metadata.column.VarcharColumn(this, "SURNAME")
    val dateOfBirth = com.dbobjekts.metadata.column.DateColumn(this, "DATE_OF_BIRTH")
    val dateOfDeath = com.dbobjekts.metadata.column.NullableDateColumn(this, "DATE_OF_DEATH")
    override val columns: List<AnyColumn> = listOf(id,name,surname,dateOfBirth,dateOfDeath)
    override fun metadata(): WriteQueryAccessors<AuthorUpdateBuilder, AuthorInsertBuilder> = WriteQueryAccessors(AuthorUpdateBuilder(), AuthorInsertBuilder())
}

class AuthorUpdateBuilder() : UpdateBuilderBase(Author) {
    fun name(value: String): AuthorUpdateBuilder = put(Author.name, value)
    fun surname(value: String): AuthorUpdateBuilder = put(Author.surname, value)
    fun dateOfBirth(value: java.time.LocalDate): AuthorUpdateBuilder = put(Author.dateOfBirth, value)
    fun dateOfDeath(value: java.time.LocalDate?): AuthorUpdateBuilder = put(Author.dateOfDeath, value)
}

class AuthorInsertBuilder():InsertBuilderBase(){
       fun name(value: String): AuthorInsertBuilder = put(Author.name, value)
    fun surname(value: String): AuthorInsertBuilder = put(Author.surname, value)
    fun dateOfBirth(value: java.time.LocalDate): AuthorInsertBuilder = put(Author.dateOfBirth, value)
    fun dateOfDeath(value: java.time.LocalDate?): AuthorInsertBuilder = put(Author.dateOfDeath, value)

    fun mandatoryColumns(name: String, surname: String, dateOfBirth: java.time.LocalDate) : AuthorInsertBuilder {
      mandatory(Author.name, name)
      mandatory(Author.surname, surname)
      mandatory(Author.dateOfBirth, dateOfBirth)
      return this
    }

}

