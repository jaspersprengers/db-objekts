package com.dbobjekts.api

import com.dbobjekts.*
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.TableJoinChain
import com.dbobjekts.metadata.column.Column
import com.dbobjekts.result.*
import com.dbobjekts.statement.customsql.CustomSQLStatementBuilder
import com.dbobjekts.statement.delete.DeleteStatementExecutor
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.select.SelectStatementExecutor
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase


interface Transaction {

    //fun connection(): ConnectionAdapter

    fun <U : UpdateBuilderBase> update(provider: HasUpdateBuilder<U, *>): U

    fun <I : InsertBuilderBase> insert(provider: HasUpdateBuilder<*, I>): I

    fun deleteFrom(table: Table): DeleteStatementExecutor

    fun deleteFrom(tableJoinChain: TableJoinChain): DeleteStatementExecutor

    fun isValid(): Boolean

    fun connection(): ConnectionAdapter

    fun commit()

    fun close()

    fun rollback()

    fun execute(sql: String, vararg args: Any): Long

    fun select(sql: String, vararg args: Any): CustomSQLStatementBuilder

    fun <I1> select(column1: Column<I1>): SelectStatementExecutor<I1, ResultRow1<I1>>

    fun <T1, T2> select(
        c1: Column<T1>,
        c2: Column<T2>
    ): SelectStatementExecutor<Tuple2<T1, T2>, ResultRow2<T1, T2>>

    fun <T1, T2, T3> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>
    ): SelectStatementExecutor<Tuple3<T1, T2, T3>, ResultRow3<T1, T2, T3>>

    fun <T1, T2, T3, T4> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>
    ): SelectStatementExecutor<Tuple4<T1, T2, T3, T4>, ResultRow4<T1, T2, T3, T4>>

    fun <T1, T2, T3, T4, T5> select(
        c1: Column<T1>, c2: Column<T2>, c3: Column<T3>, c4: Column<T4>, c5: Column<T5>
    ): SelectStatementExecutor<Tuple5<T1, T2, T3, T4, T5>, ResultRow5<T1, T2, T3, T4, T5>>


    fun <T1, T2, T3, T4, T5, T6> select(
        c1: Column<T1>, c2: Column<T2>, c3: Column<T3>, c4: Column<T4>, c5: Column<T5>, c6: Column<T6>
    ): SelectStatementExecutor<Tuple6<T1, T2, T3, T4, T5, T6>, ResultRow6<T1, T2, T3, T4, T5, T6>>

}
