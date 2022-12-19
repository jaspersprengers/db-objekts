package com.dbobjekts.codegen

import com.dbobjekts.codegen.configbuilders.CodeGeneratorConfig
import com.dbobjekts.codegen.configbuilders.MappingConfigurer
import com.dbobjekts.codegen.configbuilders.DatabaseConfigurer
import com.dbobjekts.codegen.configbuilders.OutputConfigurer
import com.dbobjekts.codegen.exclusionfilters.ExclusionConfigurer
import com.dbobjekts.codegen.metadata.DBCatalogDefinition
import com.dbobjekts.codegen.parsers.DBParserFactory
import com.dbobjekts.codegen.parsers.DBParserFactoryImpl
import com.dbobjekts.codegen.writer.SourceFileWriter
import com.dbobjekts.codegen.writer.SourcesGenerator
import org.slf4j.LoggerFactory

open class CodeGenerator {

    private val logger = LoggerFactory.getLogger(CodeGenerator::class.java)
    private var exclusionConfigurer: ExclusionConfigurer = ExclusionConfigurer()
    private val dbConfigurer = DatabaseConfigurer()
    private val outputConfigurer = OutputConfigurer()
    private val mappingConfigurer = MappingConfigurer()

    var parserFactory: DBParserFactory = DBParserFactoryImpl()

    fun dataSourceConfigurer(): DatabaseConfigurer = dbConfigurer

    fun outputConfigurer(): OutputConfigurer = outputConfigurer

    fun mappingConfigurer(): MappingConfigurer = mappingConfigurer

    fun exclusionConfigurer(): ExclusionConfigurer = exclusionConfigurer

    fun generate() {
        val catalogDefinition = createCatalogDefinition()
        logger.info("Catalog definition was parsed OK. Generating code.")

        //can be null for writers that do not write to the file system

        logger.info("Output dir for generated source code: ${outputConfigurer.basedirOpt}")
        SourcesGenerator(
            outputConfigurer.basedirOpt,
            outputConfigurer.basePackage ?: throw IllegalStateException("Base package is mandatory"),
            outputConfigurer.customSourceWriter ?: SourceFileWriter(), catalogDefinition).generate()
        logger.info("Source files were generated OK.")
    }

    fun createCatalogDefinition(): DBCatalogDefinition {
        logger.info("Running code generation tool. Validating configuration settings.")
        val generatorConfig: CodeGeneratorConfig = build()
        return parserFactory.create(generatorConfig).parseCatalog()
    }

    private fun build(): CodeGeneratorConfig {

        logger.info("Base package for generated source code: ${outputConfigurer.basePackage}")

        return CodeGeneratorConfig(
            vendor = dbConfigurer.vendor ?: throw IllegalStateException("Vendor is mandatory"),
            dataSource = dbConfigurer.getDataSource(),
            exclusionConfigurer = exclusionConfigurer,
            basePackage = outputConfigurer.basePackage ?: throw IllegalStateException("Base package is mandatory"),
            customColumnMappers = mappingConfigurer.mappers.toList(),
            sequenceMappers = mappingConfigurer.sequenceMappers
        )
    }

}
