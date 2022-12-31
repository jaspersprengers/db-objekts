package com.dbobjekts.testdb.acme.library

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.Entity
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase
import java.time.LocalDate

object Loan:Table<LoanRow>("LOAN"), HasUpdateBuilder<LoanUpdateBuilder, LoanInsertBuilder> {
    val itemId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "ITEM_ID", Item.id)
    val memberId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "MEMBER_ID", Member.id)
    val dateLoaned = com.dbobjekts.metadata.column.DateColumn(this, "DATE_LOANED")
    val dateReturned = com.dbobjekts.metadata.column.NullableDateColumn(this, "DATE_RETURNED")
    override val columns: List<AnyColumn> = listOf(itemId,memberId,dateLoaned,dateReturned)
    override fun toValue(values: List<Any?>) = LoanRow(values[0] as Long,values[1] as Long,values[2] as java.time.LocalDate,values[3] as java.time.LocalDate?)
    override fun metadata(): WriteQueryAccessors<LoanUpdateBuilder, LoanInsertBuilder> = WriteQueryAccessors(LoanUpdateBuilder(), LoanInsertBuilder())
}

class LoanUpdateBuilder() : UpdateBuilderBase(Loan) {
    fun itemId(value: Long): LoanUpdateBuilder = put(Loan.itemId, value)
    fun memberId(value: Long): LoanUpdateBuilder = put(Loan.memberId, value)
    fun dateLoaned(value: java.time.LocalDate): LoanUpdateBuilder = put(Loan.dateLoaned, value)
    fun dateReturned(value: java.time.LocalDate?): LoanUpdateBuilder = put(Loan.dateReturned, value)

    override fun updateRow(entity: Entity<*, *>): Long {
      entity as LoanRow
      add(Loan.itemId, entity.itemId)
      add(Loan.memberId, entity.memberId)
      add(Loan.dateLoaned, entity.dateLoaned)
      add(Loan.dateReturned, entity.dateReturned)
      return where (Loan.dateLoaned.eq(entity.dateLoaned))
    }    
        
}

class LoanInsertBuilder():InsertBuilderBase(){
    fun itemId(value: Long): LoanInsertBuilder = put(Loan.itemId, value)
    fun memberId(value: Long): LoanInsertBuilder = put(Loan.memberId, value)
    fun dateLoaned(value: java.time.LocalDate): LoanInsertBuilder = put(Loan.dateLoaned, value)
    fun dateReturned(value: java.time.LocalDate?): LoanInsertBuilder = put(Loan.dateReturned, value)

    fun mandatoryColumns(itemId: Long, memberId: Long, dateLoaned: java.time.LocalDate) : LoanInsertBuilder {
      mandatory(Loan.itemId, itemId)
      mandatory(Loan.memberId, memberId)
      mandatory(Loan.dateLoaned, dateLoaned)
      return this
    }


    override fun insertRow(entity: Entity<*, *>): Long {
      entity as LoanRow
      add(Loan.itemId, entity.itemId)
      add(Loan.memberId, entity.memberId)
      add(Loan.dateLoaned, entity.dateLoaned)
      add(Loan.dateReturned, entity.dateReturned)
      return execute()
    }    
        
}


data class LoanRow(
  val itemId: Long,
  val memberId: Long,
  val dateLoaned: LocalDate,
  val dateReturned: java.time.LocalDate?    
) : Entity<LoanUpdateBuilder, LoanInsertBuilder>(Loan.metadata())
        
