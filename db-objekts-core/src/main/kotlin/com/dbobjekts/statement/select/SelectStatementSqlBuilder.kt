package com.dbobjekts.statement.select

import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.statement.ColumnInResultRow
import com.dbobjekts.statement.SQLOptions
import com.dbobjekts.statement.SQLOptions.Companion.ALIAS
import com.dbobjekts.statement.whereclause.WhereClause
import com.dbobjekts.util.StringUtil

class SelectStatementSqlBuilder {

    var whereClause: WhereClause? = null
    var orderByClauses: List<OrderByClause> = listOf()
    var columnsToFetch: List<ColumnInResultRow> = listOf()
    var limitBy: Int = 0
    var limitFunction: ((Int) -> String)? = null
    var havingCondition: String? = null

    private lateinit var joinChain: String

    fun withJoinChain(joinChain: String): SelectStatementSqlBuilder {
        this.joinChain = joinChain
        return this
    }

    fun withWhereClause(whereClause: WhereClause): SelectStatementSqlBuilder {
        this.whereClause = whereClause
        return this
    }

    fun withOrderByClause(obClauses: List<OrderByClause>): SelectStatementSqlBuilder {
        this.orderByClauses = obClauses
        return this
    }

    fun withColumnsToSelect(columnsToFetch: List<ColumnInResultRow>): SelectStatementSqlBuilder {
        this.columnsToFetch = columnsToFetch
        return this
    }

    fun withLimitClause(limitBy: Int, fct: (Int) -> String): SelectStatementSqlBuilder {
        this.limitBy = limitBy
        this.limitFunction = fct
        return this
    }

    fun withHavingClause(havingCondition: String?): SelectStatementSqlBuilder {
        this.havingCondition = havingCondition
        return this
    }

    protected fun whereClauseSql(): String = whereClause?.build(ALIAS) ?: ""

    protected fun orderBySql(): String = if (orderByClauses.isNotEmpty()) ("ORDER BY " + StringUtil.joinBy(orderByClauses, ",")) else ""

    protected fun columnsToSelect(): String = StringUtil.joinBy(columnsToFetch, { it.column.forSelect() }, ",")

    protected fun groupBySql(): String {
        val (aggregates, nonAggregates) = columnsToFetch.partition { it.column.aggregateType?.usesGroupBy ?: false  }
        if (aggregates.size > 1) {
            throw StatementBuilderException("You can only use one aggregation type (sum/min/max/avg/count) in a select query, but you used ${aggregates.size}")
        }
        return if (aggregates.isNotEmpty() && !nonAggregates.isEmpty()) {
            "group by " + StringUtil.joinBy(nonAggregates, { it.column.aliasDotName() }, ",")
        } else ""
    }

    protected fun limitClause(): String = limitFunction?.invoke(limitBy) ?: ""

    fun build(): String =
        StringUtil.concat(
            listOf(
                "select",
                columnsToSelect(),
                "from",
                joinChain,
                whereClauseSql(),
                groupBySql(),
                havingCondition ?: "",
                orderBySql(),
                limitClause()
            )
        )


}
