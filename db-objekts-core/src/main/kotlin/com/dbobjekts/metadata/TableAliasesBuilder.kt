package com.dbobjekts.metadata

import com.dbobjekts.api.SchemaName
import com.dbobjekts.api.TableName
import com.dbobjekts.api.exception.CodeGenerationException
import com.dbobjekts.util.ObjectNameValidator
import com.dbobjekts.util.StringUtil

class SchemaAndTable(val schema: SchemaName, val table: TableName) : Comparable<SchemaAndTable> {

    override fun compareTo(other: SchemaAndTable): Int = when (val i = schema.value.compareTo(other.schema.value)) {
        0 -> table.value.compareTo(other.table.value)
        else -> i
    }

    override fun toString(): String = "${schema.value}.$table"
}

class TableAliases(private val data: Map<String, String>) {

    fun aliasForSchemaAndTable(schema: SchemaName, name: TableName): String = aliasForSchemaAndTable(SchemaAndTable(schema, name))


    fun aliasForSchemaAndTable(schemaAndTable: SchemaAndTable): String =
        data.getOrDefault(schemaAndTable.toString(), schemaAndTable.toString())

}

class TableAliasesBuilder {

    private val cache = mutableListOf<SchemaAndTable>()
    private val aliasCache = mutableSetOf<String>()

    fun addSchema(schema: Schema): TableAliasesBuilder {
        val entries = schema.tables.map { SchemaAndTable(schema.schemaName, it.tableName) }
        entries.forEach { add(it) }
        return this
    }

    fun addSchemaAndTables(schema: SchemaName, tableNames: List<TableName>): TableAliasesBuilder {
        val entries = tableNames.map { SchemaAndTable(schema, it) }
        entries.forEach { add(it) }
        return this
    }

    fun add(schemaAndName: SchemaAndTable): TableAliasesBuilder {
        cache.add(schemaAndName)
        return this
    }

    fun add(schemaAndNames: List<SchemaAndTable>): TableAliasesBuilder {
        cache.addAll(schemaAndNames)
        return this
    }

    fun build(): TableAliases {
        val map = cache.sorted().map {
            Pair(it.toString(), createAlias(it))
        }.toMap()
        return TableAliases(map)
    }

    private fun createAlias(schemaAndTable: SchemaAndTable): String {
        val tableAsClassName = schemaAndTable.table.metaDataObjectName

        val length = tableAsClassName.length
        val isCamelCase = StringUtil.isCamelCase(tableAsClassName)
        val alias = when {
            length <= 2 || !isCamelCase -> tableAsClassName.substring(0, length.coerceAtMost(2)).lowercase()
            else -> tableAsClassName.mapIndexed { index, character -> Pair(index, character) }
                .filter { it.first == 0 || it.second.isUpperCase() }
                .map { it.second.toString().lowercase() }
                .joinToString("")
        }
        val ret = if (!ObjectNameValidator.validate(alias) || aliasCache.contains(alias))
            tryWithIncrement(alias, 1) else alias
        aliasCache += ret
        return ret
    }

    private fun tryWithIncrement(alias: String, counter: Int): String {
        if (counter == 100)
            throw CodeGenerationException("Probable infinite loop detected in alias generation.")
        return "$alias$counter".let { if (!aliasCache.contains(it)) it else tryWithIncrement(alias, counter + 1) }
    }

}

