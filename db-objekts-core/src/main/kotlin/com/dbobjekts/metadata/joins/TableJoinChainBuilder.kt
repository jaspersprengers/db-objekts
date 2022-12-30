package com.dbobjekts.metadata.joins

import com.dbobjekts.api.AnyForeignKey
import com.dbobjekts.api.AnyTable
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.util.StringUtil

class TableJoinChainBuilder(
    val catalog: Catalog,
    val drivingTable: AnyTable,
    val tables: List<AnyTable>,
    private val useOuterJoins: Boolean = false
) {

    init{
        require(tables.any { it == drivingTable }, {"Table ${drivingTable.tableName} should be part of List $tables"})
    }

    fun build(): TableJoinChain {

        fun extractJoins(tablePairs: List<Pair<AnyTable, AnyTable>>): Pair<List<TablePair>, List<TablePair>> =
            tablePairs.map { createPairWithOptionalJoin(it) }.partition { it.isJoined() }

        fun createProperties(tbls: List<AnyTable>): JoinProperties {
            val pairings: List<Pair<AnyTable, AnyTable>> = getTablePairings(tbls)
            val (joined, unjoined) = extractJoins(pairings)
            val joins = joined.map { it.joinOpt!! }
            return JoinProperties(tbls, joins, joined, unjoined)
        }

        if (tables.isEmpty())
            throw IllegalArgumentException("Need at least one table")
        if (tables.size == 1)
            return TableJoinChain(tables[0])

        val joinProperties: JoinProperties = createProperties(tables)
        val manyToManyTables =
            joinProperties.unJoinedPairs.map { findJoinTableForUnjoinedPair(it.pair) }.filterNotNull()

        val joinPropsWithMtoN = if (manyToManyTables.isNotEmpty()) createProperties(
            StringUtil.concatLists(
                tables,
                manyToManyTables
            )
        ) else joinProperties
        return buildChain(joinPropsWithMtoN)
    }

     fun getTablePairings(tbs: List<AnyTable>): List<Pair<AnyTable, AnyTable>> {
        val l = tbs.sortedBy { it.tableName.value }
        return l.flatMap { t -> l.slice(l.indexOf(t) + 1..l.size-1).map { Pair(t, it) } }
    }

     fun findJoin(pair: Pair<AnyTable, AnyTable>): ForeignKeyJoin? =
        listOf(createJoin(pair.first, pair.second), createJoin(pair.second, pair.first)).find { it != null }

    private fun createPairWithOptionalJoin(pair: Pair<AnyTable, AnyTable>): TablePair = TablePair(pair, findJoin(pair))

     fun findJoinTableForUnjoinedPair(pair: Pair<AnyTable, AnyTable>): AnyTable? {
        fun getForeignKeyFor(child: AnyTable, parent: AnyTable): AnyForeignKey? = child.foreignKeys.find { it.parentColumn.table == parent }

        if (pair.first == pair.second) {
            throw IllegalStateException("Pair elements must not refer to the same object")
        }
        return catalog.tables.map {Pair(getForeignKeyFor(it, pair.first), getForeignKeyFor(it, pair.second))}
          .find { it.first != null && it.second != null }?.first?.table
    }

     fun createJoin(parent: AnyTable, child: AnyTable): ForeignKeyJoin? =
        child.getForeignKeyToParent(parent)?.let {
            if (it.parentColumn.table == parent) ForeignKeyJoin(it.parentColumn, it) else null
        }


     fun buildChain(props: JoinProperties): TableJoinChain {
        require(props.tables.isNotEmpty(), {"No tables to build join query"})

        fun sort(sorted: List<AnyTable>, toSort: List<AnyTable>): List<AnyTable> {
            val maybeFound = sorted
                .flatMap { t -> toSort.map { Pair(t, it) } }
                .find { createJoin(it.first, it.second) != null || createJoin(it.second, it.first) != null}
            return maybeFound?.let {
                val m = it
                sort(StringUtil.concatLists(sorted, listOf(m.second)), toSort.filterNot { it == m.second })
            }?:sorted
        }

        val tablesToJoin = props.tables.filterNot {it == drivingTable}
        val sortedTables = sort(listOf(drivingTable), tablesToJoin.sortedBy { it.tableName.value })

        val sortedSet = sortedTables.toHashSet()
        val unUsed = tablesToJoin.filterNot { sortedSet.contains(it) }
        if (unUsed.isNotEmpty())
            throw IllegalStateException("The following table(s) could not be joined: ${StringUtil.joinBy(unUsed, {it.dbName})}")
        val chain = TableJoinChain(sortedTables.first())
        sortedTables.drop(1).forEach { if (useOuterJoins) chain.leftJoin(it) else chain.innerJoin(it)}
        return chain
    }

}



data class JoinProperties(
    val tables: List<AnyTable>,
    val joins: List<ForeignKeyJoin>,
    val joinedPairs: List<TablePair>,
    val unJoinedPairs: List<TablePair>
)


data class TablePair(val pair: Pair<AnyTable, AnyTable>, val joinOpt: ForeignKeyJoin?) {
    fun isJoined(): Boolean = joinOpt != null
}

