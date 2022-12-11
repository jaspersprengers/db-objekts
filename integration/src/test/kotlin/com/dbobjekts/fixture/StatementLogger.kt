package com.dbobjekts.fixture

import com.dbobjekts.AnySqlParameter
import com.dbobjekts.SQL
import com.dbobjekts.util.QueryLogger
import org.junit.jupiter.api.Assertions.assertEquals

class StatementLogger : QueryLogger {

    var lastMessage: String? = null

    private var lastSQLStatement: SQL? = null

    var lastParameters: List<AnySqlParameter> = listOf()

    var lastResult: Any? = null

    override fun info(msg: String) {lastMessage = msg}

    override fun error(msg: String) {lastMessage = "Error $msg"}

    override fun error(msg: String, e: Throwable) {lastMessage = "Error $msg ${e.message}"}

    override fun lastSQLStatement() = lastSQLStatement

    override fun logStatement(sql: SQL, parameters: List<AnySqlParameter>) {
        lastSQLStatement = sql
        lastParameters = parameters
    }

    override fun <T> logResult(result: T): T {
        lastResult = result
        return result
    }

    fun assertSQL(sql: String) {
        assertEquals(sql, lastSQLStatement?.value?:"")
    }

    fun assertParameter(position: Int, value: Any) {
        assert(lastParameters[position - 1].value?.toString()?:"" == value.toString())
    }

    fun reset(): StatementLogger {
        lastMessage = null
        lastSQLStatement = null
        lastParameters = listOf()
        lastResult = null
        return this
    }
}
