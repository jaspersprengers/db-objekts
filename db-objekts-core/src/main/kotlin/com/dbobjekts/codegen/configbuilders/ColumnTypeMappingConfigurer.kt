package com.dbobjekts.codegen.configbuilders

import com.dbobjekts.api.CustomColumnTypeMapper
import com.dbobjekts.codegen.datatypemapper.*
import com.dbobjekts.metadata.column.NonNullableColumn

/**
 * Handles custom mapping to [com.dbobjekts.metadata.column.Column] types
 */
class ColumnTypeMappingConfigurer {

    internal val mappers: MutableList<CustomColumnTypeMapper<*>> = mutableListOf()
    internal val sequenceMappers: MutableList<SequenceForPrimaryKeyResolver> = mutableListOf()

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
            exactMatch = true
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

    /**
     * For databases that support sequences, this sets the mapping between the table and the sequence to use for generating the primary key.
     * Example:
     * ```kotlin
     *  setSequenceNameForTable("hr", "employee", "id", "employee_seq")
     * ```
     * @schema the applicable schema
     * @table the applicable table
     * @column the applicable column (the primary key)
     * @sequence the sequence name
     */
    fun setSequenceNameForPrimaryKey(
        schema: String,
        table: String,
        column: String,
        sequence: String
    ): ColumnTypeMappingConfigurer {
        sequenceMappers += SequenceForPrimaryMapperByName(
            schema = schema,
            table = table,
            column = column,
            sequence = sequence
        )
        return this
    }

    /**
     * If you have many tables that use PKs with sequences, (and a consistent naming scheme!), you may choose to provide your own custom
     * implementation of a [SequenceForPrimaryKeyResolver] rather than [setSequenceNameForPrimaryKey] for each individual table.
     * Example:
     * ```kotlin
     *   generator.mappingConfigurer()
     *       .sequenceForPrimaryKeyResolver(AcmeSequenceMapper)
     *
     *   object AcmeSequenceMapper : SequenceForPrimaryKeyResolver {
     *      override fun invoke(properties: ColumnMappingProperties): String? =
     *          if (properties.isPrimaryKey) properties.table.value + "_SEQ" else null
     *  }
     *
     * ```
     * @mapper a function from [ColumnMappingProperties] to [String]
     */
    fun sequenceForPrimaryKeyResolver(mapper: SequenceForPrimaryKeyResolver): ColumnTypeMappingConfigurer {
        sequenceMappers += mapper
        return this
    }

}
