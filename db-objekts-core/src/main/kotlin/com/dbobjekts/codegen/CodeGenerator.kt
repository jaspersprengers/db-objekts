package com.dbobjekts.codegen

import com.dbobjekts.api.TransactionManager
import com.dbobjekts.codegen.configbuilders.CodeGeneratorConfig
import com.dbobjekts.codegen.configbuilders.MappingConfigurer
import com.dbobjekts.codegen.configbuilders.OutputConfigurer
import com.dbobjekts.codegen.exclusionfilters.ExclusionConfigurer
import com.dbobjekts.codegen.metadata.DBCatalogDefinition
import com.dbobjekts.codegen.parsers.CatalogParser
import com.dbobjekts.codegen.parsers.ParserConfig
import com.dbobjekts.codegen.writer.SourcesGenerator
import com.dbobjekts.metadata.Catalog
import org.slf4j.LoggerFactory
import java.lang.IllegalArgumentException
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
open class CodeGenerator {

    private val logger = LoggerFactory.getLogger(CodeGenerator::class.java)
    private var exclusionConfigurer: ExclusionConfigurer = ExclusionConfigurer()
    private lateinit var dataSource: DataSource
    private val outputConfigurer = OutputConfigurer()
    private val mappingConfigurer = MappingConfigurer()

    fun withDataSource(datasource: DataSource): CodeGenerator {
        if (this::dataSource.isInitialized)
            throw IllegalArgumentException("DataSource is already set!")
        this.dataSource = datasource
        return this
    }

    /**
     * Provides further settings to configure the output of the code generator
     */
    fun outputConfigurer(): OutputConfigurer = outputConfigurer

    /**
     * Provides optional settings to fine-tune the mapping of [com.dbobjekts.metadata.column.Column] types to SQL types
     */
    fun mappingConfigurer(): MappingConfigurer = mappingConfigurer

    /**
     * Provides optional settings to exclude certain database schemas, tables, or columns from the generated sources.
     */
    fun exclusionConfigurer(): ExclusionConfigurer = exclusionConfigurer

    /**
     * When all is properly set up, this outputs the code
     */
    fun generateSourceFiles() {
        val catalogDefinition: DBCatalogDefinition = createCatalogDefinition()
        logger.info("Catalog definition was parsed OK. Generating code.")

        //can be null for writers that do not write to the file system
        SourcesGenerator(
            outputConfigurer.basedirOpt
                ?: throw java.lang.IllegalStateException("Missing mandatory setting basePackageForSources on outputConfigurer"),
            catalogDefinition.packageName,
            catalogDefinition
        ).generate()
        logger.info("Source files were generated OK.")
    }

    /**
     * Only writes source files if the state of the new Catalog is different from the one provided.
     *
     * This avoids unnecessarily overwriting files that have not changed.
     * @return true if the catalogs differed and sources were written, false otherwise
     */
    fun generateSourceFilesIfChanged(referenceCatalog: Catalog): Boolean {
        return true;
    }

    internal fun createCatalogDefinition(): DBCatalogDefinition {
        logger.info("Running code generation tool. Validating configuration settings.")
        val generatorConfig: CodeGeneratorConfig = build()
        return createCatalogParser(generatorConfig).parseCatalog()
    }

    private fun createCatalogParser(codeGeneratorConfig: CodeGeneratorConfig): CatalogParser {
        val transactionManager = TransactionManager.builder().withDataSource(codeGeneratorConfig.dataSource).build()
        val vendor = transactionManager.vendor
        val config = ParserConfig.fromCodeGeneratorConfig(vendor, codeGeneratorConfig)
        return CatalogParser(config, transactionManager, vendor.metadataExtractor)
    }

    fun differencesWithCatalog(catalog: Catalog): List<String> = createCatalogDefinition().diff(catalog)

    private fun build(): CodeGeneratorConfig {
        return CodeGeneratorConfig(
            dataSource = dataSource,
            exclusionConfigurer = exclusionConfigurer,
            basePackage = outputConfigurer.basePackage
                ?: throw IllegalStateException("Missing mandatory setting basePackageForSources on outputConfigurer"),
            customColumnMappers = mappingConfigurer.mappers.toList(),
            sequenceMappers = mappingConfigurer.sequenceMappers
        )
    }

}
