package com.dbobjekts.codegen.datatypemapper

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.ColumnTypeMapper
import com.dbobjekts.api.CustomColumnTypeMapper
import com.dbobjekts.codegen.metadata.GeneratedEnumPlaceholderColumn
import com.dbobjekts.metadata.ColumnFactory
import com.dbobjekts.metadata.DefaultTable
import com.dbobjekts.metadata.column.NonNullableColumn

class ColumnTypeMapperForEnum<C : Enum<C>>(
    val columnNamePattern: String,
    val enumClass: Class<C>,
    val table: String? = null,
    val schema: String? = null,
    val exactMatch: Boolean = false
) : ColumnTypeMapper {

    fun invoke(properties: ColumnMappingProperties): Class<C>? {
        val tableMatch = table?.let { properties.table.value.equals(it, true) } ?: true
        val schemaMatch = schema?.let { properties.schema.value.equals(it, true) } ?: true
        return properties.column.value.let { col ->
            if (schemaMatch && tableMatch && if (exactMatch) columnNamePattern.equals(col, true) else col.contains(columnNamePattern, true)) {
                enumClass
            } else null
        }
    }

    override fun map(properties: ColumnMappingProperties): AnyColumn? {
        return invoke(properties)?.let {
            val col = GeneratedEnumPlaceholderColumn("dummy", DefaultTable, it, null)
            if (properties.isNullable) col.nullable else col
        }
    }


}
