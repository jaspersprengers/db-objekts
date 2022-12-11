package com.dbobjekts.util

import com.dbobjekts.AnySqlParameter
import com.dbobjekts.SQL


 interface QueryLogger {

    fun info(msg: String)

    fun error(msg: String)

    fun error(msg: String, e: Throwable)

    fun lastSQLStatement(): SQL?

    fun logStatement(sql: SQL, parameters: List<AnySqlParameter>)

    fun <T> logResult(result: T): T
}
