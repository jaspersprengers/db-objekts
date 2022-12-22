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
    val bio = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "BIO")
    override val columns: List<AnyColumn> = listOf(id,name,bio)
    override fun metadata(): WriteQueryAccessors<AuthorUpdateBuilder, AuthorInsertBuilder> = WriteQueryAccessors(AuthorUpdateBuilder(), AuthorInsertBuilder())
}

class AuthorUpdateBuilder() : UpdateBuilderBase(Author) {
    fun name(value: String): AuthorUpdateBuilder = put(Author.name, value)
    fun bio(value: String?): AuthorUpdateBuilder = put(Author.bio, value)
}

class AuthorInsertBuilder():InsertBuilderBase(){
       fun name(value: String): AuthorInsertBuilder = put(Author.name, value)
    fun bio(value: String?): AuthorInsertBuilder = put(Author.bio, value)

    fun mandatoryColumns(name: String) : AuthorInsertBuilder {
      mandatory(Author.name, name)
      return this
    }

}

