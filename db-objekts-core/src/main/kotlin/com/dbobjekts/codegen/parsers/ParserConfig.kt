package com.dbobjekts.codegen.parsers

import com.dbobjekts.api.CustomColumnTypeMapper
import com.dbobjekts.api.PackageName
import com.dbobjekts.api.SequenceForPrimaryKeyResolver
import com.dbobjekts.codegen.configbuilders.CodeGeneratorConfig
import com.dbobjekts.codegen.exclusionfilters.ExclusionConfigurer
import com.dbobjekts.vendors.Vendor


data class ParserConfig(
    val vendor: Vendor,
    val exclusionConfigurer: ExclusionConfigurer,
    val basePackage: PackageName,
    val customColumnMappers: List<CustomColumnTypeMapper<*>> = listOf(),
    val sequenceMappers: List<SequenceForPrimaryKeyResolver> = listOf()
) {
    companion object {
        fun fromCodeGeneratorConfig(
            vendor: Vendor,
            codeGeneratorConfig: CodeGeneratorConfig
        ): ParserConfig =
            ParserConfig(
                vendor = vendor,
                exclusionConfigurer = codeGeneratorConfig.exclusionConfigurer,
                basePackage = codeGeneratorConfig.basePackage,
                customColumnMappers = codeGeneratorConfig.customColumnMappers,
                sequenceMappers = codeGeneratorConfig.sequenceResolvers
            )
    }
}
