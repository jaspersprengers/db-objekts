package com.dbobjekts.codegen.datatypemapper

import com.dbobjekts.AnyColumn
import com.dbobjekts.metadata.column.NonNullableColumn


open class JDBCTypeOverrideMapper(
    val jdbcType: String,
    val columnType: NonNullableColumn<*>,
    val table: String? = null,
    val schema: String? = null
) : ColumnTypeMapper {

    override operator fun invoke(properties: ColumnMappingProperties): AnyColumn? {
        val tableMatch = table?.let { properties.table.value == it } ?: true
        val schemaMatch = schema?.let { properties.schema.value == it } ?: true
        return if (tableMatch && schemaMatch && properties.jdbcType.equals(jdbcType, true))
            if (properties.isNullable) columnType.nullable else columnType
        else null
    }

}
