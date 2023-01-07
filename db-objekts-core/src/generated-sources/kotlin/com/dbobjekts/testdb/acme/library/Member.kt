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
 * Metadata object for db table MEMBER.
 *
 * Primary key: id
 *
 * Foreign keys: [] 
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
      return where (Member.id.eq(rowData.id))
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
) : TableRowData<MemberUpdateBuilder, MemberInsertBuilder>(Member.metadata())
        
