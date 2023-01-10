package com.dbobjekts.codegen.datatypemapper

@FunctionalInterface
interface SequenceForPrimaryKeyResolver {
    fun invoke(properties: ColumnMappingProperties): String?
}
