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

    private val namingConfigurer = parserConfig.objectNamingConfigurer

    private val columnTypeResolver: ColumnTypeResolver = ColumnTypeResolver(
        parserConfig.vendor.defaultMapper,
        parserConfig.customColumnMappers,
        parserConfig.sequenceMappers
    )


    fun parseCatalog(): DBCatalogDefinition {
        val tableDefinitions = createTableDefinitions(basePackage, tableMetaData)
        val schemas: List<DBSchemaDefinition> = tableDefinitions
            .groupBy { it.schema.value }
            .map { (schema, definition) ->
                val schemaName = namingConfigurer.getSchemaName(schema)
                val packageForSchema = basePackage.createSubPackageForSchema(schemaName)
                log.info("Parsing schema ${schemaName.value} with class name ${schemaName.metaDataObjectName} in package $packageForSchema")
                val (excluded, included) = parserConfig.exclusionConfigurer.partition(definition)
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
            .groupBy { md -> md.tableName.metaDataObjectName }
            .entries
            .filter { e -> e.value.size > 1 }
        return if (duplicates.isNotEmpty()) {
            throw CodeGenerationException(
                """
                The following table names  ${duplicates.map { it.key }} are found multiple times across schemas. This is not allowed.          
                If the same table is defined in multiple schemas, you must provide a unique mapping in the code generator.
                Another cause of this error could be when you set a custom object name for a table to one that already exists in the same schema.
                generator.configureObjectNaming()
                    .setObjectNameForTable("core", "employee", "core_employee")
                    .setObjectNameForTable("hr", "employee", "hr_employee")
                    """.trimIndent()
            )
        } else metaData
    }

    private fun validateNoDuplicateColumnNames(table: String, metaData: List<ColumnMetaData>): List<ColumnMetaData> {
        val duplicates = metaData
            .groupBy { md -> md.columnName.fieldName }
            .entries
            .filter { e -> e.value.size > 1 }
        return if (duplicates.isNotEmpty()) {
            throw CodeGenerationException(
                """
                The following column names are found more than once in table $table
                ${duplicates.map { it.key }}
                This is probably caused by a manual misconfiguration in CodeGenerator.configureObjectNaming()
                """.trimIndent()
            )
        } else metaData
    }

    private fun parseForeignKeyMetaData(metadata: List<ForeignKeyMetaDataRow>): List<ForeignKeyProperties> {
        return metadata.map { row ->
            namingConfigurer.getColumnName(row.schema, row.table, row.column)
            ForeignKeyProperties(
                col = namingConfigurer.getColumnName(row.schema, row.table, row.column),
                table = namingConfigurer.getTableName(row.schema, row.table),
                schema = namingConfigurer.getSchemaName(row.schema),
                parentSchema = namingConfigurer.getSchemaName(row.refSchema),
                parentColumn = namingConfigurer.getColumnName(row.refSchema, row.refTable, row.refColumn),
                parentTable = namingConfigurer.getTableName(row.refSchema, row.refTable)
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
            val cols: List<ColumnMetaData> = rows.map {
                val columnName = namingConfigurer.getColumnName(it.schema, it.table, it.column)
                ColumnMetaData(
                    columnName = columnName,
                    columnType = it.dataType,
                    isAutoIncrement = it.autoIncrement,
                    isPrimaryKey = it.isPrimaryKey,
                    remarks = "columnRemarks",
                    nullable = if (it.isPrimaryKey) false else it.defaultValue != null || it.nullable
                )
            }
            validateNoDuplicateColumnNames(table, cols)
            val tableName = namingConfigurer.getTableName(schema, table)
            val foreignKeys = foreignKeyProperties.filter { it.table.value.equals(table, true) && it.schema.value.equals(schema, true) }
            val tmd = TableMetaData(schema = namingConfigurer.getSchemaName(schema),
                tableName = tableName, columns = cols, foreignKeys = foreignKeys)
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
        tableBuilders.groupBy { it.schemaName }
            .forEach { schema, tables ->
                aliasesBuilder.addSchemaAndTables(schema, tables.map { it.tableName })
            }
        val aliases: TableAliases = aliasesBuilder.build()
        return tableBuilders
            .map { tb ->
                tb.withAlias(aliases.aliasForSchemaAndTable(tb.schemaName, tb.tableName)).build()
            }
    }


}
