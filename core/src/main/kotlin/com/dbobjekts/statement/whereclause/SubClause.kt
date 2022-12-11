package com.dbobjekts.statement.whereclause

import com.dbobjekts.metadata.column.Column
import com.dbobjekts.statement.*
import com.dbobjekts.vendors.Vendor


open class SubClause(
    override var joinType: ConditionJoinType = And,
    var isRoot: Boolean = false
) : WhereClauseComponent, HasWhereClauseComponents {

    private var vendorOpt: Vendor? = null
    private val buffer = mutableListOf<WhereClauseComponent>()

    override fun elements(): List<WhereClauseComponent> = buffer.toList()

    fun and(subClause: SubClause): SubClause {
        buffer.add(subClause)
        return this
    }

    fun or(subClause: SubClause): SubClause {
        subClause.joinType = Or
        buffer.add(subClause)
        return this
    }

    fun <C> and(column: Column<C>): Condition<C, SubClause> {
        val condition = Condition<C, SubClause>(this, column, And)
        buffer.add(condition)
        return condition
    }

    fun <C> or(column: Column<C>): Condition<C, SubClause> {
        val condition = Condition<C, SubClause>(this, column, Or)
        buffer.add(condition)
        return condition
    }

    fun <C> addCondition(
        column: Column<C>,
        joinType: ConditionJoinType = And,
        symbol: String,
        values: List<C>?,
    col: Column<C>?): Condition<C, SubClause>  {
        val condition = Condition<C, SubClause>(this, column, joinType, symbol, values, col)
        buffer.add(condition)
        return condition
    }

     fun setVendor(vendor: Vendor) {
        vendorOpt = vendor
    }

    override val keyword: String = ""

    override fun getChildren(): List<WhereClauseComponent> = elements().toList()

    override fun toSQL(options: SQLOptions): String {
        val str: String = with(elements()) {
            map {
                val isNotFirst = indexOf(it) > 0
                (if (isNotFirst) it.keyword else "") + it.toSQL(options)
            }.joinToString("")
        }
        val prefix = if (isRoot) "" else " $joinType "
        return "$prefix($str)"
    }

    override val clause: SubClause = this
}
