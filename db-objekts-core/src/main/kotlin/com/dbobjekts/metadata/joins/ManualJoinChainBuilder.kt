package com.dbobjekts.metadata.joins

import com.dbobjekts.api.AnyTable
import com.dbobjekts.statement.SQLOptions
import com.dbobjekts.statement.whereclause.SubClause
import java.lang.StringBuilder


class ManualJoinChainBuilder(private val drivingTable: AnyTable) {

    private val buffer = mutableListOf<ManualJoinChain>()

    fun leftJoin(table: AnyTable): ManualJoinChain {
        val join = ManualJoinChain(this, table, JoinType.LEFT)
        buffer += join
        return join
    }

    override fun toString(): String {
        val builder = StringBuilder("${drivingTable.dbName} ${drivingTable.alias()}")
        buffer.forEachIndexed { i, join ->
            val t = join.table
            val clause = SubClause.sql(join.clause, SQLOptions(true))
            builder.append(" ${join.joinType} JOIN ${t.dbName} ${t.alias()} on $clause")
        }
        return builder.toString()
    }

}

class ManualJoinChain(
    private val parent: ManualJoinChainBuilder,
    internal val table: AnyTable,
    internal val joinType: JoinType
) {
    internal lateinit var clause: SubClause

    fun on(subClause: SubClause): ManualJoinChainBuilder {
        clause = subClause
        return parent
    }
}
