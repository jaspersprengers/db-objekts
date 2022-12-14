package com.dbobjekts.codegen.parsers

import com.dbobjekts.ColumnName
import com.dbobjekts.SchemaName
import com.dbobjekts.TableName
import com.dbobjekts.codegen.ProgressLogger
import com.dbobjekts.codegen.configbuilders.CodeGeneratorConfig
import com.dbobjekts.codegen.metadata.ColumnMetaData
import com.dbobjekts.codegen.metadata.ForeignKeyProperties
import com.dbobjekts.codegen.metadata.TableMetaData
import com.dbobjekts.jdbc.TransactionManager


abstract class LiveDBParser(codeGeneratorConfig: CodeGeneratorConfig,
                            protected val transactionManager: TransactionManager,
                            protected val logger: ProgressLogger) : CatalogParser(codeGeneratorConfig) {

    override fun createTableMetaData(conf: CodeGeneratorConfig): List<TableMetaData> {

        val foreignKeyProperties = parseForeignKeyMetaData(extractForeignKeyMetaDataFromDB())
        val metaData = extractColumnAndTableMetaDataFromDB()

        val includedSchemas = metaData.filterNot { md -> conf.exclusionConfigurer.schemaIsExcluded(md.schema) }

        val perSchema = includedSchemas.groupBy({ it.schema })
        return perSchema.flatMap({ (schema, rows) ->
            parseTableMetaDataForSchema(schema, foreignKeyProperties, rows)
        })
    }

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
            println(tmd)
            println()
            listOf(tmd)
        })
    }




}
