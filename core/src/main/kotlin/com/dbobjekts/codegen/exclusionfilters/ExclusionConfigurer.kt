package com.dbobjekts.codegen.exclusionfilters

import com.dbobjekts.codegen.metadata.ColumnMetaData
import com.dbobjekts.codegen.metadata.DBTableDefinition
import com.dbobjekts.codegen.metadata.TableMetaData


class ExclusionConfigurer() {

    internal var schemaFilters = mutableSetOf<SchemaExclusionFilter>()
    internal var tableFilters = mutableSetOf<TableExclusionFilter>()
    internal var columnFilters = mutableSetOf<ColumnExclusionFilter>()

    fun addSchemaFilter(filter: SchemaExclusionFilter): ExclusionConfigurer {
        schemaFilters += filter
        return this
    }

    fun addTableFilter(filter: TableExclusionFilter): ExclusionConfigurer {
        tableFilters += filter
        return this
    }

    fun addColumnFilter(filter: ColumnExclusionFilter): ExclusionConfigurer {
        columnFilters += filter
        return this
    }

    fun ignoreSchemas(vararg pattern: String): ExclusionConfigurer {
        pattern.forEach { addSchemaFilter(SchemaExclusionFilter(it, exactMatch = true)) }
        return this
    }

    /**
     * @param schema applies filter only in specific schema. When null, apply it in all schemas
     */
    fun ignoreColumns(
        pattern: String,
        table: String? = null,
        schema: String? = null,
        exactMatch: Boolean = false
    ): ExclusionConfigurer {
        addColumnFilter(ColumnExclusionFilter(pattern, exactMatch = exactMatch, schema = schema, table = table))
        return this
    }

    /**
     * @param schema applies filter only in specific schema. When null, apply it in all schemas
     */
    fun ignoreTables(pattern: String, schema: String? = null, exactMatch: Boolean = false): ExclusionConfigurer {
        addTableFilter(TableExclusionFilter(pattern, exactMatch = exactMatch, schema = schema))
        return this
    }

    /**
     * Partitions tables into a Pair with first element the excluded tables, secondly the included tables.
     *
     */
    internal fun partition(tables: List<DBTableDefinition>): Pair<List<DBTableDefinition>, List<DBTableDefinition>> {
        return tables.partition { tableIsExcluded(it.schema.value, it.tableName.value) }
    }

    internal fun schemaIsExcluded(schema: String): Boolean {
        return schemaFilters.any { filter ->
            filter(schema)
        }
    }

    internal fun tableIsExcluded(schema: String, table: String): Boolean {
        return tableFilters.any { filter ->
            filter(schema, table)
        }
    }

    internal fun columnIsExcluded(table: TableMetaData, column: ColumnMetaData): Boolean {
        val markedForExclusion = columnFilters.any { filter ->
            filter.invoke(table.schema.value, table.tableName.value, column.columnName.value)
        }
        val cannotExclude = column.isPrimaryKey ||
            table.foreignKeys.any { (it.schema == table.schema && it.col == column.columnName) || (it.parentSchema == table.schema && it.parentColumn == column.columnName) }
        if (markedForExclusion && cannotExclude) {
            throw IllegalStateException("Column $column cannot be marked for exclusion. It is either a primary key or foreign key.")
        }
        return markedForExclusion
    }
}
