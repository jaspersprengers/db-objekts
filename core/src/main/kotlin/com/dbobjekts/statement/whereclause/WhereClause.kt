package com.dbobjekts.statement.whereclause

import com.dbobjekts.statement.And
import com.dbobjekts.statement.HasWhereClauseComponents
import com.dbobjekts.statement.SQLOptions
import com.dbobjekts.util.StringUtil
import com.dbobjekts.vendors.Vendor

class WhereClause(override val clause: SubClause,
                  val vendor: Vendor) : HasWhereClauseComponents {

   fun build(options: SQLOptions): String =
    if (getFlattenedConditions().isEmpty()) "" else StringUtil.concat(listOf("where", toSQL(options)))

  fun toSQL(options: SQLOptions): String {
    val sql = elements().map { clauseElement ->
      val isNotFirst = elements().indexOf(clauseElement) > 0
      (if (isNotFirst) clauseElement.keyword else "") + clauseElement.toSQL(options)
    }.joinToString("")
    return sql
  }
}

object EmptyWhereClause : SubClause(And){

}
