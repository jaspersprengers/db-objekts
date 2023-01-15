package com.dbobjekts.metadata.joins

import com.dbobjekts.api.AnyTable
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.IsForeignKey
import com.dbobjekts.statement.whereclause.SubClause
import com.dbobjekts.util.StringUtil

class TableJoinChainBuilder(
    val catalog: Catalog,
    val drivingTable: AnyTable,
    val tables: List<AnyTable>,
    private val useOuterJoins: Boolean = false
) {

    init {
        require(tables.any { it == drivingTable }, { "Table ${drivingTable.tableName} should be part of List $tables" })
    }

    fun build(): TableJoinChain {

        fun extractJoins(tablePairs: List<Pair<AnyTable, AnyTable>>): Pair<List<TablePair>, List<TablePair>> =
            tablePairs.map { createPairWithOptionalJoin(it) }.partition { it.isJoined() }

        fun createProperties(tbls: List<AnyTable>): JoinProperties {
            val pairings: List<Pair<AnyTable, AnyTable>> = getTablePairings(tbls)
            val (joined, unjoined) = extractJoins(pairings)
            return JoinProperties(tbls, joined, unjoined)
        }

        if (tables.isEmpty())
            throw StatementBuilderException("Need at least one table")
        if (tables.size == 1)
            return TableJoinChain(tables[0], listOf())

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
        return l.flatMap { t -> l.slice(l.indexOf(t) + 1..l.size - 1).map { Pair(t, it) } }
    }

    internal fun findJoin(pair: Pair<AnyTable, AnyTable>): Boolean =
        areJoined(pair.first, pair.second) && areJoined(pair.second, pair.first)

    private fun createPairWithOptionalJoin(pair: Pair<AnyTable, AnyTable>): TablePair = TablePair(pair, findJoin(pair))

    internal fun findJoinTableForUnjoinedPair(pair: Pair<AnyTable, AnyTable>): AnyTable? {
        fun getForeignKeyFor(child: AnyTable, parent: AnyTable): IsForeignKey<*, *>? =
            child.foreignKeys.find { it.parentColumn.table == parent }

        if (pair.first == pair.second) {
            throw StatementBuilderException("Pair elements must not refer to the same object")
        }
        return catalog.tables.map { Pair(getForeignKeyFor(it, pair.first), getForeignKeyFor(it, pair.second)) }
            .find { it.first != null && it.second != null }?.first?.column?.table
    }

    internal fun areJoined(parent: AnyTable, child: AnyTable): Boolean =
        child.getForeignKeysToParent(parent).filter {
            it.parentColumn.table == parent
        }.isNotEmpty()


    internal fun buildChain(props: JoinProperties): TableJoinChain {
        require(props.tables.isNotEmpty(), { "No tables to build join query" })

        fun sort(sorted: List<AnyTable>, toSort: List<AnyTable>): List<AnyTable> {
            val maybeFound = sorted
                .flatMap { t -> toSort.map { Pair(t, it) } }
                .find { areJoined(it.first, it.second)  || areJoined(it.second, it.first) }
            return maybeFound?.let {
                val m = it
                sort(StringUtil.concatLists(sorted, listOf(m.second)), toSort.filterNot { it == m.second })
            } ?: sorted
        }

        val tablesToJoin = props.tables.filterNot { it == drivingTable }
        val sortedTables: List<Table<*>> = sort(listOf(drivingTable), tablesToJoin.sortedBy { it.tableName.value })

        val sortedSet = sortedTables.toHashSet()
        val unUsed = tablesToJoin.filterNot { sortedSet.contains(it) }
        if (unUsed.isNotEmpty())
            throw StatementBuilderException("The following table(s) could not be joined: ${StringUtil.joinBy(unUsed, { it.dbName })}")
        var chain = TableJoinChain(sortedTables.first(), listOf())
        sortedTables.drop(1).forEach { chain = if (useOuterJoins) chain._join(it, JoinType.LEFT) else chain._join(it, JoinType.INNER) }
        return chain
    }

}


internal data class JoinProperties(
    val tables: List<AnyTable>,
    val joinedPairs: List<TablePair>,
    val unJoinedPairs: List<TablePair>
)


internal data class TablePair(val pair: Pair<AnyTable, AnyTable>, val joinOpt: Boolean) {
    fun isJoined(): Boolean = joinOpt
}

