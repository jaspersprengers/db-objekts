package com.dbobjekts.codegen.datatypemapper

import com.dbobjekts.api.AnyColumn

/**
 * Resolves the [com.dbobjekts.metadata.column.Column] implementation based on [ColumnMappingProperties]
 */
interface ColumnTypeMapper {
    fun map(properties: ColumnMappingProperties): AnyColumn?
}
