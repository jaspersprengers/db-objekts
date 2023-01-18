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
        validateNoDuplicateTableNames(createTableMetaData(parserConfig))
    }
    private val basePackage: PackageName = parserConfig.basePackage

    private val columnTypeResolver: ColumnTypeResolver = ColumnTypeResolver(
        parserConfig.vendor.defaultMapper,
        parserConfig.customColumnMappers,
        parserConfig.sequenceMappers
    )


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

    private fun createTableMetaData(conf: ParserConfig): List<TableMetaData> {
        val fkMetadata = metaDataExtractor.extractForeignKeyMetaDataFromDB(transactionManager)
        val foreignKeyProperties = parseForeignKeyMetaData(fkMetadata)
        val metaData = metaDataExtractor.extractColumnAndTableMetaDataFromDB(transactionManager)

        val includedSchemas = metaData.filterNot { md -> conf.exclusionConfigurer.schemaIsExcluded(md.schema) }

        val perSchema: Map<String, List<TableMetaDataRow>> = includedSchemas.groupBy({ it.schema })
        return perSchema.flatMap({ (schema, rows) ->
            parseTableMetaDataForSchema(schema, foreignKeyProperties, rows)
        })
    }

    private fun validateNoDuplicateTableNames(metaData: List<TableMetaData>): List<TableMetaData> {
        val duplicates = metaData
            .groupBy { md -> md.tableName }
            .entries
            .filter { e -> e.value.size > 1 }
        return if (duplicates.isNotEmpty()) {
            throw CodeGenerationException(
                """
                The following table names are found multiple times across schemas. This is not allowed. " +
                ${duplicates.map { it.key }}
                You must provide a unique mapping in the code generator like in the following example:
                generator.configureObjectNaming()
                    .setObjectNameForTable("core", "employee", "core_employee")
                    .setObjectNameForTable("hr", "employee", "hr_employee")
                    """.trimIndent()
            )
        } else metaData
    }

    private fun parseForeignKeyMetaData(metadata: List<ForeignKeyMetaDataRow>): List<ForeignKeyProperties> {
        val conf = parserConfig.objectNamingConfigurer
        return metadata.map { row ->
            conf.getColumnName(row.schema, row.table, row.column)
            ForeignKeyProperties(
                col = conf.getColumnName(row.schema, row.table, row.column),
                table = conf.getTableName(row.schema, row.table),
                schema = SchemaName(row.schema),
                parentSchema = SchemaName(row.refSchema),
                parentColumn = conf.getColumnName(row.refSchema, row.refTable, row.refColumn),
                parentTable = conf.getTableName(row.refSchema, row.refTable)
            )
        }
    }

    private fun parseTableMetaDataForSchema(
        schema: String,
        foreignKeyProperties: List<ForeignKeyProperties>,
        metaData: List<TableMetaDataRow>
    ): List<TableMetaData> {
        val perTable: Map<String, List<TableMetaDataRow>> = metaData.groupBy({ it.table })
        return perTable.flatMap({ (table, rows) ->
            val cols = rows.map {
                val columnName = parserConfig.objectNamingConfigurer.getColumnName(it.schema, it.table, it.column)
                ColumnMetaData(
                    columnName = columnName,
                    columnType = it.dataType,
                    isAutoIncrement = it.autoIncrement,
                    isPrimaryKey = it.isPrimaryKey,
                    remarks = "columnRemarks",
                    nullable = if (it.isPrimaryKey) false else it.defaultValue != null || it.nullable
                )
            }
            val tableName = parserConfig.objectNamingConfigurer.getTableName(schema, table)
            val foreignKeys = foreignKeyProperties.filter { it.table.value.equals(table, true) && it.schema.value.equals(schema, true) }
            val tmd = TableMetaData(schema = SchemaName(schema), tableName = tableName, columns = cols, foreignKeys = foreignKeys)
            listOf(tmd)
        })
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

    private fun createTableDefinitions(basePackage: PackageName, tableMetaData: List<TableMetaData>): List<DBTableDefinition> {
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
