package com.dbobjekts.integration.mariadb.nation

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Guests:Table("guests"), HasUpdateBuilder<GuestsUpdateBuilder, GuestsInsertBuilder> {
    val guestId = com.dbobjekts.metadata.column.LongColumn(this, "guest_id")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "name")
    override val columns: List<AnyColumn> = listOf(guestId,name)
    override fun metadata(): WriteQueryAccessors<GuestsUpdateBuilder, GuestsInsertBuilder> = WriteQueryAccessors(GuestsUpdateBuilder(), GuestsInsertBuilder())
}

class GuestsUpdateBuilder() : UpdateBuilderBase(Guests) {
    fun guestId(value: Long): GuestsUpdateBuilder = put(Guests.guestId, value)
    fun name(value: String): GuestsUpdateBuilder = put(Guests.name, value)
}

class GuestsInsertBuilder():InsertBuilderBase(){
       fun guestId(value: Long): GuestsInsertBuilder = put(Guests.guestId, value)
    fun name(value: String): GuestsInsertBuilder = put(Guests.name, value)

    fun mandatoryColumns(guestId: Long, name: String) : GuestsInsertBuilder {
      mandatory(Guests.guestId, guestId)
      mandatory(Guests.name, name)
      return this
    }

}

