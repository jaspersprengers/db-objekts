package com.dbobjekts.codegen.datatypemapper

import com.dbobjekts.api.CustomColumnTypeMapper
import com.dbobjekts.metadata.column.NonNullableColumn

class ColumnTypeMapperByNameMatch<C : NonNullableColumn<*>>(
    val columnNamePattern: String,
    val columnType: Class<C>,
    val table: String? = null,
    val schema: String? = null,
    val exactMatch: Boolean = false
) : CustomColumnTypeMapper<C>() {

    override fun invoke(properties: ColumnMappingProperties): Class<C>? {
        val tableMatch = table?.let { properties.table.value.equals(it, true) } ?: true
        val schemaMatch = schema?.let { properties.schema.value.equals(it, true) } ?: true
        return properties.column.value.let { col ->
            if (schemaMatch && tableMatch && if (exactMatch) columnNamePattern.equals(col, true) else col.contains(columnNamePattern, true)) {
                columnType
            } else null
        }
    }


}
