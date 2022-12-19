package com.dbobjekts.api

import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.TableJoinChain
import com.dbobjekts.metadata.TableOrJoin
import com.dbobjekts.metadata.column.Column
import com.dbobjekts.result.*
import com.dbobjekts.statement.customsql.CustomSQLStatementBuilder
import com.dbobjekts.statement.customsql.SQLStatementExecutor
import com.dbobjekts.statement.delete.DeleteStatementExecutor
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.select.SelectStatementExecutor
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase
import java.lang.IllegalArgumentException

class Transaction(internal val connection: ConnectionAdapter){

    fun <U : UpdateBuilderBase> update(provider: HasUpdateBuilder<U, *>): U = provider.updater(connection)

    fun <I : InsertBuilderBase> insert(provider: HasUpdateBuilder<*, I>): I = provider.inserter(connection)

    fun deleteFrom(tableOrJoin: TableOrJoin): DeleteStatementExecutor {
        val statement = DeleteStatementExecutor(connection)
        return when (tableOrJoin) {
            is Table -> statement.withTable(tableOrJoin)
            is TableJoinChain -> statement.withJoinChain(tableOrJoin)
            else -> throw IllegalArgumentException("Illegal implementation of TableOrJoin provided. Must be Table or TableJoinChain")
        }
    }

    internal fun close() {
        connection.close()
    }

    internal fun connection() = connection.jdbcConnection

    internal fun isValid(): Boolean = connection.isValid()

    fun commit() = connection.commit()

    fun rollback() = connection.rollback()

    fun execute(sql: String, vararg args: Any): Long {
        return SQLStatementExecutor<Long?, ResultRow1<Long>>(connection, sql, args.toList()).execute()
    }

    fun sql(sql: String, vararg args: Any): CustomSQLStatementBuilder =
        CustomSQLStatementBuilder(connection, sql, args.toList())

    fun <I1> select(column1: Column<I1>): SelectStatementExecutor<I1, ResultRow1<I1>> =
        SelectStatementExecutor(connection, listOf(column1), ResultRow1<I1>())

    fun <T1, T2> select(
        c1: Column<T1>,
        c2: Column<T2>
    ): SelectStatementExecutor<Tuple2<T1, T2>, ResultRow2<T1, T2>> =
        SelectStatementExecutor(connection, listOf(c1, c2), ResultRow2<T1, T2>())

    fun <T1, T2, T3> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>
    ): SelectStatementExecutor<Tuple3<T1, T2, T3>, ResultRow3<T1, T2, T3>> =
        SelectStatementExecutor(connection, listOf(c1, c2, c3), ResultRow3<T1, T2, T3>())

    fun <T1, T2, T3, T4> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>
    ): SelectStatementExecutor<Tuple4<T1, T2, T3, T4>, ResultRow4<T1, T2, T3, T4>> =
        SelectStatementExecutor(connection, listOf(c1, c2, c3, c4), ResultRow4<T1, T2, T3, T4>())

    fun <T1, T2, T3, T4, T5> select(
        c1: Column<T1>, c2: Column<T2>, c3: Column<T3>, c4: Column<T4>, c5: Column<T5>
    ): SelectStatementExecutor<Tuple5<T1, T2, T3, T4, T5>, ResultRow5<T1, T2, T3, T4, T5>> =
        SelectStatementExecutor(connection, listOf(c1, c2, c3, c4, c5), ResultRow5<T1, T2, T3, T4, T5>())


    fun <T1, T2, T3, T4, T5, T6> select(
        c1: Column<T1>, c2: Column<T2>, c3: Column<T3>, c4: Column<T4>, c5: Column<T5>, c6: Column<T6>
    ): SelectStatementExecutor<Tuple6<T1, T2, T3, T4, T5, T6>, ResultRow6<T1, T2, T3, T4, T5, T6>> =
        SelectStatementExecutor(connection, listOf(c1, c2, c3, c4, c5, c6), ResultRow6<T1, T2, T3, T4, T5, T6>())

    override fun toString() = "Hashcode ${hashCode()} Thread-id: ${Thread.currentThread().id}"

}
