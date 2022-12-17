package com.dbobjekts.codegen.configbuilders

import com.dbobjekts.codegen.datatypemapper.*
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
     * This is typically used for custom Column implementations that translate a character or number to (e.g.) an Enum or other business data object.
     * @param column the column name
     * @param columnType the appropriate type
     * @param schema if non-null, only look in provided schema. Otherwise, apply across the board
     * @param table if non-null, only look in provided schema. Otherwise, apply across the board
     */
    fun overrideTypeForColumnByName(
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
     * Overrides the vendor-specific default and sets a custom ColumnType for a specific jdbcType
     * For example, in certain cases the MySQL TINYINT type can be treated as a Boolean
     * @param jdbcType the JDBC type to search for
     * @param columnType the appropriate type
     * @param schema if non-null, only look in provided schema. Otherwise, apply across the board
     * @param table if non-null, only look in provided schema. Otherwise, apply across the board
     */
    fun overrideTypeForColumnByJDBCType(
        jdbcType: String,
        columnType: ColumnType,
        schema: String? = null,
        table: String? = null
    ): MappingConfigurer {
        mappers += JDBCTypeOverrideMapper(
            jdbcType = jdbcType,
            columnType = columnType,
            schema = schema,
            table = table
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
