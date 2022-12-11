package com.dbobjekts.util

import com.dbobjekts.AnySqlParameter
import com.dbobjekts.SQL
import org.slf4j.LoggerFactory

class SLF4JQueryLogger:QueryLogger {

    private val logger = LoggerFactory.getLogger("com.dbobjekts")

    private var _lastSQLStatement: SQL? = null

    override fun info(msg: String) = logger.info(msg)

    override fun logStatement(sql: SQL, parameters: List<AnySqlParameter>)  {
        logger.debug("Statement SQL {}", sql.value)
        _lastSQLStatement = sql
        parameters.forEach { logger.debug(it.toString()) }
    }

    override fun <T> logResult(result: T): T {
        logger.debug("Query result {}", result.toString())
        return result
    }

    override fun error(msg: String) = logger.error(msg)

    override fun error(msg: String, e: Throwable) = logger.error(msg, e)

    override fun lastSQLStatement(): SQL? = lastSQLStatement()
}
