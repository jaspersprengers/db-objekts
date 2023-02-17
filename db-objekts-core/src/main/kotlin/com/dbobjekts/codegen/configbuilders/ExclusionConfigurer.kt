package com.dbobjekts.codegen.configbuilders

import com.dbobjekts.api.ColumnExclusionFilter
import com.dbobjekts.api.SchemaExclusionFilter
import com.dbobjekts.api.TableExclusionFilter
import com.dbobjekts.api.exception.CodeGenerationException
import com.dbobjekts.codegen.CodeGenerator
import com.dbobjekts.codegen.metadata.ColumnMetaData
import com.dbobjekts.codegen.metadata.DBTableDefinition
import com.dbobjekts.codegen.metadata.TableMetaData

/**
 * Handles exclusions for the code generator engine, based on certain schemas, tables or columns.
 */
class ExclusionConfigurer(codeGenerator: CodeGenerator): AbstractConfigurer(codeGenerator) {

    internal var schemaFilters = mutableSetOf<SchemaExclusionFilter>()
    internal var tableFilters = mutableSetOf<TableExclusionFilter>()
    internal var columnFilters = mutableSetOf<ColumnExclusionFilter>()

    /**
     * Adds a custom [SchemaExclusionFilter] to the list of conditions to exclude certain schemas.
     */
    fun addSchemaFilter(filter: SchemaExclusionFilter): ExclusionConfigurer {
        schemaFilters += filter
        return this
    }

    /**
     * Adds a custom [TableExclusionFilter] to the list of conditions to exclude certain tables.
     */
    fun addTableFilter(filter: TableExclusionFilter): ExclusionConfigurer {
        tableFilters += filter
        return this
    }

    /**
     * Adds a custom [ColumnExclusionFilter] to the list of conditions to exclude certain columns.
     */
    fun addColumnFilter(filter: ColumnExclusionFilter): ExclusionConfigurer {
        columnFilters += filter
        return this
    }

    /**
     * Ignores the given schemas for code generation. Note that system schemas are excuded by default, e.g. SYS and  INFORMATION_SCHEMA
     */
    fun ignoreSchemas(vararg schemasToExclude: String): ExclusionConfigurer {
        schemasToExclude.forEach { addSchemaFilter(SchemaExclusionFilter(it, exactMatch = true)) }
        return this
    }

    /**
     * Ignores a column with an exact name. Can be applied for a specific schema/table, or anywhere.
     * @param name the name of the column
     * @param table applies filter only in specific table. When null, apply it in all schemas
     * @param schema applies filter only in specific schema. When null, apply it in all schemas
     */
    fun ignoreColumn(
        name: String,
        table: String? = null,
        schema: String? = null
    ): ExclusionConfigurer {
        addColumnFilter(ColumnExclusionFilter(name, exactMatch = true, schema = schema, table = table))
        return this
    }

    /**
     * Ignores a column by doing a contains match. Can be applied for a specific schema/table, or anywhere.
     *
     * Useful to exclude audit columns that are only written to by triggers, e.g. ignoreColumnPattern("_audit")
     * @param name the name of the column to match
     * @param table applies filter only in specific table. When null, apply it in all schemas
     * @param schema applies filter only in specific schema. When null, apply it in all schemas
     */
    fun ignoreColumnPattern(
        name: String,
        table: String? = null,
        schema: String? = null
    ): ExclusionConfigurer {
        addColumnFilter(ColumnExclusionFilter(name, exactMatch = false, schema = schema, table = table))
        return this
    }

    /**
     * Ignores a table by name. Can be applied for a specific schema, or in any schema.
     *
     *  @param name the name of the table
     * @param schema applies filter only in specific schema. When null, apply it in all schemas
     */
    fun ignoreTable(name: String, schema: String? = null): ExclusionConfigurer {
        addTableFilter(TableExclusionFilter(name, exactMatch = true, schema = schema))
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
            throw CodeGenerationException("Column $column cannot be marked for exclusion. It is either a primary key or foreign key.")
        }
        return markedForExclusion
    }
}
