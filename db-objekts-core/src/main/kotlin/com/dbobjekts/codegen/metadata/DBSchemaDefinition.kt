package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.PackageName
import com.dbobjekts.api.SchemaName
import com.dbobjekts.metadata.Schema

data class DBSchemaDefinition(
    override val packageName: PackageName,
    val schemaName: SchemaName,
    val tables: List<DBTableDefinition>,
    val excludedTables: List<DBTableDefinition>
) : DBObjectDefinition {
    override fun toString(): String = schemaName.value

    val asPackage: String = schemaName.asPackage()

    fun prettyPrint(): String =
        """
           |Schema ${packageName.toString()}.${schemaName.value} has ${tables.size} tables.
           |${tables.map { it.prettyPrint() }.joinToString("\n")}"""

    fun findTable(table: String): DBTableDefinition? =
        tables.firstOrNull { it.tableName.value.equals(table, true) }


    fun diff(schema: Schema): List<String> {
        val diffs = mutableListOf<String>()
        if (tables.size != schema.tables.size) {
            val inDb = tables.map { it.tableName.value }.joinToString(",")
            val inCatalog = schema.tables.map { it.tableName.value }.joinToString(",")
            diffs += ("DB schema $schema has ${tables.size} tables ($inDb), but catalog has ${schema.tables.size} ($inCatalog)}")
        }
        diffs += tables.flatMap { dbTableDef: DBTableDefinition ->
            val match = schema.tables.find { it.tableName.value.equals(dbTableDef.tableName.value, true) }
            if (match == null) listOf("DB table ${dbTableDef.schema.value}.${dbTableDef.tableName.value} not found in catalog") else dbTableDef.diff(
                match
            )
        }
        return diffs
    }
}
