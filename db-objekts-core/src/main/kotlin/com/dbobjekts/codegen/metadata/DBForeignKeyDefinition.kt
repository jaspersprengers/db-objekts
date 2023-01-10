package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.*

class DBForeignKeyDefinition(schema: SchemaName,
                             table: TableName,
                             columnName: ColumnName,
                             columnType: AnyColumn,
                             val parentSchema: SchemaName,
                             val parentTable: TableName,
                             val parentColumn: ColumnName,
                             partOfCompositePrimaryKey: Boolean = false,
                             comment: String? = null)
   : DBColumnDefinition(schema, table, columnName, columnType, false, partOfCompositePrimaryKey, comment) {

    val tableAndColumn = "${parentTable.capitalCamelCase()}.${parentColumn.lowerCamelCase()}"

    override fun asFactoryMethod(): String = """${fullyQualifiedClassName()}(this, "$columnName", $tableAndColumn)"""

    override fun prettyPrint(): String =
        "     Foreign key column $tableName.$columnName maps to ${fullyQualifiedClassName()}. Parent: $parentSchema.$parentTable.$parentColumn"

    override fun toString(): String = columnName.value
}
