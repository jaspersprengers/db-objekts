package com.dbobjekts.metadata.joins

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table

abstract class JoinBase(val leftPart: AnyColumn,
                        val rightPart: AnyColumn) {

  abstract val keyWord: String

  val conditionSQL: String = "${rightPart.table.toSQL()} on ${leftPart.aliasDotName()} = ${rightPart.aliasDotName()}"

  override fun toString(): String = "${leftPart.table.toSQL()} $keyWord $conditionSQL"

  fun containsTable(table: Table): Boolean = leftPart.table == table || rightPart.table == table

}
