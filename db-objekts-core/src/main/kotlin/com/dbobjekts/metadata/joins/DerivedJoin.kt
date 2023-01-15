package com.dbobjekts.metadata.joins

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyTable
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.metadata.column.IsForeignKey
import com.dbobjekts.statement.whereclause.SubClause
import com.dbobjekts.util.StringUtil

class DerivedJoin(
    table: AnyTable
) : Cloneable, JoinChain(table) {

    val pairs = mutableListOf<Pair<AnyTable, JoinType>>()

    constructor(joinChain: DerivedJoin) : this(joinChain.table)

    val joins = mutableListOf<JoinBase>()

    fun on(clause: SubClause): ManualJoinChain {
        if (pairs.size != 1)
            throw StatementBuilderException("Illegal state")
        val (tableToJoin, joinType) = pairs[0]
        return when (joinType) {
            JoinType.LEFT -> ManualJoinChain(table).leftJoin(tableToJoin).on(clause)
            JoinType.INNER -> ManualJoinChain(table).innerJoin(tableToJoin).on(clause)
            JoinType.RIGHT -> ManualJoinChain(table).rightJoin(tableToJoin).on(clause)
        }
    }

    @Suppress("UNCHECKED_CAST")
    internal fun join(table: AnyTable, joinType: JoinType): DerivedJoin {
        pairs.add(Pair(table, joinType))
        return this
    }

    fun leftJoin(table: AnyTable): DerivedJoin = join(table, JoinType.LEFT)
    fun innerJoin(table: AnyTable): DerivedJoin = join(table, JoinType.INNER)
    fun rightJoin(table: AnyTable): DerivedJoin = join(table, JoinType.RIGHT)


    private fun createJoin(joinType: JoinType, tuple: Pair<AnyColumn, AnyColumn>) = when (joinType) {
        JoinType.LEFT -> LeftJoin(tuple.first, tuple.second)
        JoinType.RIGHT -> RightJoin(tuple.first, tuple.second)
        JoinType.INNER -> InnerJoin(tuple.first, tuple.second)
    }

    private fun lastJoinTable(): AnyTable = if (joins.isEmpty()) table else joins.last().rightPart.table

    private fun checkTableNotJoinedAlready(table: AnyTable) {
        if (joins.any { it.containsTable(table) })
            throw StatementBuilderException("Table ${table.tableName} is already present in join. You cannot add it again: ${toSQL()}")
    }

    private fun extractJoinedColumnPair(table: AnyTable): Pair<AnyColumn, AnyColumn> {
        val head =
            if (joins.isEmpty()) getJoinPair(lastJoinTable(), lastJoinTable(), table)
            else joins.reversed().map { getJoinPair(it.leftPart.table, it.rightPart.table, table) }.filterNotNull().firstOrNull()

        return if (head == null) throw StatementBuilderException("Cannot join ${table.toSQL()} to ${lastJoinTable().toSQL()}") else head
    }

    private fun getJoinPair(left: AnyTable, right: AnyTable, tableToAdd: AnyTable): Pair<AnyColumn, AnyColumn>? {

        left.getForeignKeyToParent(tableToAdd)?.let {
            return Pair(
                it.column,
                tableToAdd.columnByName(it.parentColumn.nameInTable)
                    ?: throw StatementBuilderException("Cannot resolve foreign key for $tableToAdd")
            )
        }

        right.getForeignKeyToParent(tableToAdd)?.let {
            return Pair(
                it.column,
                tableToAdd.columnByName(it.parentColumn.nameInTable)
                    ?: throw StatementBuilderException("Cannot resolve foreign key for $tableToAdd")
            )
        }

        tableToAdd.getForeignKeyToParent(left)?.let {
            return Pair(
                left.columnByName(it.parentColumn.nameInTable)
                    ?: throw StatementBuilderException("Cannot resolve foreign key for $tableToAdd"), it.column
            )
        }

        tableToAdd.getForeignKeyToParent(right)?.let {
            return Pair(
                right.columnByName(it.parentColumn.nameInTable)
                    ?: throw StatementBuilderException("Cannot resolve foreign key for $tableToAdd"), it.column
            )
        }
        return null
    }

    override fun toSQL(): String {
        pairs.forEach { (t, j) ->
            checkTableNotJoinedAlready(t)
            joins += createJoin(j, extractJoinedColumnPair(t))
        }
        return StringUtil.concat(listOf(table.toSQL(), JoinFactory.toSQL(joins.toList())))
    }

}
