package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.PackageName
import com.dbobjekts.api.SchemaName

data class DBSchemaDefinition(override val packageName: PackageName,
                              val schemaName: SchemaName,
                              val tables: List<DBTableDefinition>,
                              val excludedTables: List<DBTableDefinition>): DBObjectDefinition {
    override fun toString(): String = schemaName.value

    val asPackage: String = schemaName.asPackage()

    fun prettyPrint(): String =
        """
           |Schema ${packageName.toString()}.${schemaName.value} has ${tables.size} tables.
           |${tables.map{it.prettyPrint()}.joinToString("\n")}"""
}
