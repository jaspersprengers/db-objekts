package com.dbobjekts.codegen.datatypemapper

import com.dbobjekts.api.CustomColumnTypeMapper
import com.dbobjekts.metadata.column.NonNullableColumn


open class JDBCTypeOverrideMapper<C : NonNullableColumn<*>>(
    val jdbcType: String,
    val columnType: Class<C>,
    val table: String? = null,
    val schema: String? = null
) : CustomColumnTypeMapper<C>() {

    override fun invoke(properties: ColumnMappingProperties): Class<C>? {
        val tableMatch = table?.let { properties.table.value.equals(it, true) } ?: true
        val schemaMatch = schema?.let { properties.schema.value.equals(it, true) } ?: true
        return if (tableMatch && schemaMatch && properties.jdbcType.equals(jdbcType, true))
            columnType
        else null
    }

}
