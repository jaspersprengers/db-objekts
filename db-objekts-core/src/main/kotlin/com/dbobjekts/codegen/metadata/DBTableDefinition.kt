package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.AnyTable
import com.dbobjekts.api.PackageName
import com.dbobjekts.api.SchemaName
import com.dbobjekts.api.TableName
import com.dbobjekts.metadata.Table
import java.lang.IllegalStateException


data class DBTableDefinition(
    override val packageName: PackageName,
    val schema: SchemaName,
    val tableName: TableName,
    val alias: String,
    val columns: List<DBColumnDefinition>,
    val linkedTables: List<Pair<SchemaName, TableName>>
) : DBObjectDefinition {
    override fun toString(): String = tableName.value

    fun foreignKeys(): List<DBForeignKeyDefinition> = columns.map { it as? DBForeignKeyDefinition }.filterNotNull()

    fun prettyPrint(): String =
        """
           |   Table ${packageName.toString()}.${schema.value}.$tableName $alias has ${columns.size} columns.
           |${columns.map { it.prettyPrint() }.joinToString(", ")}"""


    fun diff(codeObject: AnyTable): List<String> {
        val diffs = mutableListOf<String>()
        if (columns.size != codeObject.columns.size) {
            val inDb = columns.map { it.columnName.value }.joinToString(",")
            val inCatalog = codeObject.columns.map { it.nameInTable }.joinToString(",")
            diffs += ("DB table $codeObject has ${columns.size} columns ($inDb), but catalog has ${codeObject.columns.size} ($inCatalog)}")
        }
        diffs += columns.flatMap { sc: DBColumnDefinition ->
            val match = codeObject.columns.find { it.nameInTable.equals(sc.columnName.value, true) }
            if (match == null) listOf("DB column ${codeObject.tableName}.${sc.columnName} not found in catalog") else sc.diff(match)
        }
        return diffs
    }
}
