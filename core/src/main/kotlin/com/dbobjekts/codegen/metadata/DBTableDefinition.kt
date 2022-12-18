package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.PackageName
import com.dbobjekts.api.SchemaName
import com.dbobjekts.api.TableName

data class DBTableDefinition(override val packageName: PackageName,
                             val schema: SchemaName,
                             val tableName: TableName,
                             val alias: String,
                             val columns: List<DBColumnDefinition>): DBObjectDefinition {
    override fun toString(): String = tableName.value

    fun foreignKeys(): List<DBForeignKeyDefinition> = columns.map { it as? DBForeignKeyDefinition }.filterNotNull()

    fun prettyPrint(): String =
        """
           |   Table ${packageName.toString()}.${schema.value}.$tableName $alias has ${columns.size} columns.
           |${columns.map{it.prettyPrint()}.joinToString(", ")}"""
}
