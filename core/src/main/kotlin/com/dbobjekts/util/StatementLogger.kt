package com.dbobjekts.util

import com.dbobjekts.api.AnySqlParameter
import org.slf4j.LoggerFactory

open class StatementLogger {

    private val logger = LoggerFactory.getLogger(StatementLogger::class.java)

    private var lastMessage: String? = null

    private var lastSQLStatement: String? = null

    private var lastParameters: List<AnySqlParameter> = listOf()

    private var lastResult: Any? = null

    fun info(msg: String) {
        logger.info(msg)
        lastMessage = msg
    }

    fun error(msg: String) {
        logger.error(msg)
        lastMessage = "Error $msg"
    }

    fun error(msg: String, e: Throwable) {
        logger.error(msg, e)
        lastMessage = "Error $msg ${e.message}"
    }

    fun lastSQLStatement(): String = lastSQLStatement?.toString() ?: "No SQL statement logged!"

    internal fun logStatement(sql: String, parameters: List<AnySqlParameter>) {
        lastSQLStatement = sql
        lastParameters = parameters
    }

    internal fun <T> logResult(result: T): T {
        lastResult = result
        return result
    }
}
