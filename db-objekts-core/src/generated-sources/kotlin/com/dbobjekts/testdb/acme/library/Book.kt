package com.dbobjekts.testdb.acme.library

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.TableRowData
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase


/**           
 * Metadata object for db table BOOK.
 *
 * Primary key: isbn
 *
 * Foreign keys: [LIBRARY.BOOK.AUTHOR_ID to LIBRARY.AUTHOR.ID] 
 */
object Book:Table<BookRow>("BOOK"), HasUpdateBuilder<BookUpdateBuilder, BookInsertBuilder> {
    /**
     * Represents db column LIBRARY.BOOK.ISBN
     */
    val isbn = com.dbobjekts.metadata.column.VarcharColumn(this, "ISBN")
    /**
     * Represents db column LIBRARY.BOOK.TITLE
     */
    val title = com.dbobjekts.metadata.column.VarcharColumn(this, "TITLE")
    /**
     * Represents db column LIBRARY.BOOK.AUTHOR_ID
     *
     * Foreign key to LIBRARY.AUTHOR.ID
     */
    val authorId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "AUTHOR_ID", Author.id)
    /**
     * Represents db column LIBRARY.BOOK.PUBLISHED
     */
    val published = com.dbobjekts.metadata.column.DateColumn(this, "PUBLISHED")
    override val columns: List<AnyColumn> = listOf(isbn,title,authorId,published)
    override fun toValue(values: List<Any?>) = BookRow(values[0] as String,values[1] as String,values[2] as Long,values[3] as java.time.LocalDate)
    override fun metadata(): WriteQueryAccessors<BookUpdateBuilder, BookInsertBuilder> = WriteQueryAccessors(BookUpdateBuilder(), BookInsertBuilder())
}

class BookUpdateBuilder() : UpdateBuilderBase(Book) {
    fun isbn(value: String): BookUpdateBuilder = put(Book.isbn, value)
    fun title(value: String): BookUpdateBuilder = put(Book.title, value)
    fun authorId(value: Long): BookUpdateBuilder = put(Book.authorId, value)
    fun published(value: java.time.LocalDate): BookUpdateBuilder = put(Book.published, value)
    
    /**
     * FOR INTERNAL USE ONLY
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as BookRow
      add(Book.isbn, rowData.isbn)
      add(Book.title, rowData.title)
      add(Book.authorId, rowData.authorId)
      add(Book.published, rowData.published)
      return where (Book.isbn.eq(rowData.isbn))
    }    
        
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


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as BookRow
      add(Book.isbn, rowData.isbn)
      add(Book.title, rowData.title)
      add(Book.authorId, rowData.authorId)
      add(Book.published, rowData.published)
      return execute()
    }    
        
}


data class BookRow(
  val isbn: String,
  val title: String,
  val authorId: Long,
  val published: java.time.LocalDate    
) : TableRowData<BookUpdateBuilder, BookInsertBuilder>(Book.metadata())
        
