package com.dbobjekts.codegen.datatypemapper

import com.dbobjekts.AnyColumn
import com.dbobjekts.metadata.column.NonNullableColumn

class StandardColumnTypeMapper(
    val columnNamePattern: String,
    val columnType: NonNullableColumn<*>,
    val table: String? = null,
    val schema: String? = null,
    val exactMatch: Boolean = false
) : ColumnTypeMapper {

    operator override fun invoke(properties: ColumnMappingProperties): AnyColumn? {
        val tableMatch = table?.let { properties.table.value == it } ?: true
        val schemaMatch = schema?.let { properties.schema.value == it } ?: true
        return properties.column.value.let { col ->
            if (schemaMatch && tableMatch && if (exactMatch) columnNamePattern == col else col.contains(columnNamePattern, true)) {
                if (properties.isNullable) columnType.nullable else columnType
            } else null
        }
    }


}
