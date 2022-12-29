package com.dbobjekts.metadata

import com.dbobjekts.api.SchemaName
import com.dbobjekts.api.TableName

class SchemaAndTable(val schema: SchemaName, val table: TableName) : Comparable<SchemaAndTable> {

    override fun compareTo(other: SchemaAndTable): Int = when (val i = schema.value.compareTo(other.schema.value)) {
        0 -> table.value.compareTo(other.table.value)
        else -> i
    }

    override fun toString(): String = "${schema.value}.$table"
}

class TableAliases(private val data: Map<String, String>) {

    fun aliasForSchemaAndTable(schema: SchemaName, name: TableName): String = aliasForSchemaAndTable(SchemaAndTable(schema, name))


    fun aliasForSchemaAndTable(schemaAndTable: SchemaAndTable): String = data.getOrDefault(schemaAndTable.toString(), schemaAndTable.toString())

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
        val entries = tableNames.map  {SchemaAndTable (schema, it)}
        entries.forEach {add(it)}
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
        val map = cache.sorted().map  {
                Pair(it.toString(), createAlias(it))
            }.toMap()
        return TableAliases(map)
    }

    private fun createAlias(schemaAndTable: SchemaAndTable): String  {
        val tableAsClassName = schemaAndTable.table.capitalCamelCase()
        val alias = tableAsClassName.indices
            .map { Pair(it, tableAsClassName.toCharArray().get(it) )}
            .filter { it.first == 0 || it.second.isUpperCase() }
            .map { it.second.toString().lowercase() }
        .joinToString("")

        val ret = if (aliasCache.contains(alias)) tryWithIncrement(alias, 1) else alias
        aliasCache += ret
        return ret
    }

    private fun validate(str: String) =
        if (str.contains("_")) throw IllegalArgumentException("value must be camel case") else str

    private fun tryWithIncrement(alias: String, counter: Int): String {
        val concat = "$alias$counter"
        return if (!aliasCache.contains(concat)) concat else tryWithIncrement(alias, counter + 1)
    }

}

