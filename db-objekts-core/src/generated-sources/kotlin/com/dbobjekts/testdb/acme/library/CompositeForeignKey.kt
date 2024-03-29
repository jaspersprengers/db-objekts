package com.dbobjekts.testdb.acme.library

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.TableRowData
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.NullableVarcharColumn
import com.dbobjekts.metadata.column.VarcharColumn
import com.dbobjekts.statement.WriteQueryAccessors
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase

/**           
 * Auto-generated metadata object for db table LIBRARY.COMPOSITE_FOREIGN_KEY.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: [ISBN, TITLE]
 *
 * Foreign keys to: 
 * References by: 
 */
object CompositeForeignKey:Table<CompositeForeignKeyRow>("COMPOSITE_FOREIGN_KEY"), HasUpdateBuilder<CompositeForeignKeyUpdateBuilder, CompositeForeignKeyInsertBuilder> {
    /**
     * Represents db column LIBRARY.COMPOSITE_FOREIGN_KEY.ISBN
     */
    val isbn = VarcharColumn(this, "ISBN")
    /**
     * Represents db column LIBRARY.COMPOSITE_FOREIGN_KEY.TITLE
     */
    val title = VarcharColumn(this, "TITLE")
    /**
     * Represents db column LIBRARY.COMPOSITE_FOREIGN_KEY.MESSAGE
     */
    val message = NullableVarcharColumn(this, "MESSAGE")
    override val columns: List<AnyColumn> = listOf(isbn,title,message)
    override fun toValue(values: List<Any?>) = CompositeForeignKeyRow(values[0] as String,values[1] as String,values[2] as String?)
    override fun metadata(): WriteQueryAccessors<CompositeForeignKeyUpdateBuilder, CompositeForeignKeyInsertBuilder> = WriteQueryAccessors(CompositeForeignKeyUpdateBuilder(), CompositeForeignKeyInsertBuilder())
}

class CompositeForeignKeyUpdateBuilder() : UpdateBuilderBase(CompositeForeignKey) {
    fun isbn(value: String): CompositeForeignKeyUpdateBuilder = put(CompositeForeignKey.isbn, value)
    fun title(value: String): CompositeForeignKeyUpdateBuilder = put(CompositeForeignKey.title, value)
    fun message(value: String?): CompositeForeignKeyUpdateBuilder = put(CompositeForeignKey.message, value)
    
    /**
     * FOR INTERNAL USE ONLY
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as CompositeForeignKeyRow
      add(CompositeForeignKey.isbn, rowData.isbn)
      add(CompositeForeignKey.title, rowData.title)
      add(CompositeForeignKey.message, rowData.message)
      return where(CompositeForeignKey.isbn.eq(rowData.isbn).and(CompositeForeignKey.title.eq(rowData.title)))
    }    
        
}

class CompositeForeignKeyInsertBuilder():InsertBuilderBase(){
    fun isbn(value: String): CompositeForeignKeyInsertBuilder = put(CompositeForeignKey.isbn, value)
    fun title(value: String): CompositeForeignKeyInsertBuilder = put(CompositeForeignKey.title, value)
    fun message(value: String?): CompositeForeignKeyInsertBuilder = put(CompositeForeignKey.message, value)

    fun mandatoryColumns(isbn: String, title: String) : CompositeForeignKeyInsertBuilder {
      mandatory(CompositeForeignKey.isbn, isbn)
      mandatory(CompositeForeignKey.title, title)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as CompositeForeignKeyRow
      add(CompositeForeignKey.isbn, rowData.isbn)
      add(CompositeForeignKey.title, rowData.title)
      add(CompositeForeignKey.message, rowData.message)
      return execute()
    }    
        
}


data class CompositeForeignKeyRow(
  val isbn: String,
  val title: String,
  val message: String?    
) : TableRowData<CompositeForeignKeyUpdateBuilder, CompositeForeignKeyInsertBuilder>(CompositeForeignKey.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>(Pair(CompositeForeignKey.isbn, isbn),Pair(CompositeForeignKey.title, title))
}
        
