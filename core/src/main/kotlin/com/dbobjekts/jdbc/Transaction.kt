package com.dbobjekts.jdbc

import com.dbobjekts.*
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.TableJoinChain
import com.dbobjekts.metadata.column.Column
import com.dbobjekts.result.*
import com.dbobjekts.statement.customsql.SQLStatementExecutor
import com.dbobjekts.statement.delete.DeleteStatementExecutor
import com.dbobjekts.statement.select.SelectStatementExecutor
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

class Transaction(val connection: ConnectionAdapter) {

    fun <U : UpdateBuilderBase> update(provider: HasUpdateBuilder<U, *>): U = provider.updater(connection)

    fun <I : InsertBuilderBase> insert(provider: HasUpdateBuilder<*, I>): I = provider.inserter(connection)

    fun deleteFrom(table: Table): DeleteStatementExecutor = DeleteStatementExecutor(connection).withTable(table)

    fun deleteFrom(tableJoinChain: TableJoinChain): DeleteStatementExecutor =
        DeleteStatementExecutor(connection).withJoinChain(tableJoinChain)

    internal fun close() {
        connection.close()
    }

    fun isValid(): Boolean = connection.isValid()

    fun commit() = connection.commit()

    fun rollback() = connection.rollback()

    fun execute(sql: String, vararg args: Any): Long {
        return SQLStatementExecutor<Long?, ResultRow1<Long>>(connection, SQL(sql), args.toList()).execute()
    }

    fun <T> select(
        sql: String,
        c1: Column<T>,
        vararg args: Any
    ): SQLStatementExecutor<T, ResultRow1<T>> {
        val columns = listOf(c1)
        return SQLStatementExecutor(connection, SQL(sql), args.toList(), columns, ResultRow1<T>())
    }

    fun <T1, T2> select(
        sql: String,
        c1: Column<T1>,
        c2: Column<T2>,
        vararg args: Any
    ): SQLStatementExecutor<Pair<T1, T2>, ResultRow2<T1, T2>> {
        val columns = listOf(c1, c2)
        return SQLStatementExecutor(connection, SQL(sql), args.toList(), columns, ResultRow2<T1, T2>())
    }

    fun <T1, T2, T3> select(
        sql: String,
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        vararg args: Any
    ): SQLStatementExecutor<Triple<T1, T2, T3>, ResultRow3<T1, T2, T3>> {
        val columns = listOf(c1, c2, c3)
        return SQLStatementExecutor(connection, SQL(sql), args.toList(), columns, ResultRow3<T1, T2, T3>())
    }

    fun <T1, T2, T3, T4> select(
        sql: String,
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        vararg args: Any
    ): SQLStatementExecutor<Tuple4<T1, T2, T3, T4>, ResultRow4<T1, T2, T3, T4>> {
        val columns = listOf(c1, c2, c3, c4)
        return SQLStatementExecutor(connection, SQL(sql), args.toList(), columns, ResultRow4<T1, T2, T3, T4>())
    }

    fun <T1, T2, T3, T4, T5> select(
        sql: String,
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        c5: Column<T5>,
        vararg args: Any
    ): SQLStatementExecutor<Tuple5<T1, T2, T3, T4, T5>, ResultRow5<T1, T2, T3, T4, T5>> {
        val columns = listOf(c1, c2, c3, c4, c5)
        return SQLStatementExecutor(connection, SQL(sql), args.toList(), columns, ResultRow5<T1, T2, T3, T4, T5>())
    }


    fun <T1, T2, T3, T4, T5, T6> select(
        sql: String,
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        c5: Column<T5>,
        c6: Column<T6>,
        vararg args: Any
    ): SQLStatementExecutor<Tuple6<T1, T2, T3, T4, T5, T6>, ResultRow6<T1, T2, T3, T4, T5, T6>> {
        val columns = listOf(c1, c2, c3, c4, c5, c6)
        return SQLStatementExecutor(connection, SQL(sql), args.toList(), columns, ResultRow6<T1, T2, T3, T4, T5, T6>())
    }

    fun <T1, T2, T3, T4, T5, T6, T7> select(
        sql: String,
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        c5: Column<T5>,
        c6: Column<T6>,
        c7: Column<T7>,
        vararg args: Any
    ): SQLStatementExecutor<Tuple7<T1, T2, T3, T4, T5, T6, T7>, ResultRow7<T1, T2, T3, T4, T5, T6, T7>> {
        val columns = listOf(c1, c2, c3, c4, c5, c6, c7)
        return SQLStatementExecutor(connection, SQL(sql), args.toList(), columns, ResultRow7<T1, T2, T3, T4, T5, T6, T7>())
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8> select(
        sql: String,
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        c5: Column<T5>,
        c6: Column<T6>,
        c7: Column<T7>,
        c8: Column<T8>,
        vararg args: Any
    ): SQLStatementExecutor<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>, ResultRow8<T1, T2, T3, T4, T5, T6, T7, T8>> {
        val columns = listOf(c1, c2, c3, c4, c5, c6, c7, c8)
        return SQLStatementExecutor(connection, SQL(sql), args.toList(), columns, ResultRow8<T1, T2, T3, T4, T5, T6, T7, T8>())
    }

    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9> select(
        sql: String,
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        c5: Column<T5>,
        c6: Column<T6>,
        c7: Column<T7>,
        c8: Column<T8>,
        c9: Column<T9>,
        vararg args: Any
    ): SQLStatementExecutor<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>, ResultRow9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> {
        val columns = listOf(c1, c2, c3, c4, c5, c6, c7, c8, c9)
        return SQLStatementExecutor(connection, SQL(sql), args.toList(), columns, ResultRow9<T1, T2, T3, T4, T5, T6, T7, T8, T9>())
    }


    fun <I1> select(column1: Column<I1>): SelectStatementExecutor<I1, ResultRow1<I1>> =
        SelectStatementExecutor(connection, listOf(column1), ResultRow1<I1>())

    fun <T1, T2> select(
        c1: Column<T1>,
        c2: Column<T2>
    ): SelectStatementExecutor<Pair<T1, T2>, ResultRow2<T1, T2>> =
        SelectStatementExecutor(connection, listOf(c1, c2), ResultRow2<T1, T2>())

    fun <T1, T2, T3> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>
    ): SelectStatementExecutor<Triple<T1, T2, T3>, ResultRow3<T1, T2, T3>> =
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
