package com.dbobjekts.testdb.acme.library

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.Entity
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Item:Table<ItemRow>("ITEM"), HasUpdateBuilder<ItemUpdateBuilder, ItemInsertBuilder> {
    val id = com.dbobjekts.metadata.column.SequenceKeyLongColumn(this, "ID", "ITEM_SEQ")
    val isbn = com.dbobjekts.metadata.column.ForeignKeyVarcharColumn(this, "ISBN", Book.isbn)
    val dateAcquired = com.dbobjekts.metadata.column.DateColumn(this, "DATE_ACQUIRED")
    override val columns: List<AnyColumn> = listOf(id,isbn,dateAcquired)
    override fun toValue(values: List<Any?>) = ItemRow(values[0] as Long,values[1] as String,values[2] as java.time.LocalDate)
    override fun metadata(): WriteQueryAccessors<ItemUpdateBuilder, ItemInsertBuilder> = WriteQueryAccessors(ItemUpdateBuilder(), ItemInsertBuilder())
}

class ItemUpdateBuilder() : UpdateBuilderBase(Item) {
    fun isbn(value: String): ItemUpdateBuilder = put(Item.isbn, value)
    fun dateAcquired(value: java.time.LocalDate): ItemUpdateBuilder = put(Item.dateAcquired, value)
    override fun updateRow(entity: Entity<*, *>): Long = throw RuntimeException()
}

class ItemInsertBuilder():InsertBuilderBase(){
       fun isbn(value: String): ItemInsertBuilder = put(Item.isbn, value)
    fun dateAcquired(value: java.time.LocalDate): ItemInsertBuilder = put(Item.dateAcquired, value)
    override fun insertRow(entity: Entity<*, *>): Long = throw RuntimeException()


    fun mandatoryColumns(isbn: String, dateAcquired: java.time.LocalDate) : ItemInsertBuilder {
      mandatory(Item.isbn, isbn)
      mandatory(Item.dateAcquired, dateAcquired)
      return this
    }

}

data class ItemRow(
    val id: Long,
    val isbn: String,
    val dateAcquired: java.time.LocalDate)
