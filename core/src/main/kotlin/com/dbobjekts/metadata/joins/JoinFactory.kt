package com.dbobjekts.metadata.joins

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.util.StringUtil

object JoinFactory {

    fun createLeftJoin(left: AnyColumn, right: AnyColumn): LeftJoin = LeftJoin(left, right)

    fun createRightJoin(left: AnyColumn, right: AnyColumn): RightJoin = RightJoin(left, right)

    fun createInnerJoin(left: AnyColumn, right: AnyColumn): InnerJoin = InnerJoin(left, right)


    fun createLeftJoin(left: Table, right: Table): LeftJoin = extractJoinedColumnPair(left, right).let {
        createLeftJoin(it.first, it.second)
    }

    fun createRightJoin(left: Table, right: Table): RightJoin =
        extractJoinedColumnPair(left, right).let {
            createRightJoin(it.first, it.second)
        }


    fun createInnerJoin(left: Table, right: Table): InnerJoin =
        extractJoinedColumnPair(left, right).let {
            createInnerJoin(it.first, it.second)
        }


    private fun extractJoinedColumnPair(left: Table, right: Table): Pair<AnyColumn, AnyColumn> {
        //1: person left join hobby (person has optional FK to hobby)
        val fkToParent = left.getForeignKeyToParent(right)
        if ( fkToParent == null ){
            val fk = right.getForeignKeyToParent (left)
            if ( fk == null ) throw IllegalStateException("Could not establish foreign key relation between tables ${left.tableName} and ${right.tableName}")
            else return Pair(left.columnByName(fk.parentColumn.dbName)!!, fk.column)
        } else
            return Pair(fkToParent.column, right.columnByName(fkToParent.parentColumn.dbName)!!)

    }

    fun toSQL(vararg join: JoinBase): String = toSQL(join.asList())

    fun toSQL(joins: List<JoinBase>): String = StringUtil.concat(joins.flatMap {
        listOf(it.keyWord, it.conditionSQL)
    })


}


