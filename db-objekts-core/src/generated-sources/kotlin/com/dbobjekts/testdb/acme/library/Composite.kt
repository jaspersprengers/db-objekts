package com.dbobjekts.testdb.acme.library

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyTable
import com.dbobjekts.api.TableRowData
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.NullableDateColumn
import com.dbobjekts.metadata.column.VarcharColumn
import com.dbobjekts.metadata.joins.JoinBase
import com.dbobjekts.metadata.joins.JoinType
import com.dbobjekts.metadata.joins.TableJoinChain
import com.dbobjekts.statement.WriteQueryAccessors
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase

/**           
 * Auto-generated metadata object for db table LIBRARY.COMPOSITE.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: [ISBN, TITLE]
 *
 * Foreign keys to: 
 * References by: 
 */
object Composite:Table<CompositeRow>("COMPOSITE"), HasUpdateBuilder<CompositeUpdateBuilder, CompositeInsertBuilder> {
    /**
     * Represents db column LIBRARY.COMPOSITE.ISBN
     */
    val isbn = VarcharColumn(this, "ISBN")
    /**
     * Represents db column LIBRARY.COMPOSITE.TITLE
     */
    val title = VarcharColumn(this, "TITLE")
    /**
     * Represents db column LIBRARY.COMPOSITE.PUBLISHED
     */
    val published = NullableDateColumn(this, "PUBLISHED")
    override val columns: List<AnyColumn> = listOf(isbn,title,published)
    override fun toValue(values: List<Any?>) = CompositeRow(values[0] as String,values[1] as String,values[2] as java.time.LocalDate?)
    override fun metadata(): WriteQueryAccessors<CompositeUpdateBuilder, CompositeInsertBuilder> = WriteQueryAccessors(CompositeUpdateBuilder(), CompositeInsertBuilder())

}

class CompositeJoinChain(table: AnyTable, joins: List<JoinBase> = listOf()) : TableJoinChain(table, joins) {
    
}


class CompositeUpdateBuilder() : UpdateBuilderBase(Composite) {
    fun isbn(value: String): CompositeUpdateBuilder = put(Composite.isbn, value)
    fun title(value: String): CompositeUpdateBuilder = put(Composite.title, value)
    fun published(value: java.time.LocalDate?): CompositeUpdateBuilder = put(Composite.published, value)
    
    /**
     * FOR INTERNAL USE ONLY
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as CompositeRow
      add(Composite.isbn, rowData.isbn)
      add(Composite.title, rowData.title)
      add(Composite.published, rowData.published)
      return where(Composite.isbn.eq(rowData.isbn).and(Composite.title.eq(rowData.title)))
    }    
        
}

class CompositeInsertBuilder():InsertBuilderBase(){
    fun isbn(value: String): CompositeInsertBuilder = put(Composite.isbn, value)
    fun title(value: String): CompositeInsertBuilder = put(Composite.title, value)
    fun published(value: java.time.LocalDate?): CompositeInsertBuilder = put(Composite.published, value)

    fun mandatoryColumns(isbn: String, title: String) : CompositeInsertBuilder {
      mandatory(Composite.isbn, isbn)
      mandatory(Composite.title, title)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as CompositeRow
      add(Composite.isbn, rowData.isbn)
      add(Composite.title, rowData.title)
      add(Composite.published, rowData.published)
      return execute()
    }    
        
}


data class CompositeRow(
  val isbn: String,
  val title: String,
  val published: java.time.LocalDate?    
) : TableRowData<CompositeUpdateBuilder, CompositeInsertBuilder>(Composite.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>(Pair(Composite.isbn, isbn),Pair(Composite.title, title))
}
        
