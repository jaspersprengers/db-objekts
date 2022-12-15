package com.dbobjekts.codegen.parsers

import com.dbobjekts.ColumnName
import com.dbobjekts.SchemaName
import com.dbobjekts.TableName
import com.dbobjekts.codegen.TableBuilder
import com.dbobjekts.codegen.ValidateForeignKeyConstraints
import com.dbobjekts.codegen.configbuilders.CodeGeneratorConfig
import com.dbobjekts.codegen.datatypemapper.ColumnTypeResolver
import com.dbobjekts.codegen.metadata.*
import com.dbobjekts.metadata.TableAliases
import com.dbobjekts.metadata.TableAliasesBuilder
import org.slf4j.LoggerFactory


abstract class CatalogParser(private val codeGeneratorConfig: CodeGeneratorConfig) {

    private val log = LoggerFactory.getLogger(CatalogParser::class.java)

    val tableMetaData: List<TableMetaData> by lazy {
        createTableMetaData(codeGeneratorConfig)
    }
    private val basePackage = codeGeneratorConfig.basePackage
    private val columnTypeResolver: ColumnTypeResolver

    init {
        columnTypeResolver = ColumnTypeResolver(codeGeneratorConfig.vendor.defaultMapper,
            codeGeneratorConfig.customColumnMappers,
            codeGeneratorConfig.sequenceMappers)
    }
    
    fun createTableMetaData(conf: CodeGeneratorConfig): List<TableMetaData> {

        val foreignKeyProperties = parseForeignKeyMetaData(extractForeignKeyMetaDataFromDB())
        val metaData = extractColumnAndTableMetaDataFromDB()

        val includedSchemas = metaData.filterNot { md -> conf.exclusionConfigurer.schemaIsExcluded(md.schema) }

        val perSchema = includedSchemas.groupBy({ it.schema })
        return perSchema.flatMap({ (schema, rows) ->
            parseTableMetaDataForSchema(schema, foreignKeyProperties, rows)
        })
    }

    abstract fun extractCatalogs(): List<String>

    abstract fun extractColumnAndTableMetaDataFromDB(): List<TableMetaDataRow>

    abstract fun extractForeignKeyMetaDataFromDB(): List<ForeignKeyMetaDataRow>

    protected fun parseForeignKeyMetaData(metadata: List<ForeignKeyMetaDataRow>): List<ForeignKeyProperties> {
        return metadata.map { row ->
            ForeignKeyProperties(
                col = ColumnName(row.column),
                table = TableName(row.table),
                schema = SchemaName(row.schema),
                parentSchema = SchemaName(row.refSchema),
                parentColumn = ColumnName(row.refColumn),
                parentTable = TableName(row.refTable)
            )
        }
    }

    protected fun parseTableMetaDataForSchema(
        schema: String,
        foreignKeyProperties: List<ForeignKeyProperties>,
        metaData: List<TableMetaDataRow>
    ): List<TableMetaData> {
        val perTable = metaData.groupBy({ it.table })
        return perTable.flatMap({ (table, rows) ->
            val cols = rows.map {
                ColumnMetaData(
                    columnName = ColumnName(it.column),
                    columnType = it.dataType,
                    isAutoIncrement = it.autoIncrement,
                    isPrimaryKey = it.isPrimaryKey,
                    remarks = "columnRemarks",
                    nullable = if (it.isPrimaryKey) false else it.defaultValue != null || it.nullable
                )
            }
            val foreignKeys = foreignKeyProperties.filter { it.table.value.equals(table, true) && it.schema.value.equals(schema, true) }
            val tmd = TableMetaData(schema = SchemaName(schema), tableName = TableName(table), columns = cols, foreignKeys = foreignKeys)
            listOf(tmd)
        })
    }

    fun parseCatalog(): DBCatalogDefinition {
        log.info("Changelog files contain metadata on ${tableMetaData.size} tables.")
        val catalogs = extractCatalogs()
        val catalog = catalogs.firstOrNull() ?: "default"

        val tableDefinitions = createTableDefinitions(tableMetaData)
        val schemas: List<DBSchemaDefinition> = tableDefinitions
            .groupBy { it.schema.value }
            .map { entry ->
                val schemaName = SchemaName(entry.key)
                val packageForSchema = basePackage.createSubPackageForSchema(schemaName)
                val (excluded, included) = codeGeneratorConfig.exclusionConfigurer.partition(entry.value)
                DBSchemaDefinition(packageForSchema, schemaName, included, excluded)
            }
        return DBCatalogDefinition(basePackage, codeGeneratorConfig.vendor, schemas, "${catalog.lowercase()}_catalog")
            .also {
                validateCatalogForMissingTables(it)
            }
    }

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
            val tb = TableBuilder(packageForSchema, tableMd.schema, tableMd.tableName, codeGeneratorConfig, keyManager, columnTypeResolver)

            //exclude the columns explicitly marked
            val allowedColumns = tableMd.columns.filterNot {
                codeGeneratorConfig.exclusionConfigurer.columnIsExcluded(tableMd, it)
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
