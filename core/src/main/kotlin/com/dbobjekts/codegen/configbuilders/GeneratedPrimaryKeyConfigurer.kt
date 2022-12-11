package com.dbobjekts.codegen.configbuilders

import com.dbobjekts.codegen.metadata.*


class GeneratedPrimaryKeyConfigurer(private val returnValue: MappingConfigurer) {

    private var autoIncrement = false
    private var noGeneratedKey = true
    private var sequenceNameResolverStrategy: SequenceNameResolverStrategy? = null
    private var sequenceNameByPatternResolverStrategy: SequenceNameResolverStrategy? = null

    fun autoIncrementPrimaryKey(): MappingConfigurer {
        reset()
        autoIncrement = true
        return returnValue
    }

    fun sequenceNameResolverStrategy(strategy: SequenceNameResolverStrategy): MappingConfigurer {
        reset()
        sequenceNameResolverStrategy = strategy
        return returnValue
    }

    fun sequenceNameByPatternResolverStrategy(pattern: String): MappingConfigurer {
        reset()
        sequenceNameByPatternResolverStrategy = SequenceByPatternResolverStrategy(pattern)
        return returnValue
    }

    private fun reset(){
        autoIncrement = false
        noGeneratedKey = false
        sequenceNameResolverStrategy = null
        sequenceNameByPatternResolverStrategy = null
    }

    fun getStrategy(): PrimaryKeyStrategy {
        return if (autoIncrement) AutoIncrementPrimaryKeyStrategy
        else sequenceNameResolverStrategy
            ?: sequenceNameByPatternResolverStrategy
            ?: NoGeneratedPrimaryKeyStrategy
    }
}
