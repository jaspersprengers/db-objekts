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
 * Auto-generated metadata object for db table LIBRARY.MEMBER.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: ID
 *
 * Foreign keys to: 
 * References by: LIBRARY.LOAN
 */
object Member:Table<MemberRow>("MEMBER"), HasUpdateBuilder<MemberUpdateBuilder, MemberInsertBuilder> {
    /**
     * Represents db column LIBRARY.MEMBER.ID
     */
    val id = com.dbobjekts.metadata.column.SequenceKeyLongColumn(this, "ID", "MEMBER_SEQ")
    /**
     * Represents db column LIBRARY.MEMBER.NAME
     */
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "NAME")
    override val columns: List<AnyColumn> = listOf(id,name)
    override fun toValue(values: List<Any?>) = MemberRow(values[0] as Long,values[1] as String)
    override fun metadata(): WriteQueryAccessors<MemberUpdateBuilder, MemberInsertBuilder> = WriteQueryAccessors(MemberUpdateBuilder(), MemberInsertBuilder())

    fun leftJoin(table: com.dbobjekts.testdb.acme.library.Loan): com.dbobjekts.testdb.acme.library.LoanJoinChain = com.dbobjekts.testdb.acme.library.LoanJoinChain(this)._join(table, JoinType.LEFT)
    fun innerJoin(table: com.dbobjekts.testdb.acme.library.Loan): com.dbobjekts.testdb.acme.library.LoanJoinChain = com.dbobjekts.testdb.acme.library.LoanJoinChain(this)._join(table, JoinType.INNER)
    fun rightJoin(table: com.dbobjekts.testdb.acme.library.Loan): com.dbobjekts.testdb.acme.library.LoanJoinChain = com.dbobjekts.testdb.acme.library.LoanJoinChain(this)._join(table, JoinType.RIGHT)                      
       
}

class MemberJoinChain(table: AnyTable, joins: List<JoinBase> = listOf()) : TableJoinChain(table, joins) {
    
    fun leftJoin(table: com.dbobjekts.testdb.acme.library.Loan): com.dbobjekts.testdb.acme.library.LoanJoinChain = com.dbobjekts.testdb.acme.library.LoanJoinChain(this.table, this.joins)._join(table, JoinType.LEFT)
    fun innerJoin(table: com.dbobjekts.testdb.acme.library.Loan): com.dbobjekts.testdb.acme.library.LoanJoinChain = com.dbobjekts.testdb.acme.library.LoanJoinChain(this.table, this.joins)._join(table, JoinType.INNER)
    fun rightJoin(table: com.dbobjekts.testdb.acme.library.Loan): com.dbobjekts.testdb.acme.library.LoanJoinChain = com.dbobjekts.testdb.acme.library.LoanJoinChain(this.table, this.joins)._join(table, JoinType.RIGHT)
}


class MemberUpdateBuilder() : UpdateBuilderBase(Member) {
    fun name(value: String): MemberUpdateBuilder = put(Member.name, value)
    
    /**
     * FOR INTERNAL USE ONLY
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as MemberRow
      add(Member.id, rowData.id)
      add(Member.name, rowData.name)
      return where(Member.id.eq(rowData.id))
    }    
        
}

class MemberInsertBuilder():InsertBuilderBase(){
    fun name(value: String): MemberInsertBuilder = put(Member.name, value)

    fun mandatoryColumns(name: String) : MemberInsertBuilder {
      mandatory(Member.name, name)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as MemberRow
      add(Member.name, rowData.name)
      return execute()
    }    
        
}


data class MemberRow(
val id: Long = 0,
  val name: String    
) : TableRowData<MemberUpdateBuilder, MemberInsertBuilder>(Member.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>(Pair(Member.id, id))
}
        
