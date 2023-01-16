package com.dbobjekts.codegen.configbuilders

import com.dbobjekts.api.CustomColumnTypeMapper
import com.dbobjekts.codegen.datatypemapper.*
import com.dbobjekts.metadata.column.NonNullableColumn

/**
 * Handles custom mapping to [com.dbobjekts.metadata.column.Column] types
 */
class ColumnTypeMappingConfigurer {

    internal val mappers: MutableList<CustomColumnTypeMapper<*>> = mutableListOf()

    /**
     * Adds a custom [CustomColumnTypeMapper] implementation, which are applied in order before the default mappings
     * for the vendor are applied.
     */
    fun addCustomColumnTypeMapper(mapper: CustomColumnTypeMapper<*>): ColumnTypeMappingConfigurer {
        mappers += mapper
        return this
    }

    /**
     * Overrides the vendor-specific default and set a custom ColumnType for a specific column.
     * This is typically used for custom Column implementations that translate a character or number to (e.g.) an Enum or other business data object.
     * @param column the column name
     * @param columnType the appropriate type to return when the column matches
     * @param schema if non-null, only look in provided schema. Otherwise, apply across the board
     * @param table if non-null, only look in provided schema. Otherwise, apply across the board
     */
    fun <C : NonNullableColumn<*>> setColumnTypeForName(
        column: String,
        columnType: Class<C>,
        schema: String? = null,
        table: String? = null
    ): ColumnTypeMappingConfigurer {
        mappers += ColumnTypeMapperByNameMatch(
            columnNamePattern = column,
            columnType = columnType,
            schema = schema,
            table = table,
            exactMatch = false
        )
        return this
    }

    /**
     * Overrides the vendor-specific default and set a custom ColumnType for a specific column.
     * This is typically used for custom Column implementations that translate a character or number to (e.g.) an Enum or other business data object.
     * @param columnPattern the column name
     * @param columnType the appropriate type to return when the patterns is matched
     * @param schema if non-null, only look in provided schema. Otherwise, apply across the board
     * @param table if non-null, only look in provided schema. Otherwise, apply across the board
     */
    fun <C : NonNullableColumn<*>> setColumnTypeForNamePattern(
        columnPattern: String,
        columnType: Class<C>,
        schema: String? = null,
        table: String? = null
    ): ColumnTypeMappingConfigurer {
        mappers += ColumnTypeMapperByNameMatch(
            columnNamePattern = columnPattern,
            columnType = columnType,
            schema = schema,
            table = table,
            exactMatch = false
        )
        return this
    }

    /**
     * Overrides the vendor-specific default and sets a custom ColumnType for a specific jdbcType
     * For example, in certain cases the MySQL TINYINT type can be treated as a Boolean
     * @param jdbcType the JDBC type to search for
     * @param columnType the vendor-specific JDBC type. See the appropriate ColumnTypeMapper implementation for each vendor
     * @param schema if non-null, limit to this schema.
     * @param table if non-null, limit to this table.
     */
    fun <C : NonNullableColumn<*>> setColumnTypeForJDBCType(
        jdbcType: String,
        columnType: Class<C>,
        schema: String? = null,
        table: String? = null
    ): ColumnTypeMappingConfigurer {
        mappers += JDBCTypeOverrideMapper(
            jdbcType = jdbcType,
            columnType = columnType,
            schema = schema,
            table = table
        )
        return this
    }

}
