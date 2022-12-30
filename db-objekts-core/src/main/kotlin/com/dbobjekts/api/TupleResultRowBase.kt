package com.dbobjekts.api

import com.dbobjekts.jdbc.JDBCResultSetAdapter
import com.dbobjekts.metadata.Selectable
import com.dbobjekts.statement.ColumnInResultRow
import java.sql.ResultSet

/**
 * Is returned upon successful execution of a query and allows access to the query results.
 */
abstract class ResultRow<out O> {

    protected lateinit var jdbcResultSetAdapter: JDBCResultSetAdapter
    private lateinit var rows: List<O>
    internal lateinit var selectables: List<Selectable<*>>

    internal fun initialize(jdbcResultSetAdapter: JDBCResultSetAdapter) {
        this.jdbcResultSetAdapter = jdbcResultSetAdapter
    }

    internal fun retrieveAll() {
        rows = jdbcResultSetAdapter.retrieveAll(this)
    }

    internal fun extractValue(column: ColumnInResultRow, resultSet: ResultSet): Any? {
        return column.column.retrieveValue(column.position, resultSet)
    }
    protected fun extractRow_2(cols: List<ColumnInResultRow>, resultSet: ResultSet): List<Any?>{
        var index = 0
        val values = mutableListOf<Any?>()
        for (s in selectables){
            val retrieved: List<Any?> = s.columns.mapIndexed() { idx, col ->
                val curr = index + idx
                extractValue(cols[curr], resultSet)
            }
            values += s.toValue(retrieved)
            index += s.columns.size
        }
        return values
    }

    internal fun first(): O =
        if (rows.isEmpty())
            throw IllegalStateException("Expected exactly one row, but result set was empty.")
        else rows.get(0)

    internal fun firstOrNull(): O? = if (rows.isEmpty()) null else rows.get(0)

    internal fun asList(): List<O> = rows

    internal fun extractRow(cols: List<ColumnInResultRow>, resultSet: ResultSet): O{
        var index = 0
        val values = mutableListOf<Any?>()
        for (s in selectables){
            val retrieved: List<Any?> = s.columns.mapIndexed() { idx, col ->
                val curr = index + idx
                extractValue(cols[curr], resultSet)
            }
            values += s.toValue(retrieved)
            index += s.columns.size
        }
        return castToRow(values)
    }

    abstract fun castToRow(values: List<Any?>): O

    internal fun columns(): List<AnyColumn> = jdbcResultSetAdapter.resultSetColumns().map { it.column }

}
