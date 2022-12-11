package com.dbobjekts.statement

import com.dbobjekts.AnyColumn
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


    override fun toString(): String = column.dbName + symbol + (values ?: "null")

    override val notEqualsOperator = "<>"

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

        fun columnComponent(col: AnyColumn): String = if (options.includeAlias) column.aliasDotName() else column.dbName

        fun getOptionalAlias(col: AnyColumn) = "${col.table.alias()}.${col.dbName}"

        val rightOperand: String =
            if (columnCondition != null) getOptionalAlias(columnCondition!!) else getParameterCharactersForValues()

        return StringUtil.concat(
            listOf(
                columnComponent(column),
                symbol ?: throw IllegalStateException("This Condition is not finished"),
                rightOperand
            )
        )
    }

}
