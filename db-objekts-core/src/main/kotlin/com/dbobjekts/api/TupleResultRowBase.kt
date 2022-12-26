package com.dbobjekts.api

import com.dbobjekts.jdbc.JDBCResultSetAdapter
import com.dbobjekts.statement.ColumnInResultRow
import java.sql.ResultSet

/**
 * Is returned upon successful execution of a query and allows access to the query results.
 */
abstract class ResultRow<out O> {

    protected lateinit var jdbcResultSetAdapter: JDBCResultSetAdapter
    private lateinit var rows: List<O>

    internal fun initialize(jdbcResultSetAdapter: JDBCResultSetAdapter) {
        this.jdbcResultSetAdapter = jdbcResultSetAdapter
    }

    internal fun retrieveAll() {
        rows = jdbcResultSetAdapter.retrieveAll(this)
    }

    internal fun extractValue(column: ColumnInResultRow, resultSet: ResultSet): Any? {
        return column.column.retrieveValue(column.position, resultSet, jdbcResultSetAdapter.useDefaultValuesInOUterJoins)
    }

    internal fun first(): O =
        if (rows.isEmpty())
            throw IllegalStateException("Expected exactly one row, but result set was empty.")
        else rows.get(0)

    internal fun firstOrNull(): O? = if (rows.isEmpty()) null else rows.get(0)

    internal fun asList(): List<O> = rows

    abstract fun extractRow(cols: List<ColumnInResultRow>, resultSet: ResultSet): O

    internal fun columns(): List<AnyColumn> = jdbcResultSetAdapter.resultSetColumns().map { it.column }

}
