package com.dbobjekts.api.exception

/**
 * Custom exception to signal errors during statement construction, before executing the statement and retrieving the results
 */
class StatementBuilderException(msg: String,
                                throwable: Throwable?,
                                val sql: String?) : DBObjektsException(msg, throwable) {
    constructor(msg: String) : this(msg, null, null)
    constructor(msg: String, sql: String) : this(msg, null, sql)
}
