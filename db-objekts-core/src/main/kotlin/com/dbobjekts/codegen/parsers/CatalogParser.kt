package com.dbobjekts.codegen.parsers

import com.dbobjekts.api.*
import com.dbobjekts.api.exception.CodeGenerationException
import com.dbobjekts.codegen.TableBuilder
import com.dbobjekts.codegen.ValidateForeignKeyConstraints
import com.dbobjekts.codegen.datatypemapper.ColumnTypeResolver
import com.dbobjekts.codegen.metadata.*
import com.dbobjekts.metadata.TableAliases
import com.dbobjekts.metadata.TableAliasesBuilder
import org.slf4j.LoggerFactory


class CatalogParser(
    private val parserConfig: ParserConfig,
    private val transactionManager: TransactionManager,
    private val metaDataExtractor: VendorSpecificMetaDataExtractor
) {

    private val log = LoggerFactory.getLogger(CatalogParser::class.java)

    val tableMetaData: List<TableMetaData> by lazy {
        createTableMetaData(parserConfig)
    }
    private val basePackage: PackageName = parserConfig.basePackage

    private val columnTypeResolver: ColumnTypeResolver = ColumnTypeResolver(
        parserConfig.vendor.defaultMapper,
        parserConfig.customColumnMappers,
        parserConfig.sequenceMappers
    )


    fun createTableMetaData(conf: ParserConfig): List<TableMetaData> {
        val fkMetadata = metaDataExtractor.extractForeignKeyMetaDataFromDB(transactionManager)
        val foreignKeyProperties = parseForeignKeyMetaData(fkMetadata)
        val metaData = metaDataExtractor.extractColumnAndTableMetaDataFromDB(transactionManager)

        val includedSchemas = metaData.filterNot { md -> conf.exclusionConfigurer.schemaIsExcluded(md.schema) }

        val perSchema: Map<String, List<TableMetaDataRow>> = includedSchemas.groupBy({ it.schema })
        return perSchema.flatMap({ (schema, rows) ->
            parseTableMetaDataForSchema(schema, foreignKeyProperties, rows)
        })
    }

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
        val tableDefinitions = createTableDefinitions(basePackage, tableMetaData)
        val schemas: List<DBSchemaDefinition> = tableDefinitions
            .groupBy { it.schema.value }
            .map { entry ->
                val schemaName = SchemaName(entry.key)
                val packageForSchema = basePackage.createSubPackageForSchema(schemaName)
                val (excluded, included) = parserConfig.exclusionConfigurer.partition(entry.value)
                DBSchemaDefinition(packageForSchema, schemaName, included, excluded)
            }
        return DBCatalogDefinition(basePackage, parserConfig.vendor, schemas, "catalog_definition")
            .also {
                validateCatalogForMissingTables(it)
            }
    }

    private fun validateCatalogForMissingTables(catalog: DBCatalogDefinition): DBCatalogDefinition {
        return if (!ValidateForeignKeyConstraints(catalog)) {
            val missing: List<Pair<String, String>> = ValidateForeignKeyConstraints.reportMissing(catalog)
            val errstr = missing.map { "Column ${it.first} refers to ${it.second}." }
            throw CodeGenerationException(
                """One or more tables have a foreign key reference to a table or column that has been excluded.
                   Code generation has been aborted, because the generated code cannot compile.
                   Either relax your exclusion criteria, or exclude these tables too.
                   Error report:
                   ${errstr.joinToString("\n")}""".trimIndent()
            )
        } else catalog
    }

    fun createTableDefinitions(basePackage: PackageName, tableMetaData: List<TableMetaData>): List<DBTableDefinition> {
        val keyManager = ForeignKeyManager(tableMetaData)
        val tableBuilders: List<TableBuilder> = tableMetaData.map({ table ->
            val packageForSchema = basePackage.createSubPackageForSchema(table.schema)
            val tableBuilder = TableBuilder(packageForSchema, table.schema, table.tableName, keyManager, columnTypeResolver)

            //exclude the columns explicitly marked
            val allowedColumns = table.columns.filterNot {
                parserConfig.exclusionConfigurer.columnIsExcluded(table, it)
            }

            tableBuilder.withColumns(table.schema, table.tableName, allowedColumns)
        })
        if (tableBuilders.isEmpty()) {
            log.warn("Found no eligible tables in catalog.")
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
