package com.dbobjekts.testdb.acme.library

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Member:Table<MemberRow>("MEMBER"), HasUpdateBuilder<MemberUpdateBuilder, MemberInsertBuilder> {
    val id = com.dbobjekts.metadata.column.SequenceKeyLongColumn(this, "ID", "MEMBER_SEQ")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "NAME")
    override val columns: List<AnyColumn> = listOf(id,name)
    override fun toValue(values: List<Any?>) = MemberRow(values[0] as Long,values[1] as String)
    override fun metadata(): WriteQueryAccessors<MemberUpdateBuilder, MemberInsertBuilder> = WriteQueryAccessors(MemberUpdateBuilder(), MemberInsertBuilder())
}

class MemberUpdateBuilder() : UpdateBuilderBase(Member) {
    fun name(value: String): MemberUpdateBuilder = put(Member.name, value)
}

class MemberInsertBuilder():InsertBuilderBase(){
       fun name(value: String): MemberInsertBuilder = put(Member.name, value)

    fun mandatoryColumns(name: String) : MemberInsertBuilder {
      mandatory(Member.name, name)
      return this
    }

}

data class MemberRow(
    val id: Long,
    val name: String)
