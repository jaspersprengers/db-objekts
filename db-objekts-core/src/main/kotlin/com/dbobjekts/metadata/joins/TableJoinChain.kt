package com.dbobjekts.metadata.joins

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyTable
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.TableOrJoin
import com.dbobjekts.statement.ConditionJoinType
import com.dbobjekts.statement.whereclause.SubClause
import com.dbobjekts.util.StringUtil
import javax.swing.plaf.nimbus.State

open class TableJoinChain(
    val table: AnyTable,
    protected var joins: List<JoinBase>
) : TableOrJoin, Cloneable {

    constructor(joinChain: TableJoinChain) : this(joinChain.table, joinChain.joins)

    fun on(clause: SubClause): ManualJoinChainBuilder {
        if (joins.size != 1)
            throw StatementBuilderException("Illegal state")
        val j = joins[0]
        val toJoin = if (table == j.leftPart.table) j.rightPart.table else j.leftPart.table
        return when (j) {
            is LeftJoin -> ManualJoinChainBuilder(table).leftJoin(toJoin).on(clause)
            else -> throw IllegalStateException("Illegal state")
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : TableJoinChain> _join(table: AnyTable, joinType: JoinType): T {
        checkTableNotJoinedAlready(table)
        joins = joins + createJoin(joinType, extractJoinedColumnPair(table))
        return this as T
    }

    private fun createJoin(joinType: JoinType, tuple: Pair<AnyColumn, AnyColumn>) = when (joinType) {
        JoinType.LEFT -> LeftJoin(tuple.first, tuple.second)
        JoinType.RIGHT -> RightJoin(tuple.first, tuple.second)
        JoinType.INNER -> InnerJoin(tuple.first, tuple.second)
    }

    internal fun checkTableNotJoinedAlready(table: AnyTable) {
        if (joins.any { it.containsTable(table) })
            throw StatementBuilderException("Table ${table.tableName} is already present in join. You cannot add it again: ${toSQL()}")
    }

    internal fun lastJoinTable(): AnyTable = if (joins.isEmpty()) table else joins.last().rightPart.table

    internal fun hasJoins(): Boolean = !joins.isEmpty()

    private fun extractJoinedColumnPair(table: AnyTable): Pair<AnyColumn, AnyColumn> {
        val head =
            if (joins.isEmpty()) getJoinPair(lastJoinTable(), lastJoinTable(), table)
            else joins.reversed().map { getJoinPair(it.leftPart.table, it.rightPart.table, table) }.filterNotNull().firstOrNull()

        return if (head == null) throw StatementBuilderException("Cannot join ${table.toSQL()} to ${lastJoinTable().toSQL()}") else head
    }

    internal fun canJoin(table: AnyTable): Boolean {
        val head = if (joins.isEmpty()) getJoinPair(lastJoinTable(), lastJoinTable(), table)
        else joins.reversed().map { getJoinPair(it.leftPart.table, it.rightPart.table, table) }.filterNotNull().firstOrNull()
        return head != null
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

    fun toSQL(): String = StringUtil.concat(listOf(table.toSQL(), JoinFactory.toSQL(joins.toList())))

}
