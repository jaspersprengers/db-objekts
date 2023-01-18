package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.PackageName
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.vendors.Vendor

data class DBCatalogDefinition(
    override val packageName: PackageName,
    val vendor: Vendor,
    val schemas: List<DBSchemaDefinition>,
    val name: String
) : DBObjectDefinition {

    override fun toString(): String = name

    fun prettyPrint(): String =
        """${vendor} Database in package ${packageName} with ${schemas.size} schemas.
           |${schemas.map { it.prettyPrint() }.joinToString("\n")}"""

    fun findSchema(schema: String): DBSchemaDefinition? =
        schemas.firstOrNull { s -> s.schemaName.value.equals(schema, true) }


    fun findTable(schema: String?, table: String): DBTableDefinition? {
        return schemas.firstOrNull { s -> schema?.let { s.schemaName.value.equals(it, true) } ?: true }?.findTable(table)
    }

    internal fun diff(codeObject: Catalog): List<String> {
        val diffs = mutableListOf<String>()
        if (!codeObject.vendor.equals(vendor.name, true)) {
            diffs += ("DB vendor $vendor does not match catalog vendor ${codeObject.vendor}")
        } else if (schemas.size != codeObject.schemas.size) {
            val inDb = schemas.map { it.schemaName.value }.joinToString(",")
            val inCatalog = codeObject.schemas.map { it.schemaName.value }.joinToString(",")
            diffs += ("DB has ${schemas.size} schemas ($inDb), but catalog has ${codeObject.schemas.size} ($inCatalog)}")
        }
        diffs += schemas.flatMap { sc ->
            val match = codeObject.schemas.find { it.schemaName.value.equals(sc.schemaName.value, true) }
            if (match == null) listOf("DB schema ${sc.schemaName} not found in catalog") else sc.diff(match)
        }
        return diffs
    }
}
