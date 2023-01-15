package com.dbobjekts.statement.whereclause

import com.dbobjekts.api.AnyCondition
import com.dbobjekts.api.AnySqlParameter
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.statement.And
import com.dbobjekts.statement.SQLOptions
import com.dbobjekts.statement.SqlParameter
import com.dbobjekts.util.StringUtil

class WhereClause(
    private val clause: SubClause
) {

    fun build(options: SQLOptions): String =
        if (getFlattenedConditions().isEmpty()) "" else StringUtil.concat(listOf("where", toSQL(options)))

    fun toSQL(options: SQLOptions): String = SubClause.sql(clause, options)

    fun getParameters(): List<AnySqlParameter> {
        val flattenedConditions = getFlattenedConditions().filter { validateCondition(it).valueOrColumn.values != null }
        val params: List<AnySqlParameter> = flattenedConditions.flatMap { SqlParameter.fromWhereClauseCondition(it) }
        return params.mapIndexed { index, sqlParameter -> sqlParameter.copy(oneBasedPosition = index + 1) }
    }

    private fun validateCondition(condition: AnyCondition): AnyCondition {
        if (condition.column.aggregateType != null) {
            throw StatementBuilderException("Cannot use aggregate method ${condition.column.aggregateType} for column ${condition.column.nameInTable} in a whereclause.")
        }
        return condition
    }

    fun getFlattenedConditions(): List<AnyCondition> {
        fun getChildren(cle: WhereClauseComponent): List<WhereClauseComponent> =
            if (cle.getChildren().isEmpty()) listOf(cle)
            else cle.getChildren().flatMap { getChildren((it)) }

        return elements().toList().flatMap { getChildren(it) }.map { it as? AnyCondition }.filterNotNull()
    }

    private fun elements(): List<WhereClauseComponent> = clause.elements().toList()
}

object EmptyWhereClause : SubClause(And) {

}
