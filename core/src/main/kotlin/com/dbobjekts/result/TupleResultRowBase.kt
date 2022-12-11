package com.dbobjekts.result

import com.dbobjekts.AnyColumn
import com.dbobjekts.jdbc.JDBCResultSetAdapter
import java.sql.ResultSet

/**
 * Is returned upon successful execution of a query and allows access to the query results.
 */
abstract class ResultRow<out O> {

    protected lateinit var jdbcResultSetAdapter: JDBCResultSetAdapter
    private lateinit var rows: List<O>

    fun initialize(jdbcResultSetAdapter: JDBCResultSetAdapter) {
        this.jdbcResultSetAdapter = jdbcResultSetAdapter
    }

    fun retrieveAll() {
        rows = jdbcResultSetAdapter.retrieveAll(this)
    }

     fun extractValue(column: ColumnInResultRow, resultSet: ResultSet): Any? {
        return column.column.retrieveValue(column.position, resultSet)
    }

    fun first(): O =
        if (rows.isEmpty())
            throw IllegalStateException("Expected exactly one row, but result set was empty.")
        else rows.get(0)

    fun firstOrNull(): O? = if (rows.isEmpty()) null else rows.get(0)

    fun asList(): List<O> = rows

    abstract fun extractRow(cols: List<ColumnInResultRow>, resultSet: ResultSet): O

     fun columns(): List<AnyColumn> = jdbcResultSetAdapter.resultSetColumns().map { it.column }

}
