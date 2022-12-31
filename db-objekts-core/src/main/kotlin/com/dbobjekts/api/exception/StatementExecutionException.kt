package com.dbobjekts.api.exception

import java.sql.SQLException

/**
 * Custom exception to signal errors during execution of a statement or retrieval of results
 */
class StatementExecutionException(
    msg: String,
    throwable: Throwable?,
    val sql: String
) : DBObjektsException(msg, throwable) {
    constructor(msg: String, sql: String) : this(msg, null, sql)
}
