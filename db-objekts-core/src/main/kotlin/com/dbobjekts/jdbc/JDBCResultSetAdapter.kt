package com.dbobjekts.jdbc

import com.dbobjekts.api.Slice
import com.dbobjekts.statement.ColumnInResultRow
import com.dbobjekts.api.ResultRow
import com.dbobjekts.api.exception.StatementExecutionException
import java.sql.ResultSet
import java.util.LinkedList

class JDBCResultSetAdapter(
    val resultSetColumns: List<ColumnInResultRow>,
    val resultSet: ResultSet
) {

    fun <T, RS : ResultRow<T>> retrieveWithIterator(selectResultSet: RS, mapper: (Int, T) -> Boolean) {
        var rowNumber = 0
        while (advanceResultSet()) {
            rowNumber += 1
            val proceed = selectResultSet.extractRow(resultSetColumns, resultSet).let { row ->
                try {
                    mapper.invoke(rowNumber, row)
                } catch (e: Exception) {
                    false
                }
            }
            if (!proceed)
                break
        }
        resultSet.close()
    }

    fun resultSetColumns(): List<ColumnInResultRow> {
        return resultSetColumns
    }

    fun <T, RS : ResultRow<T>> retrieveAll(
        resultRow: RS,
        slice: Slice?
    ): List<T> {
        val buffer = LinkedList<T>()
        var rowNumber = 0
        while (advanceResultSet()) {
            if (slice == null || (slice.skip <= rowNumber && buffer.size < slice.limit))
                buffer.add(resultRow.extractRow(resultSetColumns, resultSet))
            rowNumber += 1
        }
        resultSet.close()
        return buffer.toList()
    }

    private fun advanceResultSet(): Boolean {
        if (resultSet.isClosed) {
            throw StatementExecutionException(
                "Cannot read from java.sql.ResultSet: it is already closed. " +
                        "This can happen if you try to access a com.dbobjekts.result.ResultSetBase outside the transaction block and the underlying java.sql.Connection is already closed."
            )
        }
        return resultSet.next()
    }

}
