package com.dbobjekts.codegen

import com.dbobjekts.codegen.configbuilders.CodeGeneratorConfig
import com.dbobjekts.codegen.configbuilders.MappingConfigurer
import com.dbobjekts.codegen.configbuilders.DatabaseSourceConfigurer
import com.dbobjekts.codegen.configbuilders.OutputConfigurer
import com.dbobjekts.codegen.exclusionfilters.ExclusionConfigurer
import com.dbobjekts.codegen.metadata.DBCatalogDefinition
import com.dbobjekts.codegen.parsers.CatalogParser
import com.dbobjekts.codegen.parsers.LiquibaseCatalogParser
import com.dbobjekts.codegen.parsers.LiveDBParserFactory
import com.dbobjekts.codegen.writer.SourceFileWriter
import com.dbobjekts.codegen.writer.SourcesGenerator

open class CodeGenerator {

    private val logger = ProgressLogger()
    private var exclusionConfigurer: ExclusionConfigurer = ExclusionConfigurer()
    private val dbConfigurer = DatabaseSourceConfigurer()
    private val outputConfigurer = OutputConfigurer()
    private val mappingConfigurer = MappingConfigurer()

    fun sourceConfigurer(): DatabaseSourceConfigurer = dbConfigurer

    fun outputConfigurer(): OutputConfigurer = outputConfigurer

    fun mappingConfigurer(): MappingConfigurer = mappingConfigurer

    fun exclusionConfigurer(): ExclusionConfigurer = exclusionConfigurer

    fun generate() {
        val catalogDefinition = createCatalogDefinition()
        logger.info("Catalog definition was parsed OK. Generating code.")

        //can be null for writers that do not write to the file system
        val baseDirForSources: String? =
            outputConfigurer.basedirOpt
                ?: dbConfigurer.getChangeLogFilesPath()

        logger.info("Output dir for generated source code: $baseDirForSources")
        SourcesGenerator(
            baseDirForSources,
            outputConfigurer.basePackage ?: throw IllegalStateException("Base package is mandatory"),
            outputConfigurer.customSourceWriter ?: SourceFileWriter(),
            catalogDefinition,
            logger
        ).generate()
        logger.info("Source files were generated OK.")
    }

    fun createCatalogDefinition(): DBCatalogDefinition {
        logger.info("Running code generation tool. Validating configuration settings.")
        val generatorConfig: CodeGeneratorConfig = build()
        return createCatalogParser(generatorConfig).parseCatalog()
    }

    private fun createCatalogParser(config: CodeGeneratorConfig): CatalogParser {
        val hasFiles = config.changeLogFiles.isNotEmpty()
        val hasDataSource = config.dataSourceInfo != null
        if (hasFiles && hasDataSource) {
            throw IllegalStateException("You cannot specify both liquibase changelog files and a datasource. Choose either one of these strategies.")
        }
        return if (hasFiles) {
            LiquibaseCatalogParser(config, logger)
        } else if (hasDataSource) {
            LiveDBParserFactory.create(config, logger)
        } else {
            throw IllegalStateException(
                "You did not configure the source (liquibase files or live database) from which to extract table details. " +
                        "Make sure to configure databaseSourceConfigurer()"

            )
        }
    }

    private fun build(): CodeGeneratorConfig {
        dbConfigurer.validate()

        logger.info("Base package for generated source code: ${outputConfigurer.basePackage}")

        return CodeGeneratorConfig(
            vendor = dbConfigurer.vendor ?: throw IllegalStateException("Vendor is mandatory"),
            changeLogFiles = dbConfigurer.changeLogFiles.toMap(),
            dataSourceInfo = dbConfigurer.dataSourceConfigurer.toDataSourceInfo(),
            exclusionConfigurer = exclusionConfigurer,
            basePackage = outputConfigurer.basePackage ?: throw IllegalStateException("Base package is mandatory"),
            customColumnMappers = mappingConfigurer.mappers.toList(),
            sequenceMappers = mappingConfigurer.sequenceMappers
        )
    }

}
