package com.dbobjekts.jdbc

import com.dbobjekts.result.*
import java.sql.ResultSet

class JDBCResultSetAdapter(
    val resultSetColumns: List<ColumnInResultRow>,
    val resultSet: ResultSet
) {

    fun isClosed(): Boolean = resultSet.isClosed

    fun <T, RS : ResultRow<T>> retrieveWithIterator(selectResultSet: RS, mapper: (T) -> Boolean) {
        while (advanceResultSet()) {
            val proceed = selectResultSet.extractRow(resultSetColumns, resultSet).let { row ->
                try {
                    mapper.invoke(row)
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

    fun <T, RS : ResultRow<T>> retrieveAll(rrb: RS): List<T> {
        val buffer = mutableListOf<T>()
        while (advanceResultSet()) {
            buffer.add(rrb.extractRow(resultSetColumns, resultSet))
        }
        resultSet.close()
        return buffer.toList()
    }

    private fun advanceResultSet(): Boolean {
        if (resultSet.isClosed) {
            throw IllegalStateException(
                "Cannot read from java.sql.ResultSet: it is already closed. " +
                        "This can happen if you try to access a com.dbobjekts.result.ResultSetBase outside the transaction block and the underlying java.sql.Connection is already closed."
            )
        }
        return resultSet.next()
    }

}
