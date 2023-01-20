package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.AnyTable
import com.dbobjekts.api.PackageName
import com.dbobjekts.api.SchemaName
import com.dbobjekts.api.TableName


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

    override fun asClassName(): String = tableName.metaDataObjectName

    fun prettyPrint(): String =
        """
           |   Table ${packageName.toString()}.${schema.value}.$tableName $alias has ${columns.size} columns.
           |${columns.map { it.prettyPrint() }.joinToString(", ")}"""


    /**
     * Searches the current table for a [DBColumnDefinition] by name.
     */
    fun findColumn(column: String): DBColumnDefinition? =
        columns.firstOrNull { it.columnName.value.equals(column, true) }

    /**
     * Searches the current table for a [DBForeignKeyDefinition] by name.
     */
    fun findForeignKey(column: String): DBForeignKeyDefinition? =
        columns.firstOrNull { it.columnName.value.equals(column, true) && it is DBForeignKeyDefinition} as DBForeignKeyDefinition?

    /**
     * Searches the current table for a [DBGeneratedPrimaryKeyDefinition] by name.
     */
    fun findPrimaryKey(column: String): DBGeneratedPrimaryKeyDefinition? =
        columns.firstOrNull { it.columnName.value.equals(column, true) && it is DBGeneratedPrimaryKeyDefinition} as DBGeneratedPrimaryKeyDefinition?

    fun diff(codeObject: AnyTable): List<String> {
        val diffs = mutableListOf<String>()
        if (columns.size != codeObject.columns.size) {
            val inDb = columns.map { it.columnName.value }.joinToString(",")
            val inCatalog = codeObject.columns.map { it.nameInTable }.joinToString(",")
            diffs += ("DB table $codeObject has ${columns.size} columns ($inDb), but catalog has ${codeObject.columns.size} ($inCatalog)}")
        }
        diffs += columns.flatMap { dbColDef: DBColumnDefinition ->
            val match = codeObject.columns.find { it.nameInTable.equals(dbColDef.columnName.value, true) }
            if (match == null) listOf("DB column ${codeObject.tableName}.${dbColDef.columnName} not found in catalog") else dbColDef.diff(match)
        }
        return diffs
    }
}
