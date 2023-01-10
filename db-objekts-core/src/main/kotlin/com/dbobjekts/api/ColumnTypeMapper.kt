package com.dbobjekts.api

import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties

/**
 * Resolves the [com.dbobjekts.metadata.column.Column] implementation based on [ColumnMappingProperties]
 */
@FunctionalInterface
interface ColumnTypeMapper {
    fun map(properties: ColumnMappingProperties): AnyColumn?
}
