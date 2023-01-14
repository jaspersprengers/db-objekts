package com.dbobjekts.codegen

import com.dbobjekts.api.PackageName
import com.dbobjekts.api.SchemaName
import com.dbobjekts.api.TableName
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.datatypemapper.ColumnTypeResolver
import com.dbobjekts.codegen.metadata.*
import org.slf4j.LoggerFactory

/**
 * Yields a DBTableDefinition
 */
internal class TableBuilder(
    val packageName: PackageName,
    val schema: SchemaName,
    val tableName: TableName,
    val foreignKeyManager: ForeignKeyManager,
    val columnTypeResolver: ColumnTypeResolver
) {
    private val log = LoggerFactory.getLogger(TableBuilder::class.java)
    private var alias: String = tableName.value
    private val columns: MutableList<DBColumnDefinition> = mutableListOf()

    fun withAlias(alias: String): TableBuilder {
        this.alias = alias
        return this
    }

    fun build(): DBTableDefinition = DBTableDefinition(packageName, schema, tableName, alias, columns, foreignKeyManager.findLinkedTables(schema, tableName))

    private fun getForeignKeyDefinition(columnMetaData: ColumnMetaData): DBForeignKeyDefinition? =
        foreignKeyManager.findForeignKey(schema, tableName, columnMetaData.columnName)?.let {
            val foreignKey = columnTypeResolver.getForeignKeyColumnForType(schema, tableName, columnMetaData)
            DBForeignKeyDefinition(
                schema = schema,
                table = tableName,
                columnName = it.col,
                columnType = foreignKey,
                parentSchema = it.parentSchema,
                parentTable = it.parentTable,
                parentColumn = it.parentColumn,
                partOfCompositePrimaryKey = columnMetaData.isPrimaryKey,
                comment = columnMetaData.remarks
            )
        }

    private fun findPrimaryKey(props: ColumnMetaData, hasCompositePK: Boolean): DBColumnDefinition? {
        val columnMappingProperties = ColumnMappingProperties.fromMetaData(schema, tableName, props)
        if (!props.isPrimaryKey) {
            return null
        }
        if (props.isAutoIncrement) {
            return DBAutoGeneratedKeyDefinition(
                schema = schema,
                table = tableName,
                columnName = props.columnName,
                columnType = columnTypeResolver.mapAutoIncrementColumn(columnMappingProperties),
                comment = props.remarks
            )
        }
        val sequence = columnTypeResolver.findSequence(columnMappingProperties)
        return if (sequence != null) {
            val defaultType = columnTypeResolver.getDefaultMapping(columnMappingProperties)
            DBSequenceKeyDefinition(
                schema = schema,
                table = tableName,
                name = props.columnName,
                columnType = columnTypeResolver.determineSequenceColumn(defaultType),
                sequence = sequence,
                comment = props.remarks
            )
        } else if (props.isPrimaryKey && !hasCompositePK) {
            val colType = columnTypeResolver.mapDataType(columnMappingProperties)
            DBColumnDefinition(schema, tableName, props.columnName, colType, true, false, props.remarks)
        } else
            null
    }

    fun withColumns(
        schema: SchemaName,
        table: TableName,
        columnMetaData: List<ColumnMetaData>
    ): TableBuilder {
        val tableHasCompositePK: Boolean = columnMetaData.filter { it.isPrimaryKey }.count() > 1
        columnMetaData.forEach { props ->
            findPrimaryKey(props, tableHasCompositePK)?.let { pk ->
                log.debug("Adding primary key for ${pk.tableName}.${pk.columnName}")
                columns += pk
            } ?: getForeignKeyDefinition(props)?.let { fk ->
                log.debug("Adding foreign key for ${fk.tableName}.${fk.columnName}")
                columns += fk
            } ?: columnTypeResolver.mapDataType(ColumnMappingProperties.fromMetaData(schema, table, props)).let { colType ->
                columns += DBColumnDefinition(schema,
                    tableName,
                    props.columnName,
                    colType,
                    false,
                    tableHasCompositePK && props.isPrimaryKey,
                    props.remarks)
            }
        }
        return this
    }

}
