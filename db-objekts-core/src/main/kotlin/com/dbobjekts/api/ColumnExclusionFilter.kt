package com.dbobjekts.api

/**
 * Configuration to exclude columns in the database schemas from the generated code.
 */
class ColumnExclusionFilter(
    private val column: String,
    private val table: String? = null,
    private val schema: String? = null,
    private val exactMatch: Boolean = false
) {

    operator fun invoke(schema: String, table: String, column: String): Boolean {
        //true if nul
        val matchSchema = this.schema?.equals(schema, true) ?: true
        val matchTable = this.table?.equals(table, true) ?: true
        return matchSchema && matchTable && (if (exactMatch) this.column.equals(column, true) else column.contains(this.column, true))
    }
}
