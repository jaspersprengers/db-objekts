package com.dbobjekts.testdb.acme.library

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyTable
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.TableRowData
import com.dbobjekts.statement.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.metadata.joins.JoinBase
import com.dbobjekts.metadata.joins.JoinType
import com.dbobjekts.metadata.joins.TableJoinChain
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase


/**           
 * Auto-generated metadata object for db table LIBRARY.AUTHOR.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: ID
 *
 * Foreign keys to: 
 * References by: LIBRARY.BOOK
 */
object Author:Table<AuthorRow>("AUTHOR"), HasUpdateBuilder<AuthorUpdateBuilder, AuthorInsertBuilder> {
    /**
     * Represents db column LIBRARY.AUTHOR.ID
     */
    val id = com.dbobjekts.metadata.column.SequenceKeyLongColumn(this, "ID", "AUTHOR_SEQ")
    /**
     * Represents db column LIBRARY.AUTHOR.NAME
     */
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "NAME")
    /**
     * Represents db column LIBRARY.AUTHOR.BIO
     */
    val bio = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "BIO")
    override val columns: List<AnyColumn> = listOf(id,name,bio)
    override fun toValue(values: List<Any?>) = AuthorRow(values[0] as Long,values[1] as String,values[2] as String?)
    override fun metadata(): WriteQueryAccessors<AuthorUpdateBuilder, AuthorInsertBuilder> = WriteQueryAccessors(AuthorUpdateBuilder(), AuthorInsertBuilder())

    fun leftJoin(table: com.dbobjekts.testdb.acme.library.Book): com.dbobjekts.testdb.acme.library.BookJoinChain = com.dbobjekts.testdb.acme.library.BookJoinChain(this)._join(table, JoinType.LEFT)
    fun innerJoin(table: com.dbobjekts.testdb.acme.library.Book): com.dbobjekts.testdb.acme.library.BookJoinChain = com.dbobjekts.testdb.acme.library.BookJoinChain(this)._join(table, JoinType.INNER)
    fun rightJoin(table: com.dbobjekts.testdb.acme.library.Book): com.dbobjekts.testdb.acme.library.BookJoinChain = com.dbobjekts.testdb.acme.library.BookJoinChain(this)._join(table, JoinType.RIGHT)                      
       
}

class AuthorJoinChain(table: AnyTable, joins: List<JoinBase> = listOf()) : TableJoinChain(table, joins) {
    
    fun leftJoin(table: com.dbobjekts.testdb.acme.library.Book): com.dbobjekts.testdb.acme.library.BookJoinChain = com.dbobjekts.testdb.acme.library.BookJoinChain(this.table, this.joins)._join(table, JoinType.LEFT)
    fun innerJoin(table: com.dbobjekts.testdb.acme.library.Book): com.dbobjekts.testdb.acme.library.BookJoinChain = com.dbobjekts.testdb.acme.library.BookJoinChain(this.table, this.joins)._join(table, JoinType.INNER)
    fun rightJoin(table: com.dbobjekts.testdb.acme.library.Book): com.dbobjekts.testdb.acme.library.BookJoinChain = com.dbobjekts.testdb.acme.library.BookJoinChain(this.table, this.joins)._join(table, JoinType.RIGHT)
}


class AuthorUpdateBuilder() : UpdateBuilderBase(Author) {
    fun name(value: String): AuthorUpdateBuilder = put(Author.name, value)
    fun bio(value: String?): AuthorUpdateBuilder = put(Author.bio, value)
    
    /**
     * FOR INTERNAL USE ONLY
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as AuthorRow
      add(Author.id, rowData.id)
      add(Author.name, rowData.name)
      add(Author.bio, rowData.bio)
      return where(Author.id.eq(rowData.id))
    }    
        
}

class AuthorInsertBuilder():InsertBuilderBase(){
    fun name(value: String): AuthorInsertBuilder = put(Author.name, value)
    fun bio(value: String?): AuthorInsertBuilder = put(Author.bio, value)

    fun mandatoryColumns(name: String) : AuthorInsertBuilder {
      mandatory(Author.name, name)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as AuthorRow
      add(Author.name, rowData.name)
      add(Author.bio, rowData.bio)
      return execute()
    }    
        
}


data class AuthorRow(
val id: Long = 0,
  val name: String,
  val bio: String?    
) : TableRowData<AuthorUpdateBuilder, AuthorInsertBuilder>(Author.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>(Pair(Author.id, id))
}
        
