package com.dbobjekts.statement.select

import com.dbobjekts.metadata.column.Aggregate
import com.dbobjekts.metadata.column.AggregateColumn
import com.dbobjekts.metadata.joins.TableJoinChain
import com.dbobjekts.statement.ColumnInResultRow
import com.dbobjekts.statement.SQLOptions
import com.dbobjekts.statement.whereclause.WhereClause
import com.dbobjekts.util.StringUtil

class SelectStatementSqlBuilder {

    var whereClause: WhereClause? = null
    var orderByClauses: List<OrderByClause<*>> = listOf()
    var columnsToFetch: List<ColumnInResultRow> = listOf()
    var limitBy: Int = 0
    var limitFunction: ((Int) -> String)? = null
    var havingCondition: String? = null

    private lateinit var joinChain: TableJoinChain

    fun withJoinChain(joinChain: TableJoinChain): SelectStatementSqlBuilder {
        this.joinChain = joinChain
        return this
    }

    fun withWhereClause(whereClause: WhereClause): SelectStatementSqlBuilder {
        this.whereClause = whereClause
        return this
    }

    fun withOrderByClause(obClauses: List<OrderByClause<*>>): SelectStatementSqlBuilder {
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

    private fun isAggregate(): Boolean = columnsToFetch.any { it.column is AggregateColumn }

    protected fun whereClauseSql(): String = whereClause?.build(SQLOptions(includeAlias = true)) ?: ""

    protected fun orderBySql(): String = if (orderByClauses.isNotEmpty()) ("ORDER BY " + StringUtil.joinBy(orderByClauses, ",")) else ""

    protected fun columnsToSelect(): String = StringUtil.joinBy(columnsToFetch, { it.column.forSelect() }, ",")

    protected fun groupBySql(): String {
        val groupByCols = columnsToFetch.filterNot { it.column is AggregateColumn && it.column.aggregateType != null }
        return if (isAggregate())
            "group by " + StringUtil.joinBy(groupByCols, { it.column.aliasDotName() }, ",")
        else ""
    }

    protected fun havingSql(): String {
        return havingCondition?.let { Aggregate.havingClause(it) } ?: ""
    }

    protected fun limitClause(): String = limitFunction?.invoke(limitBy) ?: ""

    fun build(): String =
        StringUtil.concat(
            listOf(
                "select",
                columnsToSelect(),
                "from",
                joinChain.toSQL(),
                whereClauseSql(),
                groupBySql(),
                havingSql(),
                orderBySql(),
                limitClause()
            )
        )


}
