package com.dbobjekts.statement

import com.dbobjekts.api.AnyCondition
import com.dbobjekts.api.AnySqlParameter
import com.dbobjekts.statement.whereclause.SubClause
import com.dbobjekts.statement.whereclause.WhereClauseComponent

interface HasWhereClauseComponents {

    val clause: SubClause
    fun elements(): List<WhereClauseComponent> = clause.elements().toList()

    fun getParameters(): List<AnySqlParameter> {
        val flattenedConditions = getFlattenedConditions().filter {it.values != null}
        val params: List<AnySqlParameter> = flattenedConditions.flatMap {SqlParameter.fromWhereClauseCondition(it)}
        return params.mapIndexed { index, sqlParameter ->  sqlParameter.copy(oneBasedPosition = index+1)}
    }

    fun getFlattenedConditions(): List<AnyCondition> {
        fun getChildren(cle: WhereClauseComponent): List<WhereClauseComponent> =
            if (cle.getChildren().isEmpty()) listOf(cle)
            else cle.getChildren().flatMap { getChildren((it)) }

        return elements().toList().flatMap { getChildren(it) }.map { it as? AnyCondition }.filterNotNull()
    }

}
