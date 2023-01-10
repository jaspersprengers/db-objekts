package com.dbobjekts.testdb.acme.library

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.TableRowData
import com.dbobjekts.metadata.column.IsGeneratedPrimaryKey
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.statement.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase


/**           
 * Auto-generated metadata object for db table LIBRARY.BOOK_REVIEW.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: none
 *
 * Foreign keys: [ISBN to LIBRARY.BOOK.ISBN] 
 */
object BookReview:Table<BookReviewRow>("BOOK_REVIEW"), HasUpdateBuilder<BookReviewUpdateBuilder, BookReviewInsertBuilder> {
    /**
     * Represents db column LIBRARY.BOOK_REVIEW.ISBN
     *
     * Foreign key to LIBRARY.BOOK.ISBN
     */
    val isbn = com.dbobjekts.metadata.column.ForeignKeyVarcharColumn(this, "ISBN", Book.isbn)
    /**
     * Represents db column LIBRARY.BOOK_REVIEW.REVIEW
     */
    val review = com.dbobjekts.metadata.column.VarcharColumn(this, "REVIEW")
    override val columns: List<AnyColumn> = listOf(isbn,review)
    override fun toValue(values: List<Any?>) = BookReviewRow(values[0] as String,values[1] as String)
    override fun metadata(): WriteQueryAccessors<BookReviewUpdateBuilder, BookReviewInsertBuilder> = WriteQueryAccessors(BookReviewUpdateBuilder(), BookReviewInsertBuilder())
}

class BookReviewUpdateBuilder() : UpdateBuilderBase(BookReview) {
    fun isbn(value: String): BookReviewUpdateBuilder = put(BookReview.isbn, value)
    fun review(value: String): BookReviewUpdateBuilder = put(BookReview.review, value)

    /**
     * Warning: this method will throw a StatementBuilderException at runtime because BookReview does not have a primary key.
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long = 
      throw StatementBuilderException("Sorry, but you cannot use row-based updates for table BookReview. At least one column must be marked as primary key.")                
            
}

class BookReviewInsertBuilder():InsertBuilderBase(){
    fun isbn(value: String): BookReviewInsertBuilder = put(BookReview.isbn, value)
    fun review(value: String): BookReviewInsertBuilder = put(BookReview.review, value)

    fun mandatoryColumns(isbn: String, review: String) : BookReviewInsertBuilder {
      mandatory(BookReview.isbn, isbn)
      mandatory(BookReview.review, review)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as BookReviewRow
      add(BookReview.isbn, rowData.isbn)
      add(BookReview.review, rowData.review)
      return execute()
    }    
        
}


data class BookReviewRow(
  val isbn: String,
  val review: String    
) : TableRowData<BookReviewUpdateBuilder, BookReviewInsertBuilder>(BookReview.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>()
}
        
