package com.dbobjekts.codegen.metadata

import com.dbobjekts.ColumnName
import com.dbobjekts.SchemaName
import com.dbobjekts.TableName

data class ForeignKeyProperties(
    val col: ColumnName,
    val table: TableName,
    val schema: SchemaName,
    val parentSchema: SchemaName,
    val parentColumn: ColumnName,
    val parentTable: TableName
){
    override fun toString(): String {
        return "$schema.$table.$col references $parentSchema.$parentTable.$parentColumn"
    }
}

class ForeignKeyManager(tableMetaData: List<TableMetaData>) {

    private val foreignKeys: List<ForeignKeyProperties> = tableMetaData.flatMap { it.foreignKeys }

    fun findForeignKey(schema: SchemaName, tableName: TableName, colName: ColumnName): ForeignKeyProperties? =
        foreignKeys.find { tableName == it.table && it.col == colName && it.schema == schema }

}
