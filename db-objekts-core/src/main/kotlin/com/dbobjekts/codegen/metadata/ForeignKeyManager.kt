package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.ColumnName
import com.dbobjekts.api.SchemaName
import com.dbobjekts.api.TableName

data class ForeignKeyProperties(
    val col: ColumnName,
    val table: TableName,
    val schema: SchemaName,
    val parentSchema: SchemaName,
    val parentColumn: ColumnName,
    val parentTable: TableName
) {
    override fun toString(): String {
        return "$schema.$table.$col references $parentSchema.$parentTable.$parentColumn"
    }
}

class ForeignKeyManager(tableMetaData: List<TableMetaData>) {

    private val foreignKeys: List<ForeignKeyProperties> = tableMetaData.flatMap { it.foreignKeys }

    fun findForeignKey(schema: SchemaName, tableName: TableName, colName: ColumnName): ForeignKeyProperties? =
        foreignKeys.find { tableName == it.table && it.col == colName && it.schema == schema }

    fun findLinkedTables(schema: SchemaName, tableName: TableName): List<Pair<SchemaName, TableName>> {
        val parents: List<Pair<SchemaName, TableName>> =
            foreignKeys.filter { it.schema == schema && it.table == tableName }.map { Pair(it.parentSchema, it.parentTable) }
        val references = foreignKeys.filter { it.parentSchema == schema && it.parentTable == tableName }.map { Pair(it.schema, it.table) }
        return parents + references
    }

}
