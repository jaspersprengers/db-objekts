package com.dbobjekts.statement.customsql

import com.dbobjekts.api.*
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.ColumnFactory
import com.dbobjekts.statement.ColumnInResultRow
import com.dbobjekts.statement.SqlParameter
import com.dbobjekts.statement.StatementExecutor

open class SQLStatementExecutor<T, RSB : ResultRow<T>>(
    private val semaphore: Semaphore,
    override val connection: ConnectionAdapter,
    internal val sql: String,
    internal val args: List<Any>,
    columnClasses: List<AnyColumn>,
    internal val selectResultSet: RSB
) : StatementExecutor {

    @Suppress("UNCHECKED_CAST")
    constructor(semaphore: Semaphore, connection: ConnectionAdapter, sql: String, args: List<Any>) : this(
        semaphore,
        connection,
        sql,
        args,
        listOf(ColumnFactory.LONG),
        ResultRow1<Long>() as RSB
    )

    internal val columnsToFetch: List<ColumnInResultRow> =
        columnClasses.mapIndexed { index, column -> ColumnInResultRow(1 + index, column) }

    init {
        selectResultSet.selectables = columnClasses
    }

    /**
     * Executes the select statement, fetches all rows and returns the first result.
     * For better performance, use this only when you expect a single result, or use the [limit] clause in addition.
     * @throws IllegalStateException if there are no results. To prevent this, use [firstOrNull]
     */
    fun first(): T = executeForSelect().first().also { semaphore.clear() }

    /**
     * Executes the select statement, fetches all rows and returns the first result, or null if there is no match.
     * For better performance, use this only when you expect a single result, or use the [limit] clause in addition.
     */
    fun firstOrNull(): T? = executeForSelect().firstOrNull().also { semaphore.clear() }

    /**
     * Executes the select statement, fetches all rows and returns them as a list of tuples
     */
    fun asList(): List<T> = executeForSelect().asList().also { semaphore.clear() }

    /**
     * Executes the select query and lets you step through the results with a custom function that receives the current row data
     * and returns a Boolean to indicate whether to proceed or not. Example:
     * ```kotlin
     * transaction.sql("select").withResultTypes().long().string()
     *     .forEachRow { row: Tuple2<Long, String> ->
     *          buffer.add(row)
     *          !buffer.memoryFull() }
     *  ```
     *
     * This can be useful for huge result sets that would run into memory problems when fetched at once into a list.
     */
    fun forEachRow(mapper: (T) -> Boolean) {
        semaphore.clear()
        connection.prepareAndExecuteForSelectWithRowIterator<T, RSB>(
            sql,
            params,
            columnsToFetch,
            selectResultSet,
            mapper
        )
    }

    val params: List<AnySqlParameter> by lazy {
        if (args.isNotEmpty()) {
            val list = when (val first = args.first()) {
                is List<*> -> first
                else -> args
            }
            list.map { createParameter(1 + list.indexOf(it), it) }
        } else listOf()
    }


    private fun <T> createParameter(position: Int, value: T): SqlParameter<T> =
        SqlParameter<T>(position, ColumnFactory.getColumnForValue(value), value)


    private fun executeForSelect(): RSB {
        connection.statementLog.logStatement(sql, params)
        return connection.prepareAndExecuteForSelect(sql, params, columnsToFetch, selectResultSet)
    }


    internal fun execute(): Long {
        connection.statementLog.logStatement(sql, params)
        return connection.prepareAndExecuteUpdate(sql, params)
    }

}

