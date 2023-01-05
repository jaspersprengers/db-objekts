package com.dbobjekts.codegen.metadata

import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties

abstract class SequenceNameResolverStrategy {
    abstract fun getSequence(columnProperties: ColumnMappingProperties): String
}


