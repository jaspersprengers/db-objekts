package com.dbobjekts.codegen.datatypemapper

import com.dbobjekts.AnyColumn

/**
 * Resolves the Column implementation based on ColumnMappingProperties
 */
interface ColumnTypeMapper {
    operator fun invoke(properties: ColumnMappingProperties): AnyColumn?
}
