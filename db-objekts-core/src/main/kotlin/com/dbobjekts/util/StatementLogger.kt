package com.dbobjekts.util

import com.dbobjekts.api.AnySqlParameter
import com.dbobjekts.api.ExecutedStatementInfo

open class StatementLogger {

    private val cache = mutableListOf<ExecutedStatementInfo>()

    private var lastSQLStatement: String? = null
    private var lastParameters: List<AnySqlParameter> = listOf()

    //returns immutable list
    fun transactionExecutionLog(): List<ExecutedStatementInfo> = cache.toList()

    fun lastStatement(): String? = lastSQLStatement

    internal fun logStatement(
        sql: String,
        parameters: List<AnySqlParameter>
    ) {
        lastSQLStatement = sql
        lastParameters = parameters
    }

    internal fun logResult(result: Any?) {
        //should in fact never be null when we log a result
        if (lastSQLStatement != null) {
            cache += ExecutedStatementInfo(lastSQLStatement ?: "NIL", lastParameters, result)
        }
    }
}
