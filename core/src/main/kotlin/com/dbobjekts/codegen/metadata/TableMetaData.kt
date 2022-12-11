package com.dbobjekts.codegen.metadata

import com.dbobjekts.SchemaName
import com.dbobjekts.TableName

data class TableMetaData(
    val schema: SchemaName,
    val tableName: TableName,
    val columns: List<ColumnMetaData> = listOf(),
    val foreignKeys: List<ForeignKeyProperties> = listOf()
) {

    override fun toString(): String {
        return """
            $schema.$tableName(
            ${columns.joinToString("\n")}
            )
            Keys: ( 
            ${foreignKeys.joinToString("\n")}
            )""".trimIndent()
    }

}



