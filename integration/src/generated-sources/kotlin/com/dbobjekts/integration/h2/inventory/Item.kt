package com.dbobjekts.integration.h2.inventory

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Item:Table("ITEM"), HasUpdateBuilder<ItemUpdateBuilder, ItemInsertBuilder> {
    val id = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "ID")
    val bookId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "BOOK_ID", Book.id)
    val dateAcquired = com.dbobjekts.metadata.column.DateColumn(this, "DATE_ACQUIRED")
    override val columns: List<AnyColumn> = listOf(id,bookId,dateAcquired)
    override fun metadata(): WriteQueryAccessors<ItemUpdateBuilder, ItemInsertBuilder> = WriteQueryAccessors(ItemUpdateBuilder(), ItemInsertBuilder())
}

class ItemUpdateBuilder() : UpdateBuilderBase(Item) {
    fun bookId(value: Long): ItemUpdateBuilder = put(Item.bookId, value)
    fun dateAcquired(value: java.time.LocalDate): ItemUpdateBuilder = put(Item.dateAcquired, value)
}

class ItemInsertBuilder():InsertBuilderBase(){
       fun bookId(value: Long): ItemInsertBuilder = put(Item.bookId, value)
    fun dateAcquired(value: java.time.LocalDate): ItemInsertBuilder = put(Item.dateAcquired, value)

    fun mandatoryColumns(bookId: Long, dateAcquired: java.time.LocalDate) : ItemInsertBuilder {
      mandatory(Item.bookId, bookId)
      mandatory(Item.dateAcquired, dateAcquired)
      return this
    }

}

