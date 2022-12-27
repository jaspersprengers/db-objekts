package com.dbobjekts.codegen

import com.dbobjekts.api.PackageName
import com.dbobjekts.api.SchemaName
import com.dbobjekts.api.TableName
import com.dbobjekts.codegen.configbuilders.CodeGeneratorConfig
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.datatypemapper.ColumnTypeResolver
import com.dbobjekts.codegen.metadata.*
import com.dbobjekts.codegen.parsers.ParserConfig
import org.slf4j.LoggerFactory

/**
 * Yields a DBTableDefinition
 */
internal class TableBuilder(
    val packageName: PackageName,
    val schema: SchemaName,
    val tableName: TableName,
    val foreignKeyManager: ForeignKeyManager,
    val sqlMapper: ColumnTypeResolver
) {
    private val log = LoggerFactory.getLogger(TableBuilder::class.java)
    var alias: String = tableName.value
    val columns: MutableList<DBColumnDefinition> = mutableListOf()

    fun getForeignKeyDefinition(columnMetaData: ColumnMetaData): DBForeignKeyDefinition? =
        foreignKeyManager.findForeignKey(schema, tableName, columnMetaData.columnName)?.let {
            val foreignKey = sqlMapper.getForeignKeyColumnForType(schema, tableName, columnMetaData)
            DBForeignKeyDefinition(
                schema = schema,
                table = tableName,
                columnName = it.col,
                columnType = foreignKey,
                parentSchema = it.parentSchema,
                parentTable = it.parentTable,
                parentColumn = it.parentColumn,
                comment = columnMetaData.remarks
            )
        }

    private fun findPrimaryKey(props: ColumnMetaData): DBColumnDefinition? {
        val columnMappingProperties = ColumnMappingProperties.fromMetaData(schema, tableName, props)
        return if (!props.isPrimaryKey)
            null
        else {
            if (props.isAutoIncrement) {
                DBAutoGeneratedKeyDefinition(
                    schema = schema,
                    table = tableName,
                    columnName = props.columnName,
                    columnType = sqlMapper.mapAutoIncrementColumn(columnMappingProperties),
                    comment = props.remarks
                )
            } else {
                val defaultType = sqlMapper.getDefaultMapping(columnMappingProperties)
                sqlMapper.findSequence(columnMappingProperties)?.let {sequence ->
                    DBSequenceKeyDefinition(
                        schema = schema,
                        table = tableName,
                        name = props.columnName,
                        columnType = sqlMapper.determineSequenceColumn(defaultType),
                        sequence = sequence,
                        comment = props.remarks
                    )
                }
            }
        }
    }

    fun withColumns(
        schema: SchemaName,
        table: TableName,
        columnMetaData: List<ColumnMetaData>
    ): TableBuilder {
        columnMetaData.forEach { props ->
            findPrimaryKey(props)?.let { pk ->
                log.debug("Adding primary key for ${pk.tableName}.${pk.columnName}")
                addColumn(pk)
            } ?: getForeignKeyDefinition(props)?.let { fk ->
                log.debug("Adding foreign key for ${fk.tableName}.${fk.columnName}")
                addColumn(fk)
            } ?: sqlMapper.mapDataType(ColumnMappingProperties.fromMetaData(schema, table, props)).let { colType ->
                val column = DBColumnDefinition(schema, tableName, props.columnName, colType, props.remarks)
                addColumn(column)
            }
        }
        return this
    }


    fun addColumn(columnDef: DBColumnDefinition) {
        columns += columnDef
    }

    fun withAlias(alias: String): TableBuilder {
        this.alias = alias
        return this
    }

    fun build(): DBTableDefinition = DBTableDefinition(packageName, schema, tableName, alias, columns)

}
