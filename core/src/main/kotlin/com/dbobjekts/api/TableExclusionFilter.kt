package com.dbobjekts.api

class TableExclusionFilter(
    private val pattern: String,
    private val exactMatch: Boolean = false,
    private val schema: String? = null
) {

    operator fun invoke(schema: String, table: String): Boolean {
        val matchSchema = this.schema?.equals(schema) ?: true
        return matchSchema && if (exactMatch) table.equals(pattern, true) else table.contains(pattern, true)
    }

}
