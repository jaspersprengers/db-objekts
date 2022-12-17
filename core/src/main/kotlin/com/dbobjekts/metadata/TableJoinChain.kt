package com.dbobjekts.metadata

import com.dbobjekts.AnyColumn
import com.dbobjekts.util.StringUtil

class TableJoinChain(val table: Table):TableOrJoin, Cloneable, SerializableToSQL {

    private val joins: MutableList<JoinBase> = mutableListOf()

    internal fun addJoin(join: JoinBase): TableJoinChain {
        joins += join
        return this
    }

    fun leftJoin(table: Table): TableJoinChain {
        checkTableNotJoinedAlready(table)
        addJoin(createLeftJoin(extractJoinedColumnPair(table)))
        return this
    }

    fun innerJoin(table: Table): TableJoinChain {
        checkTableNotJoinedAlready(table)
        addJoin(createInnerJoin(extractJoinedColumnPair(table)))
        return this
    }


    internal  fun checkTableNotJoinedAlready(table: Table) {
        if (joins.any { it.containsTable(table) })
            throw IllegalArgumentException("Table ${table.tableName} is already present in join. You cannot add it again: ${toSQL()}")
    }

    internal fun lastJoinTable(): Table = if (joins.isEmpty()) table else joins.last().rightPart.table

    private fun extractJoinedColumnPair(table: Table): Pair<AnyColumn, AnyColumn> {
        val head =
            if (joins.isEmpty()) getJoinPair(lastJoinTable(), lastJoinTable(), table)
              else joins.reversed().map { getJoinPair(it.leftPart.table, it.rightPart.table, table) }.filterNotNull().firstOrNull()

        return if (head == null) throw IllegalStateException("Cannot join ${table.toSQL()} to ${lastJoinTable().toSQL()}") else head
    }

    internal fun canJoin(table: Table): Boolean {
        val head = if (joins.isEmpty()) getJoinPair(lastJoinTable(), lastJoinTable(), table)
        else joins.reversed().map { getJoinPair(it.leftPart.table, it.rightPart.table, table) }.filterNotNull().firstOrNull()
        return head != null
    }

    private fun getJoinPair(left: Table, right: Table, tableToAdd: Table): Pair<AnyColumn, AnyColumn>? {

        left.getForeignKeyToParent(tableToAdd)?.let {
            return Pair(it.column, tableToAdd.columnByName(it.parentColumn.dbName)?:throw java.lang.IllegalStateException(""))
        }

        right.getForeignKeyToParent(tableToAdd)?.let {
            return Pair(it.column, tableToAdd.columnByName(it.parentColumn.dbName)?:throw java.lang.IllegalStateException(""))
        }

        tableToAdd.getForeignKeyToParent(left)?.let {
            return Pair(left.columnByName(it.parentColumn.dbName)?:throw java.lang.IllegalStateException(""), it.column)
        }

        tableToAdd.getForeignKeyToParent(right)?.let {
            return Pair(right.columnByName(it.parentColumn.dbName)?:throw java.lang.IllegalStateException(""), it.column)
        }

    /*   *//* val lrFK = left.getForeignKeyToParent(tableToAdd)
        if (lrFK != null) {
            val fk = lrFK
            return Pair(fk.column, tableToAdd.columnByName(fk.parentColumn.dbName)?:throw java.lang.IllegalStateException(""))
        }

        val lrFK2 = right.getForeignKeyToParent(tableToAdd)
        if (lrFK2 != null) {
            val fk = lrFK2
            return Pair(fk.column, tableToAdd.columnByName(fk.parentColumn.dbName)?:throw java.lang.IllegalStateException(""))
        }*//*

        val rlFK = tableToAdd.getForeignKeyToParent(left)
        if (rlFK != null) {
            val fk = rlFK
            return Pair(left.columnByName(fk.parentColumn.dbName)?:throw java.lang.IllegalStateException(""), fk.column)
        }
        val rlFK2 = tableToAdd.getForeignKeyToParent(right)
        if (rlFK2 != null) {
            val fk = rlFK2
            return Pair(right.columnByName(fk.parentColumn.dbName)?:throw java.lang.IllegalStateException(""), fk.column)
        }*/
        return null
    }

    private fun createLeftJoin(tuple: Pair<AnyColumn, AnyColumn>) = LeftJoin(tuple.first, tuple.second)

    private fun createInnerJoin(tuple: Pair<AnyColumn, AnyColumn>) = InnerJoin(tuple.first, tuple.second)

    override fun toSQL(): String = StringUtil.concat(listOf(table.toSQL(), JoinFactory.toSQL(joins.toList())))

}
