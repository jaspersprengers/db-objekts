package com.dbobjekts.testdb.acme.library

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Book:Table<BookRow>("BOOK"), HasUpdateBuilder<BookUpdateBuilder, BookInsertBuilder> {
    val isbn = com.dbobjekts.metadata.column.VarcharColumn(this, "ISBN")
    val title = com.dbobjekts.metadata.column.VarcharColumn(this, "TITLE")
    val authorId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "AUTHOR_ID", Author.id)
    val published = com.dbobjekts.metadata.column.DateColumn(this, "PUBLISHED")
    override val columns: List<AnyColumn> = listOf(isbn,title,authorId,published)
    override fun toValue(values: List<Any?>): BookRow = BookRow()

    override fun metadata(): WriteQueryAccessors<BookUpdateBuilder, BookInsertBuilder> = WriteQueryAccessors(BookUpdateBuilder(), BookInsertBuilder())
}
class BookRow()
class BookUpdateBuilder() : UpdateBuilderBase(Book) {
    fun isbn(value: String): BookUpdateBuilder = put(Book.isbn, value)
    fun title(value: String): BookUpdateBuilder = put(Book.title, value)
    fun authorId(value: Long): BookUpdateBuilder = put(Book.authorId, value)
    fun published(value: java.time.LocalDate): BookUpdateBuilder = put(Book.published, value)
}

class BookInsertBuilder():InsertBuilderBase(){
       fun isbn(value: String): BookInsertBuilder = put(Book.isbn, value)
    fun title(value: String): BookInsertBuilder = put(Book.title, value)
    fun authorId(value: Long): BookInsertBuilder = put(Book.authorId, value)
    fun published(value: java.time.LocalDate): BookInsertBuilder = put(Book.published, value)

    fun mandatoryColumns(isbn: String, title: String, authorId: Long, published: java.time.LocalDate) : BookInsertBuilder {
      mandatory(Book.isbn, isbn)
      mandatory(Book.title, title)
      mandatory(Book.authorId, authorId)
      mandatory(Book.published, published)
      return this
    }

}

