package com.dbobjekts.statement.whereclause

import com.dbobjekts.statement.ConditionJoinType
import com.dbobjekts.statement.SQLOptions

/**
 * Either a Condition (name eq "John") or a Subclause (age > 18 and married eq false)
 * ClauseElements can be nested to form a complex whereclause, e.g. where age > 18 or (age < 18 and married eq false)
 */
abstract class WhereClauseComponent {

    internal abstract val joinType: ConditionJoinType

    internal abstract val keyword: String

    internal abstract fun getChildren(): List<WhereClauseComponent>

    internal abstract fun toSQL(options: SQLOptions): String
}
