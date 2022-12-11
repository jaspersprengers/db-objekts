package com.dbobjekts.codegen.exclusionfilters

class ColumnExclusionFilter(
    private val column: String,
    private val table: String? = null,
    private val schema: String? = null,
    private val exactMatch: Boolean = false
) {

    operator fun invoke(schema: String, table: String, column: String): Boolean {
        val matchSchema = this.schema?.equals(schema) ?: true
        val matchTable = this.table?.equals(table) ?: true
        return matchSchema && matchTable && (if (exactMatch) this.column == column else column.contains(this.column))
    }
}
