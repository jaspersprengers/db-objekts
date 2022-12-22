package com.dbobjekts.integration.h2.operations

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase
import com.dbobjekts.integration.h2.inventory.Item


object Loan:Table("LOAN"), HasUpdateBuilder<LoanUpdateBuilder, LoanInsertBuilder> {
    val id = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "ID")
    val itemId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "ITEM_ID", Item.id)
    val memberId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "MEMBER_ID", Member.id)
    val dateLoaned = com.dbobjekts.metadata.column.TimeStampColumn(this, "DATE_LOANED")
    val dateReturned = com.dbobjekts.metadata.column.NullableTimeStampColumn(this, "DATE_RETURNED")
    override val columns: List<AnyColumn> = listOf(id,itemId,memberId,dateLoaned,dateReturned)
    override fun metadata(): WriteQueryAccessors<LoanUpdateBuilder, LoanInsertBuilder> = WriteQueryAccessors(LoanUpdateBuilder(), LoanInsertBuilder())
}

class LoanUpdateBuilder() : UpdateBuilderBase(Loan) {
    fun itemId(value: Long): LoanUpdateBuilder = put(Loan.itemId, value)
    fun memberId(value: Long): LoanUpdateBuilder = put(Loan.memberId, value)
    fun dateLoaned(value: java.time.Instant): LoanUpdateBuilder = put(Loan.dateLoaned, value)
    fun dateReturned(value: java.time.Instant?): LoanUpdateBuilder = put(Loan.dateReturned, value)
}

class LoanInsertBuilder():InsertBuilderBase(){
       fun itemId(value: Long): LoanInsertBuilder = put(Loan.itemId, value)
    fun memberId(value: Long): LoanInsertBuilder = put(Loan.memberId, value)
    fun dateLoaned(value: java.time.Instant): LoanInsertBuilder = put(Loan.dateLoaned, value)
    fun dateReturned(value: java.time.Instant?): LoanInsertBuilder = put(Loan.dateReturned, value)

    fun mandatoryColumns(itemId: Long, memberId: Long, dateLoaned: java.time.Instant) : LoanInsertBuilder {
      mandatory(Loan.itemId, itemId)
      mandatory(Loan.memberId, memberId)
      mandatory(Loan.dateLoaned, dateLoaned)
      return this
    }

}

