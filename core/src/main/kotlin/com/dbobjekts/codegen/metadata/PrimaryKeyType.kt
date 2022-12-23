package com.dbobjekts.codegen.metadata

import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties

interface PrimaryKeyStrategy

abstract class SequenceNameResolverStrategy : PrimaryKeyStrategy {
    abstract fun getSequence(columnProperties: ColumnMappingProperties): String
}


