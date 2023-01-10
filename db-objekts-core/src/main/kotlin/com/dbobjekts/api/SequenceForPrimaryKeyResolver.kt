package com.dbobjekts.api

import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties

@FunctionalInterface
interface SequenceForPrimaryKeyResolver {
    fun invoke(properties: ColumnMappingProperties): String?
}
