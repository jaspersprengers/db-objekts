package com.dbobjekts.metadata.joins

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyTable
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.metadata.TableOrJoin
import com.dbobjekts.util.StringUtil

class TableJoinChain(val table: AnyTable) : TableOrJoin, Cloneable {

    private val joins: MutableList<JoinBase> = mutableListOf()

    internal fun addJoin(join: JoinBase): TableJoinChain {
        joins += join
        return this
    }

    fun leftJoin(table: AnyTable): TableJoinChain {
        checkTableNotJoinedAlready(table)
        addJoin(createLeftJoin(extractJoinedColumnPair(table)))
        return this
    }

    fun innerJoin(table: AnyTable): TableJoinChain {
        checkTableNotJoinedAlready(table)
        addJoin(createInnerJoin(extractJoinedColumnPair(table)))
        return this
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
            return Pair(it.column, tableToAdd.columnByName(it.parentColumn.nameInTable) ?: throw StatementBuilderException("Cannot resolve foreign key for $tableToAdd"))
        }

        right.getForeignKeyToParent(tableToAdd)?.let {
            return Pair(it.column, tableToAdd.columnByName(it.parentColumn.nameInTable) ?: throw StatementBuilderException("Cannot resolve foreign key for $tableToAdd"))
        }

        tableToAdd.getForeignKeyToParent(left)?.let {
            return Pair(left.columnByName(it.parentColumn.nameInTable) ?: throw StatementBuilderException("Cannot resolve foreign key for $tableToAdd"), it.column)
        }

        tableToAdd.getForeignKeyToParent(right)?.let {
            return Pair(right.columnByName(it.parentColumn.nameInTable) ?: throw StatementBuilderException("Cannot resolve foreign key for $tableToAdd"), it.column)
        }
        return null
    }

    private fun createLeftJoin(tuple: Pair<AnyColumn, AnyColumn>) = LeftJoin(tuple.first, tuple.second)

    private fun createInnerJoin(tuple: Pair<AnyColumn, AnyColumn>) = InnerJoin(tuple.first, tuple.second)

    fun toSQL(): String = StringUtil.concat(listOf(table.toSQL(), JoinFactory.toSQL(joins.toList())))

}
