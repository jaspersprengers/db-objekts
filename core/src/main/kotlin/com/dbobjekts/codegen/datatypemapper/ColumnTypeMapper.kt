package com.dbobjekts.codegen.datatypemapper

import com.dbobjekts.api.AnyColumn

/**
 * Resolves the Column implementation based on ColumnMappingProperties
 */
interface ColumnTypeMapper {
    fun map(properties: ColumnMappingProperties): AnyColumn?
}
