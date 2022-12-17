package com.dbobjekts.codegen.datatypemapper

import com.dbobjekts.AnyColumn
import com.dbobjekts.metadata.Columns
import com.dbobjekts.metadata.column.ColumnType


open class JDBCTypeOverrideMapper(
    val jdbcType: String,
    val columnType: ColumnType,
    val table: String? = null,
    val schema: String? = null
) : ColumnTypeMapper {

    override operator fun invoke(properties: ColumnMappingProperties): AnyColumn? {
        val tableMatch = table?.let { properties.table.value == it } ?: true
        val schemaMatch = schema?.let { properties.schema.value == it } ?: true
        return if (tableMatch && schemaMatch && properties.jdbcType.equals(jdbcType, true))
            Columns.byName(columnType, properties.isNullable)
        else null
    }

}
