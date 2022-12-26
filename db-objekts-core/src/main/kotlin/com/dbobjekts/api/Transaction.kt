package com.dbobjekts.api

import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.TableOrJoin
import com.dbobjekts.metadata.joins.TableJoinChain
import com.dbobjekts.metadata.column.Column
import com.dbobjekts.statement.customsql.CustomSQLStatementBuilder
import com.dbobjekts.statement.customsql.SQLStatementExecutor
import com.dbobjekts.statement.delete.DeleteStatementExecutor
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.select.SelectStatementExecutor
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase
import java.lang.IllegalArgumentException

/**
 * A short-lived object that wraps a live [java.sql.Connection] to a database on which you perform queries that are committed by the [TransactionManager].
 *
 * It is important that a [Transaction] instance is not used outside the scope of the lambda passed to [TransactionManager.newTransaction]. So you should stick to this usage scenario:
 * ```kotlin
 * manager.newTransaction { transaction ->
 *   // the underlying Connection will be committed or rolled back when the lambda throws an Exception
 * }
 * ```
 */
class Transaction(internal val connection: ConnectionAdapter) {

    internal val semaphore: Semaphore = Semaphore()

    /**
     * Starts a new update statement. Example:
     * ```kotlin
     * transaction.update(Author)
     *   .bio("(1903-1950) Influential English author and journalist.")
     *   .where(Author.id.eq(rowId))
     * ```
     * @param table a [Table] metadata object
     * @return a statement builder object to set the columns for update
     */
    fun <U : UpdateBuilderBase> update(table: HasUpdateBuilder<U, *>): U {
        val updater = table.metadata().updater
        updater.connection = connection
        semaphore.claim("update")
        updater.semaphore = semaphore
        return updater
    }

    /**
     * Starts a new insert statement. Example:
     * ```kotlin
     *   val idForNewRow: Long = transaction.insert(Author)
     *   .mandatoryColumns("George Orwell")
     *   .bio("Pseudonym of Eric Blair (1903-1950)").execute()
     * ```
     * @param a [Table] metadata object
     * @return a statement builder to set the fields to insert
     */
    fun <I : InsertBuilderBase> insert(table: HasUpdateBuilder<*, I>): I {
        val inserter: I = table.metadata().inserter
        inserter.connection = connection
        semaphore.claim("update")
        inserter.semaphore = semaphore
        return inserter
    }

    /**
     * Starts a delete statement. Example:
     * ```kotlin
     *  transaction.deleteFrom(Book).where(Book.isbn.eq("ISBN"))
     * ```
     * @param a [Table] or join chain, e.g. Item.leftJoin(Loan)
     * @param a statement to set the where clause
     */
    fun deleteFrom(tableOrJoin: TableOrJoin): DeleteStatementExecutor {
        val statement = DeleteStatementExecutor(semaphore, connection)
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

    /**
     * Returns log data of all statements executed during the transactions life cycle up to this point.
     *
     * Used for debugging purposes: data is no longer available when the transaction goes out of scope. This is deliberate, as the log data contains actual data written and retrieved, which poses a security risk when written to persistent logs.
     * @return an [ExecutedStatementInfo] object for each executed statement
     */
    fun transactionExecutionLog(): List<ExecutedStatementInfo> = connection.statementLog.transactionExecutionLog()

    /**
     * Commits the underlying [java.sql.Connection]. Use with caution. There is usually little reason to invoke this manually, as it happens automatically when the [Transaction] goes out of scope.
     */
    fun commit() = connection.commit()

    /**
     * Rolls back the underlying [java.sql.Connection]. This happens automatically when an exception is thrown, so there is usually no reason to do this manually.
     */
    fun rollback() = connection.rollback()

    /**
     * Executes a native sql query that does not return data. Example:
     * ```kotlin
     *  val result: Long = transaction.execute("delete from book b where b.id = ?", 42)
     * ```
     * @param sql a native sql query, highly specific to the underlying vendor
     * @param any number of placeholders, identified with '?' in the query
     * @return the result of the underlying call to [java.sql.PreparedStatement.executeUpdate]
     */
    fun execute(sql: String, vararg args: Any): Long {
        return SQLStatementExecutor<Long?, ResultRow1<Long>>(semaphore, connection, sql, args.toList()).execute()
            .also { connection.statementLog.logResult(it) }
    }

    /**
     * Starts a native sql query that expects a result and returns a builder object to specify the types of the expected columns. Example:
     * ```kotlin
     *  val rows: Tuple6<Long, String, Double, Boolean?, Int?, String?> =
     *    transaction.sql("select e.id,e.name,e.salary,e.married, e.children, h.NAME from core.employee e join hr.HOBBY h on h.ID = e.HOBBY_ID where e.name = ?",
     *       "John")
     *     .withResultTypes().long().string().double().booleanNil().intNil().stringNil()
     *     .first()
     * ```
     * @param sql a native sql query, highly specific to the underlying vendor
     * @param any number of placeholders, identified with '?' in the query
     * @return a [CustomSQLStatementBuilder] builder, used to provide the column types you expect
     */
    fun sql(sql: String, vararg args: Any): CustomSQLStatementBuilder =
        CustomSQLStatementBuilder(semaphore, connection, sql, args.toList(), statementLog = connection.statementLog)

    /**
     * Starts a select statement that expects a single column. Example:
     * ```kotlin
     *  transaction.select(Book.title).asList()
     * ```
     */
    fun <I1> select(column1: Column<I1>): SelectStatementExecutor<I1, ResultRow1<I1>> =
        SelectStatementExecutor(semaphore, connection, listOf(column1), ResultRow1<I1>())

    /**
     * Creates a parameterized select statement for 1 to 22 [Column] references, returning type-safe results when the results are fetched
     */
    fun <T1, T2> select(c1: Column<T1>, c2: Column<T2>): SelectStatementExecutor<Tuple2<T1, T2>, ResultRow2<T1, T2>> =
        SelectStatementExecutor(semaphore, connection, listOf(c1, c2), ResultRow2<T1, T2>())

    /**
     * Creates a parameterized select statement for 1 to 22 [Column] references, returning type-safe results when the results are fetched
     */
    fun <T1, T2, T3> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>
    ): SelectStatementExecutor<Tuple3<T1, T2, T3>, ResultRow3<T1, T2, T3>> =
        SelectStatementExecutor(semaphore, connection, listOf(c1, c2, c3), ResultRow3<T1, T2, T3>())

    /**
     * Creates a parameterized select statement for 1 to 22 [Column] references, returning type-safe results when the results are fetched
     */
    fun <T1, T2, T3, T4> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>
    ): SelectStatementExecutor<Tuple4<T1, T2, T3, T4>, ResultRow4<T1, T2, T3, T4>> =
        SelectStatementExecutor(semaphore, connection, listOf(c1, c2, c3, c4), ResultRow4<T1, T2, T3, T4>())

    /**
     * Creates a parameterized select statement for 1 to 22 [Column] references, returning type-safe results when the results are fetched
     */
    fun <T1, T2, T3, T4, T5> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        c5: Column<T5>
    ): SelectStatementExecutor<Tuple5<T1, T2, T3, T4, T5>, ResultRow5<T1, T2, T3, T4, T5>> =
        SelectStatementExecutor(semaphore, connection, listOf(c1, c2, c3, c4, c5), ResultRow5<T1, T2, T3, T4, T5>())

    /**
     * Creates a parameterized select statement for 1 to 22 [Column] references, returning type-safe results when the results are fetched
     */
    fun <T1, T2, T3, T4, T5, T6> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        c5: Column<T5>,
        c6: Column<T6>
    ): SelectStatementExecutor<Tuple6<T1, T2, T3, T4, T5, T6>, ResultRow6<T1, T2, T3, T4, T5, T6>> =
        SelectStatementExecutor(semaphore, connection, listOf(c1, c2, c3, c4, c5, c6), ResultRow6<T1, T2, T3, T4, T5, T6>())

    /**
     * Creates a parameterized select statement for 1 to 22 [Column] references, returning type-safe results when the results are fetched
     */
    fun <T1, T2, T3, T4, T5, T6, T7> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        c5: Column<T5>,
        c6: Column<T6>,
        c7: Column<T7>
    ): SelectStatementExecutor<Tuple7<T1, T2, T3, T4, T5, T6, T7>, ResultRow7<T1, T2, T3, T4, T5, T6, T7>> =
        SelectStatementExecutor(semaphore, connection, listOf(c1, c2, c3, c4, c5, c6, c7), ResultRow7<T1, T2, T3, T4, T5, T6, T7>())

    /**
     * Creates a parameterized select statement for 1 to 22 [Column] references, returning type-safe results when the results are fetched
     */
    fun <T1, T2, T3, T4, T5, T6, T7, T8> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        c5: Column<T5>,
        c6: Column<T6>,
        c7: Column<T7>,
        c8: Column<T8>
    ): SelectStatementExecutor<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>, ResultRow8<T1, T2, T3, T4, T5, T6, T7, T8>> =
        SelectStatementExecutor(semaphore, connection, listOf(c1, c2, c3, c4, c5, c6, c7, c8), ResultRow8<T1, T2, T3, T4, T5, T6, T7, T8>())

    /**
     * Creates a parameterized select statement for 1 to 22 [Column] references, returning type-safe results when the results are fetched
     */
    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        c5: Column<T5>,
        c6: Column<T6>,
        c7: Column<T7>,
        c8: Column<T8>,
        c9: Column<T9>
    ): SelectStatementExecutor<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>, ResultRow9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> =
        SelectStatementExecutor(
            semaphore,
            connection,
            listOf(c1, c2, c3, c4, c5, c6, c7, c8, c9),
            ResultRow9<T1, T2, T3, T4, T5, T6, T7, T8, T9>()
        )

    /**
     * Creates a parameterized select statement for 1 to 22 [Column] references, returning type-safe results when the results are fetched
     */
    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        c5: Column<T5>,
        c6: Column<T6>,
        c7: Column<T7>,
        c8: Column<T8>,
        c9: Column<T9>,
        c10: Column<T10>
    ): SelectStatementExecutor<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, ResultRow10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> =
        SelectStatementExecutor(
            semaphore,
            connection,
            listOf(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10),
            ResultRow10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>()
        )

    /**
     * Creates a parameterized select statement for 1 to 22 [Column] references, returning type-safe results when the results are fetched
     */
    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        c5: Column<T5>,
        c6: Column<T6>,
        c7: Column<T7>,
        c8: Column<T8>,
        c9: Column<T9>,
        c10: Column<T10>,
        c11: Column<T11>
    ): SelectStatementExecutor<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>, ResultRow11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> =
        SelectStatementExecutor(
            semaphore,
            connection,
            listOf(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11),
            ResultRow11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>()
        )

    /**
     * Creates a parameterized select statement for 1 to 22 [Column] references, returning type-safe results when the results are fetched
     */
    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        c5: Column<T5>,
        c6: Column<T6>,
        c7: Column<T7>,
        c8: Column<T8>,
        c9: Column<T9>,
        c10: Column<T10>,
        c11: Column<T11>,
        c12: Column<T12>
    ): SelectStatementExecutor<Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>, ResultRow12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> =
        SelectStatementExecutor(
            semaphore,
            connection,
            listOf(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12),
            ResultRow12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>()
        )

    /**
     * Creates a parameterized select statement for 1 to 22 [Column] references, returning type-safe results when the results are fetched
     */
    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        c5: Column<T5>,
        c6: Column<T6>,
        c7: Column<T7>,
        c8: Column<T8>,
        c9: Column<T9>,
        c10: Column<T10>,
        c11: Column<T11>,
        c12: Column<T12>,
        c13: Column<T13>
    ): SelectStatementExecutor<Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>, ResultRow13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> =
        SelectStatementExecutor(
            semaphore,
            connection,
            listOf(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13),
            ResultRow13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>()
        )

    /**
     * Creates a parameterized select statement for 1 to 22 [Column] references, returning type-safe results when the results are fetched
     */
    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        c5: Column<T5>,
        c6: Column<T6>,
        c7: Column<T7>,
        c8: Column<T8>,
        c9: Column<T9>,
        c10: Column<T10>,
        c11: Column<T11>,
        c12: Column<T12>,
        c13: Column<T13>,
        c14: Column<T14>
    ): SelectStatementExecutor<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>, ResultRow14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> =
        SelectStatementExecutor(
            semaphore,
            connection,
            listOf(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14),
            ResultRow14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>()
        )

    /**
     * Creates a parameterized select statement for 1 to 22 [Column] references, returning type-safe results when the results are fetched
     */
    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        c5: Column<T5>,
        c6: Column<T6>,
        c7: Column<T7>,
        c8: Column<T8>,
        c9: Column<T9>,
        c10: Column<T10>,
        c11: Column<T11>,
        c12: Column<T12>,
        c13: Column<T13>,
        c14: Column<T14>,
        c15: Column<T15>
    ): SelectStatementExecutor<Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>, ResultRow15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> =
        SelectStatementExecutor(
            semaphore,
            connection,
            listOf(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15),
            ResultRow15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>()
        )

    /**
     * Creates a parameterized select statement for 1 to 22 [Column] references, returning type-safe results when the results are fetched
     */
    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        c5: Column<T5>,
        c6: Column<T6>,
        c7: Column<T7>,
        c8: Column<T8>,
        c9: Column<T9>,
        c10: Column<T10>,
        c11: Column<T11>,
        c12: Column<T12>,
        c13: Column<T13>,
        c14: Column<T14>,
        c15: Column<T15>,
        c16: Column<T16>
    ): SelectStatementExecutor<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>, ResultRow16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> =
        SelectStatementExecutor(
            semaphore,
            connection,
            listOf(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16),
            ResultRow16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>()
        )

    /**
     * Creates a parameterized select statement for 1 to 22 [Column] references, returning type-safe results when the results are fetched
     */
    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        c5: Column<T5>,
        c6: Column<T6>,
        c7: Column<T7>,
        c8: Column<T8>,
        c9: Column<T9>,
        c10: Column<T10>,
        c11: Column<T11>,
        c12: Column<T12>,
        c13: Column<T13>,
        c14: Column<T14>,
        c15: Column<T15>,
        c16: Column<T16>,
        c17: Column<T17>
    ): SelectStatementExecutor<Tuple17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>, ResultRow17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> =
        SelectStatementExecutor(
            semaphore,
            connection,
            listOf(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17),
            ResultRow17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>()
        )

    /**
     * Creates a parameterized select statement for 1 to 22 [Column] references, returning type-safe results when the results are fetched
     */
    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        c5: Column<T5>,
        c6: Column<T6>,
        c7: Column<T7>,
        c8: Column<T8>,
        c9: Column<T9>,
        c10: Column<T10>,
        c11: Column<T11>,
        c12: Column<T12>,
        c13: Column<T13>,
        c14: Column<T14>,
        c15: Column<T15>,
        c16: Column<T16>,
        c17: Column<T17>,
        c18: Column<T18>
    ): SelectStatementExecutor<Tuple18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>, ResultRow18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> =
        SelectStatementExecutor(
            semaphore,
            connection,
            listOf(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18),
            ResultRow18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>()
        )

    /**
     * Creates a parameterized select statement for 1 to 22 [Column] references, returning type-safe results when the results are fetched
     */
    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        c5: Column<T5>,
        c6: Column<T6>,
        c7: Column<T7>,
        c8: Column<T8>,
        c9: Column<T9>,
        c10: Column<T10>,
        c11: Column<T11>,
        c12: Column<T12>,
        c13: Column<T13>,
        c14: Column<T14>,
        c15: Column<T15>,
        c16: Column<T16>,
        c17: Column<T17>,
        c18: Column<T18>,
        c19: Column<T19>
    ): SelectStatementExecutor<Tuple19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>, ResultRow19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> =
        SelectStatementExecutor(
            semaphore,
            connection,
            listOf(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18, c19),
            ResultRow19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>()
        )

    /**
     * Creates a parameterized select statement for 1 to 22 [Column] references, returning type-safe results when the results are fetched
     */
    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        c5: Column<T5>,
        c6: Column<T6>,
        c7: Column<T7>,
        c8: Column<T8>,
        c9: Column<T9>,
        c10: Column<T10>,
        c11: Column<T11>,
        c12: Column<T12>,
        c13: Column<T13>,
        c14: Column<T14>,
        c15: Column<T15>,
        c16: Column<T16>,
        c17: Column<T17>,
        c18: Column<T18>,
        c19: Column<T19>,
        c20: Column<T20>
    ): SelectStatementExecutor<Tuple20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>, ResultRow20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> =
        SelectStatementExecutor(
            semaphore,
            connection,
            listOf(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18, c19, c20),
            ResultRow20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>()
        )

    /**
     * Creates a parameterized select statement for 1 to 22 [Column] references, returning type-safe results when the results are fetched
     */
    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        c5: Column<T5>,
        c6: Column<T6>,
        c7: Column<T7>,
        c8: Column<T8>,
        c9: Column<T9>,
        c10: Column<T10>,
        c11: Column<T11>,
        c12: Column<T12>,
        c13: Column<T13>,
        c14: Column<T14>,
        c15: Column<T15>,
        c16: Column<T16>,
        c17: Column<T17>,
        c18: Column<T18>,
        c19: Column<T19>,
        c20: Column<T20>,
        c21: Column<T21>
    ): SelectStatementExecutor<Tuple21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>, ResultRow21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> =
        SelectStatementExecutor(
            semaphore,
            connection,
            listOf(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18, c19, c20, c21),
            ResultRow21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>()
        )

    /**
     * Creates a parameterized select statement for 1 to 22 [Column] references, returning type-safe results when the results are fetched
     */
    fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> select(
        c1: Column<T1>,
        c2: Column<T2>,
        c3: Column<T3>,
        c4: Column<T4>,
        c5: Column<T5>,
        c6: Column<T6>,
        c7: Column<T7>,
        c8: Column<T8>,
        c9: Column<T9>,
        c10: Column<T10>,
        c11: Column<T11>,
        c12: Column<T12>,
        c13: Column<T13>,
        c14: Column<T14>,
        c15: Column<T15>,
        c16: Column<T16>,
        c17: Column<T17>,
        c18: Column<T18>,
        c19: Column<T19>,
        c20: Column<T20>,
        c21: Column<T21>,
        c22: Column<T22>
    ): SelectStatementExecutor<Tuple22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>, ResultRow22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> =
        SelectStatementExecutor(
            semaphore,
            connection,
            listOf(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18, c19, c20, c21, c22),
            ResultRow22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>()
        )

    override fun toString() = "Hashcode ${hashCode()} Thread-id: ${Thread.currentThread().id}"

}