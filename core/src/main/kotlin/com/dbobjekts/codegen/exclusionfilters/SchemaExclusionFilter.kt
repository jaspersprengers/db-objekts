package com.dbobjekts.codegen.exclusionfilters

class SchemaExclusionFilter(
    private val pattern: String,
    private val exactMatch: Boolean = false
) {

    operator fun invoke(schema: String): Boolean {
        return if (exactMatch) schema.equals(pattern, true) else schema.contains(pattern, true)
    }

}
