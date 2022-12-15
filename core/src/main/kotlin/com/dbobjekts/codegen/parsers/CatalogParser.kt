package com.dbobjekts.codegen.parsers

import com.dbobjekts.SchemaName
import com.dbobjekts.codegen.TableBuilder
import com.dbobjekts.codegen.ValidateForeignKeyConstraints
import com.dbobjekts.codegen.configbuilders.CodeGeneratorConfig
import com.dbobjekts.codegen.datatypemapper.ColumnTypeResolver
import com.dbobjekts.codegen.metadata.*
import com.dbobjekts.metadata.TableAliases
import com.dbobjekts.metadata.TableAliasesBuilder
import org.slf4j.LoggerFactory


abstract class CatalogParser(private val generatorConfig: CodeGeneratorConfig) {

    private val log = LoggerFactory.getLogger(CatalogParser::class.java)

    val tableMetaData: List<TableMetaData> by lazy {
        createTableMetaData(generatorConfig)
    }
    private val basePackage = generatorConfig.basePackage
    private val columnTypeResolver: ColumnTypeResolver

    init {
        columnTypeResolver = ColumnTypeResolver(generatorConfig.vendor.defaultMapper,
            generatorConfig.customColumnMappers,
            generatorConfig.sequenceMappers)
    }

    fun parseCatalog(): DBCatalogDefinition {
        log.info("Changelog files contain metadata on ${tableMetaData.size} tables.")
        val tableDefinitions = createTableDefinitions(tableMetaData)
        val schemas: List<DBSchemaDefinition> = tableDefinitions
            .groupBy { it.schema.value }
            .map { entry ->
                val schemaName = SchemaName(entry.key)
                val packageForSchema = basePackage.createSubPackageForSchema(schemaName)
                val (excluded, included) = generatorConfig.exclusionConfigurer.partition(entry.value)
                DBSchemaDefinition(packageForSchema, schemaName, included, excluded)
            }
        return DBCatalogDefinition(basePackage, generatorConfig.vendor, schemas, generatorConfig.catalogName)
            .also {
            validateCatalogForMissingTables(it)
        }
    }

    abstract fun createTableMetaData(conf: CodeGeneratorConfig): List<TableMetaData>

    private fun validateCatalogForMissingTables(catalog: DBCatalogDefinition): DBCatalogDefinition {
        return if (!ValidateForeignKeyConstraints(catalog)) {
            val missing: List<Pair<String, String>> = ValidateForeignKeyConstraints.reportMissing(catalog)
            val errstr = missing.map { "Column ${it.first} refers to ${it.second}." }
            throw IllegalStateException(
                """One or more tables have a foreign key reference to a table or column that has been excluded.
                   |Code generation has been aborted, because the generated code cannot compile.
                   |Either relax your exclusion criteria, or exclude these tables too.
                   |Error report:
                   |${errstr.joinToString("\n")}"""
            )
        } else catalog
    }

    fun createTableDefinitions(tableMetaData: List<TableMetaData>): List<DBTableDefinition> {
        val keyManager = ForeignKeyManager(tableMetaData)
        val tableBuilders: List<TableBuilder> = tableMetaData.map({ tableMd ->
            val packageForSchema = basePackage.createSubPackageForSchema(tableMd.schema)
            val tb = TableBuilder(packageForSchema, tableMd.schema, tableMd.tableName, generatorConfig, keyManager, columnTypeResolver)

            //exclude the columns explicitly marked
            val allowedColumns = tableMd.columns.filterNot {
                generatorConfig.exclusionConfigurer.columnIsExcluded(tableMd, it)
            }

            tb.withColumns(tableMd.schema, tableMd.tableName, allowedColumns)
        })
        if (tableBuilders.isEmpty()) {
            log.warn("Found no tables in schema!")
        }
        val aliasesBuilder = TableAliasesBuilder()
        tableBuilders.groupBy { it.schema }
            .forEach { schema, tables ->
                aliasesBuilder.addSchemaAndTables(schema, tables.map { it.tableName })
            }
        val aliases: TableAliases = aliasesBuilder.build()
        return tableBuilders
            .map { tb ->
                tb.withAlias(aliases.aliasForSchemaAndTable(tb.schema, tb.tableName)).build()
            }
    }

}
