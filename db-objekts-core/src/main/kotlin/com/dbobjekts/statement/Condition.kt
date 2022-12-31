package com.dbobjekts.statement

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.metadata.column.Column
import com.dbobjekts.statement.whereclause.ConditionFactory
import com.dbobjekts.statement.whereclause.WhereClauseComponent
import com.dbobjekts.util.StringUtil

data class Condition<I, W : WhereClauseComponent>(
    val parent: W,
    val column: Column<I>,
    override val joinType: ConditionJoinType = And,
    private var symbol: String? = null,
     var values: List<I>? = null,
     var columnCondition: Column<I>? = null
) : WhereClauseComponent, ConditionFactory<I, W> {

    private fun getParameterCharactersForValues(): String =
        if (values == null) "" else (if (values!!.size == 1) "?" else "(${values!!.map { "?" }.joinToString(",")})")


    override fun toString(): String = column.nameInTable + symbol + (values ?: "null")

    override fun createSubClause(
        symbol: String,
        values: List<I>?,
        secondColumn: Column<I>?
    ): W {
        this.symbol = symbol
        this.values = values
        return parent
    }

    override val keyword: String = " $joinType "

    override fun getChildren(): List<WhereClauseComponent> = listOf()

    override fun toSQL(options: SQLOptions): String {

        fun columnComponent(): String = if (options.includeAlias) column.aliasDotName() else column.nameInTable

        fun getOptionalAlias(col: AnyColumn) = "${col.table.alias()}.${col.nameInTable}"

        val rightOperand: String =
            if (columnCondition != null) getOptionalAlias(columnCondition!!) else getParameterCharactersForValues()

        return StringUtil.concat(
            listOf(
                columnComponent(),
                symbol ?: throw StatementBuilderException("This Condition is not finished"),
                rightOperand
            )
        )
    }

}
