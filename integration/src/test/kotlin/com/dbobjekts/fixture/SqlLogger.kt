package com.dbobjekts.fixture

import com.dbobjekts.AnySqlParameter
import com.dbobjekts.SQL
import com.dbobjekts.util.QueryLogger
import com.dbobjekts.util.SLF4JQueryLogger


class SqlLogger : QueryLogger {
    private val slf4JLogger = SLF4JQueryLogger()
    private val statements = mutableListOf<SQL>()

    override fun logStatement(sql: SQL, parameters: List<AnySqlParameter>) {
        slf4JLogger.logStatement(sql, parameters)
        statements.add(sql)
    }

    override fun <T> logResult(result: T): T {
        slf4JLogger.logResult(result)
        return result
    }

    override fun info(msg: String) {
        slf4JLogger.info(msg)
    }

    override fun error(msg: String) {
        slf4JLogger.error(msg)
    }

    override fun error(msg: String, e: Throwable) {
        slf4JLogger.error(msg, e)
    }

    override fun lastSQLStatement(): SQL? = statements.lastOrNull()
}
