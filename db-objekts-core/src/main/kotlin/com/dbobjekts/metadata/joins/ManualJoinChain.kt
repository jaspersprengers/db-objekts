package com.dbobjekts.metadata.joins

import com.dbobjekts.api.AnyTable
import com.dbobjekts.statement.SQLOptions
import com.dbobjekts.statement.whereclause.SubClause
import java.lang.StringBuilder


class ManualJoinChain(table: AnyTable) : JoinChain(table){

    private val buffer = mutableListOf<ManualJoin>()

    private fun newJoin(table: AnyTable, joinType: JoinType): ManualJoin {
        val join = ManualJoin(this, table, joinType)
        buffer += join
        return join
    }

    fun innerJoin(table: AnyTable): ManualJoin = newJoin(table, JoinType.INNER)
    fun leftJoin(table: AnyTable): ManualJoin = newJoin(table, JoinType.LEFT)
    fun rightJoin(table: AnyTable): ManualJoin = newJoin(table, JoinType.RIGHT)

    override fun toSQL(): String {
        val builder = StringBuilder("${table.schemaAndName()} ${table.alias()}")
        buffer.forEachIndexed { i, join ->
            val t = join.table
            val clause = SubClause.toSQL(join.clause, SQLOptions(true))
            builder.append(" ${join.joinType} JOIN ${t.schemaAndName()} ${t.alias()} on $clause")
        }
        return builder.toString()
    }

}

class ManualJoin(
    private val parent: ManualJoinChain,
    internal val table: AnyTable,
    internal val joinType: JoinType
) {
    internal lateinit var clause: SubClause

    fun on(subClause: SubClause): ManualJoinChain {
        clause = subClause
        return parent
    }
}
