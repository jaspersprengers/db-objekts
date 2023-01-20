package com.dbobjekts.codegen

import com.dbobjekts.api.TransactionManager
import com.dbobjekts.api.exception.CodeGenerationException
import com.dbobjekts.codegen.configbuilders.*
import com.dbobjekts.codegen.exclusionfilters.ExclusionConfigurer
import com.dbobjekts.codegen.metadata.DBCatalogDefinition
import com.dbobjekts.codegen.parsers.CatalogParser
import com.dbobjekts.codegen.parsers.ParserConfig
import com.dbobjekts.codegen.writer.SourcesGenerator
import com.dbobjekts.metadata.Catalog
import org.slf4j.LoggerFactory
import javax.sql.DataSource

/**
 * Central manager for creating metadata source code. Configuration consists of
 * * setting the [DataSource]
 * * custom settings for mapping [com.dbobjekts.metadata.column.Column] instances to JDBC types, among other setting
 * * configuration of the output, e.g. the package and directory to be used
 *
 * A minimal example would be
 * ```kotlin
 *    val generator = CodeGenerator()
 *        generator.withDataSource(H2DB.dataSource)
 *        generator.outputConfigurer()
 *             .basePackageForSources("com.dbobjekts.sampledbs.h2")
 *             .outputDirectoryForGeneratedSources("<PATH>")
 *    generator.generateSourceFiles()
 * ```
 */
class CodeGenerator {

    private val logger = LoggerFactory.getLogger(CodeGenerator::class.java)
    private var exclusionConfigurer: ExclusionConfigurer = ExclusionConfigurer()
    private lateinit var dataSource: DataSource
    private val outputConfigurer = OutputConfigurer()
    private val objectNamingConfigurer = ObjectNamingConfigurer()
    private val columnTypeMappingConfigurer = ColumnTypeMappingConfigurer()
    private val primaryKeySequenceConfigurer = PrimaryKeySequenceConfigurer()

    fun withDataSource(datasource: DataSource): CodeGenerator {
        if (this::dataSource.isInitialized)
            throw CodeGenerationException("DataSource is already set!")
        this.dataSource = datasource
        return this
    }

    /**
     * Provides further settings to configure the output of the code generator
     */
    fun configureOutput(): OutputConfigurer = outputConfigurer

    /**
     * Provides optional settings to override default behavior for naming metadata objects and fields.
     */
    fun configureObjectNaming(): ObjectNamingConfigurer = objectNamingConfigurer

    /**
     * Provides optional settings to fine-tune the mapping of [com.dbobjekts.metadata.column.Column] types to SQL types
     */
    fun configureColumnTypeMapping(): ColumnTypeMappingConfigurer = columnTypeMappingConfigurer

    /**
     * Provides optional settings to fine-tune the mapping of [com.dbobjekts.metadata.column.Column] types to SQL types
     */
    fun configurePrimaryKeySequences(): PrimaryKeySequenceConfigurer = primaryKeySequenceConfigurer


    /**
     * Provides optional settings to exclude certain database schemas, tables, or columns from the generated sources.
     */
    fun configureExclusions(): ExclusionConfigurer = exclusionConfigurer

    /**
     * When all mandatory settings have been provided, this writes generated metadata source code to the designated folder.
     */
    fun generateSourceFiles() {
        val catalogDefinition: DBCatalogDefinition = createCatalogDefinition()
        logger.info("Catalog definition was parsed OK. Generating code.")

        //can be null for writers that do not write to the file system
        SourcesGenerator(
            outputConfigurer.basedirOpt
                ?: throw CodeGenerationException("Missing mandatory setting outputDirectoryForGeneratedSources on outputConfigurer"),
            catalogDefinition.packageName,
            catalogDefinition
        ).generate()
        logger.info("Source files were generated OK.")
    }

    /**
     * FOR DEBUGGING PURPOSES
     *
     * Returns the metamodel used for code generation. Can be useful for validation prior to writing code
     *
     * @return [DBCatalogDefinition]
     */
    fun createCatalogDefinition(): DBCatalogDefinition {
        logger.info("Running code generation tool. Validating configuration settings.")
        val generatorConfig: CodeGeneratorConfig = createConfig()
        return createCatalogParser(generatorConfig).parseCatalog()
    }

    private fun createCatalogParser(codeGeneratorConfig: CodeGeneratorConfig): CatalogParser {
        val transactionManager = TransactionManager.builder()
            .withDataSource(codeGeneratorConfig.dataSource)
            .build()
        val vendor = transactionManager.vendor
        val parserConfig = ParserConfig.fromCodeGeneratorConfig(vendor, codeGeneratorConfig)
        return CatalogParser(parserConfig, transactionManager, vendor.metadataExtractor)
    }

    fun differencesWithCatalog(catalog: Catalog): List<String> = createCatalogDefinition().diff(catalog)

    private fun createConfig(): CodeGeneratorConfig {
        return CodeGeneratorConfig(
            dataSource = dataSource,
            exclusionConfigurer = exclusionConfigurer,
            basePackage = outputConfigurer.basePackage
                ?: throw CodeGenerationException("Missing mandatory setting basePackageForSources on outputConfigurer"),
            customColumnMappers = columnTypeMappingConfigurer.mappers.toList(),
            objectNamingConfigurer = objectNamingConfigurer,
            sequenceResolvers = primaryKeySequenceConfigurer.resolvers
        )
    }

}
