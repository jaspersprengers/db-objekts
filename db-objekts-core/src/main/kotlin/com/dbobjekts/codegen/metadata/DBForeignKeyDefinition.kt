package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.*

class DBForeignKeyDefinition(
    schema: SchemaName,
    table: TableName,
    columnName: ColumnName,
    columnType: AnyColumn,
    jdbcType: String,
    val parentSchema: SchemaName,
    val parentTable: TableName,
    val parentColumn: ColumnName,
    partOfCompositePrimaryKey: Boolean = false,
    comment: String? = null
) : DBColumnDefinition(
    schema,
    table,
    columnName,
    columnType,
    jdbcType,
    isSinglePrimaryKey = false,
    isCompositePrimaryKey = partOfCompositePrimaryKey,
    comment
) {

    val tableAndColumn = "${parentTable.metaDataObjectName}.${parentColumn.fieldName}"

    override fun asFactoryMethod(): String = """${column.simpleClassName()}(this, "$columnName", $tableAndColumn)"""

    override fun prettyPrint(): String =
        "     Foreign key column $tableName.$columnName maps to ${fullyQualifiedClassName()}. Parent: $parentSchema.$parentTable.$parentColumn"

    override fun toString(): String = columnName.value
}
