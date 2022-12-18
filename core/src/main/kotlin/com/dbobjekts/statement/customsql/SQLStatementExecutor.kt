package com.dbobjekts.statement.customsql

import com.dbobjekts.AnyColumn
import com.dbobjekts.AnySqlParameter
import com.dbobjekts.SQL
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.ColumnFactory
import com.dbobjekts.result.ColumnInResultRow
import com.dbobjekts.result.ResultRow1
import com.dbobjekts.result.ResultRow
import com.dbobjekts.statement.SqlParameter
import com.dbobjekts.statement.StatementExecutor


open class SQLStatementExecutor<T, RSB : ResultRow<T>>(
    override val connection: ConnectionAdapter,
    internal val sql: SQL,
    internal val args: List<Any>,
    columnClasses: List<AnyColumn>,
    internal val selectResultSet: RSB
) : StatementExecutor {

    constructor(connection: ConnectionAdapter, sql: SQL, args: List<Any>) : this(connection, sql, args, listOf(ColumnFactory.LONG), ResultRow1<Long>() as RSB)

    internal val columnsToFetch: List<ColumnInResultRow> =
        columnClasses.mapIndexed { index, column -> ColumnInResultRow(1 + index, column)}

    fun first(): T = executeForSelect().first()

    fun firstOrNull(): T? = executeForSelect().firstOrNull()

    fun asList(): List<T> = executeForSelect().asList()

    fun forEachRow(mapper: (T) -> Boolean) {
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


    fun executeForSelect(): RSB =
        connection.prepareAndExecuteForSelect(sql, params, columnsToFetch, selectResultSet)

    fun execute(): Long = connection.prepareAndExecuteUpdate(sql, params)

}
