package com.dbobjekts.codegen.metadata

import com.dbobjekts.AnyColumn
import com.dbobjekts.ColumnName
import com.dbobjekts.SchemaName
import com.dbobjekts.TableName

class DBForeignKeyDefinition(table: TableName,
                             columnName: ColumnName,
                             columnType: AnyColumn,
                             val parentSchema: SchemaName,
                             val parentTable: TableName,
                             val parentColumn: ColumnName,
                             comment: String? = null)
   : DBColumnDefinition(table, columnName, columnType, comment) {

    val tableAndColumn = "${parentTable.capitalCamelCase()}.${parentColumn.lowerCamelCase()}"

    override fun asFactoryMethod(): String = """${fullyQualifiedClassName()}(this, "$columnName", $tableAndColumn)"""

    override fun prettyPrint(): String =
        "     Foreign key column $tableName.$columnName maps to ${fullyQualifiedClassName()}. Parent: $parentSchema.$parentTable.$parentColumn"

    override fun toString(): String = columnName.value
}
