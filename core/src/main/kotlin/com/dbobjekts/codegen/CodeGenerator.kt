package com.dbobjekts.codegen

import com.dbobjekts.codegen.configbuilders.CodeGeneratorConfig
import com.dbobjekts.codegen.configbuilders.MappingConfigurer
import com.dbobjekts.codegen.configbuilders.OutputConfigurer
import com.dbobjekts.codegen.exclusionfilters.ExclusionConfigurer
import com.dbobjekts.codegen.metadata.DBCatalogDefinition
import com.dbobjekts.codegen.parsers.DBParserFactory
import com.dbobjekts.codegen.parsers.DBParserFactoryImpl
import com.dbobjekts.codegen.writer.SourceFileWriter
import com.dbobjekts.codegen.writer.SourcesGenerator
import org.slf4j.LoggerFactory
import javax.sql.DataSource

open class CodeGenerator {

    private val logger = LoggerFactory.getLogger(CodeGenerator::class.java)
    private var exclusionConfigurer: ExclusionConfigurer = ExclusionConfigurer()
    private lateinit var dataSource: DataSource
    private val outputConfigurer = OutputConfigurer()
    private val mappingConfigurer = MappingConfigurer()

    internal var parserFactory: DBParserFactory = DBParserFactoryImpl()

    fun withDataSource(datasource: DataSource): CodeGenerator {
        this.dataSource = datasource
        return this
    }

    fun outputConfigurer(): OutputConfigurer = outputConfigurer

    fun mappingConfigurer(): MappingConfigurer = mappingConfigurer

    fun exclusionConfigurer(): ExclusionConfigurer = exclusionConfigurer

    fun generateSourceFiles() {
        val catalogDefinition: DBCatalogDefinition = createCatalogDefinition()
        logger.info("Catalog definition was parsed OK. Generating code.")

        //can be null for writers that do not write to the file system
        logger.info("Output dir for generated source code: ${outputConfigurer.basedirOpt}")
        SourcesGenerator(
            outputConfigurer.basedirOpt,
            catalogDefinition.packageName,
            outputConfigurer.customSourceWriter ?: SourceFileWriter(), catalogDefinition
        ).generate()
        logger.info("Source files were generated OK.")
    }

    fun createCatalogDefinition(): DBCatalogDefinition {
        logger.info("Running code generation tool. Validating configuration settings.")
        val generatorConfig: CodeGeneratorConfig = build()
        return parserFactory.create(generatorConfig).parseCatalog()
    }

    private fun build(): CodeGeneratorConfig {
        return CodeGeneratorConfig(
            dataSource = dataSource,
            exclusionConfigurer = exclusionConfigurer,
            basePackage = outputConfigurer.basePackage,
            customColumnMappers = mappingConfigurer.mappers.toList(),
            sequenceMappers = mappingConfigurer.sequenceMappers
        )
    }

}
