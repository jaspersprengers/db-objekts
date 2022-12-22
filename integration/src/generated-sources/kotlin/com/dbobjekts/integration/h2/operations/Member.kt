package com.dbobjekts.integration.h2.operations

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Member:Table("MEMBER"), HasUpdateBuilder<MemberUpdateBuilder, MemberInsertBuilder> {
    val id = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "ID")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "NAME")
    val surname = com.dbobjekts.metadata.column.VarcharColumn(this, "SURNAME")
    val dateOfBirth = com.dbobjekts.metadata.column.DateColumn(this, "DATE_OF_BIRTH")
    override val columns: List<AnyColumn> = listOf(id,name,surname,dateOfBirth)
    override fun metadata(): WriteQueryAccessors<MemberUpdateBuilder, MemberInsertBuilder> = WriteQueryAccessors(MemberUpdateBuilder(), MemberInsertBuilder())
}

class MemberUpdateBuilder() : UpdateBuilderBase(Member) {
    fun name(value: String): MemberUpdateBuilder = put(Member.name, value)
    fun surname(value: String): MemberUpdateBuilder = put(Member.surname, value)
    fun dateOfBirth(value: java.time.LocalDate): MemberUpdateBuilder = put(Member.dateOfBirth, value)
}

class MemberInsertBuilder():InsertBuilderBase(){
       fun name(value: String): MemberInsertBuilder = put(Member.name, value)
    fun surname(value: String): MemberInsertBuilder = put(Member.surname, value)
    fun dateOfBirth(value: java.time.LocalDate): MemberInsertBuilder = put(Member.dateOfBirth, value)

    fun mandatoryColumns(name: String, surname: String, dateOfBirth: java.time.LocalDate) : MemberInsertBuilder {
      mandatory(Member.name, name)
      mandatory(Member.surname, surname)
      mandatory(Member.dateOfBirth, dateOfBirth)
      return this
    }

}

