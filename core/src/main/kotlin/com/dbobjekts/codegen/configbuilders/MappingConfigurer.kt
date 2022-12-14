package com.dbobjekts.codegen.configbuilders

import com.dbobjekts.AnyColumn
import com.dbobjekts.codegen.datatypemapper.ColumnTypeMapper
import com.dbobjekts.codegen.datatypemapper.CustomColumnTypeMapper
import com.dbobjekts.codegen.datatypemapper.SequenceForPrimaryKeyMapper
import com.dbobjekts.codegen.datatypemapper.StandardColumnTypeMapper
import com.dbobjekts.metadata.column.ColumnType


class MappingConfigurer {

    internal val mappers: MutableList<ColumnTypeMapper> = mutableListOf()
    internal val sequenceMappers: MutableList<SequenceForPrimaryKeyMapper> = mutableListOf()

    fun addColumnTypeMapper(mapper: ColumnTypeMapper): MappingConfigurer {
        mappers += mapper
        return this
    }

    /**
     * Overrides the vendor-specific default and set a custom ColumnType for a specific column.
     * @param column the column name
     * @param columnType the appropriate type
     * @param schema if non-null, only look in provided schema. Otherwise, apply across the board
     * @param table if non-null, only look in provided schema. Otherwise, apply across the board
     */
    fun mapColumnToStandardType(
        column: String,
        columnType: ColumnType,
        schema: String? = null,
        table: String? = null,
        exactMatch: Boolean = false
    ): MappingConfigurer {
        mappers += StandardColumnTypeMapper(
            columnNamePattern = column,
            columnType = columnType,
            schema = schema,
            table = table,
            exactMatch = exactMatch
        )
        return this
    }

    /**
     * Overrides the vendor-specific default and set a custom ColumnType for a specific column.
     * @param column the column name
     * @param columnType the appropriate type
     * @param schema if non-null, only look in provided schema. Otherwise, apply across the board
     * @param table if non-null, only look in provided schema. Otherwise, apply across the board
     */
    fun mapColumnToCustomType(
        column: String,
        columnType: AnyColumn,
        schema: String? = null,
        table: String? = null,
        exactMatch: Boolean = false
    ): MappingConfigurer {
        mappers += CustomColumnTypeMapper(
            columnNamePattern = column,
            columnType = columnType,
            schema = schema,
            table = table,
            exactMatch = exactMatch
        )
        return this
    }

    fun sequenceForPrimaryKey(
        schema: String,
        table: String,
        column: String,
        sequence: String
    ): MappingConfigurer {
        sequenceMappers += SequenceForPrimaryKeyMapper(
            schema = schema,
            table = table,
            column = column,
            sequence = sequence
        )
        return this
    }

}
