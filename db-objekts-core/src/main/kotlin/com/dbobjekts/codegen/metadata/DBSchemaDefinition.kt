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

    fun diff(codeObject: Schema): List<String> {
        val diffs = mutableListOf<String>()
        if (tables.size != codeObject.tables.size){
            val inDb = tables.map { it.tableName.value }.joinToString(",")
            val inCatalog = codeObject.tables.map { it.tableName.value }.joinToString(",")
            diffs += ("DB schema $codeObject has ${tables.size} tables ($inDb), but catalog has ${codeObject.tables.size} ($inCatalog)}")
        }
        diffs += tables.flatMap { sc: DBTableDefinition ->
            val match = codeObject.tables.find { it.tableName.value.equals(sc.tableName.value, true) }
            if (match == null) listOf("DB table ${codeObject.schemaName}.${sc.tableName} not found in catalog") else sc.diff(match)
        }
        return diffs
    }
}
