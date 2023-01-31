package com.dbobjekts.api

import com.dbobjekts.api.exception.StatementExecutionException
import com.dbobjekts.statement.ColumnInResultRow
import java.sql.ResultSet


class ResultSetIterator<T, R : ResultRow<T>>(
    internal val selectResultSet: R,
    internal val resultSetColumns: List<ColumnInResultRow>,
    internal val resultSet: ResultSet
) : Iterator<T> {

    private var nextElement: T? = null

    override fun hasNext(): Boolean {
        if (resultSet.isClosed) {
            throw StatementExecutionException(
                "Cannot read from java.sql.ResultSet: it is already closed. " +
                        "This can happen if you try to access a com.dbobjekts.result.ResultSetBase outside the transaction block and the underlying java.sql.Connection is already closed."
            )
        }
        return if (nextElement == null) {
            resultSet.next().also { hasNext ->
                nextElement = if (hasNext) selectResultSet.extractRow(resultSetColumns, resultSet) else null
            }
        } else {
            true
        }
    }

    override fun next(): T {
        if (!hasNext())
            throw NoSuchElementException("No more rows in ResultSet. You should check hasNext() before calling next().")
        return (nextElement ?: throw NoSuchElementException()).also { nextElement = null }
    }

}
