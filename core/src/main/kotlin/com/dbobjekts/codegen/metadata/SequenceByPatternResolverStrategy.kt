package com.dbobjekts.codegen.metadata

import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties

class SequenceByPatternResolverStrategy(val pattern: String) : SequenceNameResolverStrategy() {
    override fun getSequence(columnProperties: ColumnMappingProperties): String =
        pattern.replace("SCHEMA", columnProperties.schema.value).replace("TABLE", columnProperties.table.value)
}
