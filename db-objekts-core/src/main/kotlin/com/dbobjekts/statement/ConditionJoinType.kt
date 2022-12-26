package com.dbobjekts.statement


interface ConditionJoinType

object Or : ConditionJoinType {
    override fun toString() = "or"
}

object And : ConditionJoinType {
    override fun toString() = "and"
}
