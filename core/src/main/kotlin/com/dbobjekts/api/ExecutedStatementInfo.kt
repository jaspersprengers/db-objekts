package com.dbobjekts.api

data class ExecutedStatementInfo(
    val sql: String,
    val parameters: List<AnySqlParameter>,
    var result: Any?
) {
    override fun toString(): String {
        val params = parameters.map { "${it.oneBasedPosition}='${it.value}'" }.joinToString(", ")
        return "$sql Params: ($params) returned ${result.toString()}"
    }
}
