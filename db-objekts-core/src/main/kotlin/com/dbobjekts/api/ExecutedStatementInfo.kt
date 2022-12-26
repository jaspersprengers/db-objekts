package com.dbobjekts.api

/**
 * Information container about an executed sql statement
 */
data class ExecutedStatementInfo(
    /**
     * The executed sql string
     */
    val sql: String,
    /**
     * The values used for the placeholders, marked as '?' in the query
     */
    val parameters: List<AnySqlParameter>,
    /**
     * The result returned by the query
     */
    var result: Any?
) {
    override fun toString(): String {
        val params = parameters.map { "${it.oneBasedPosition}='${it.value}'" }.joinToString(", ")
        return "$sql Params: ($params) returned ${result.toString()}"
    }
}
