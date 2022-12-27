package com.dbobjekts.codegen.configbuilders

import com.dbobjekts.codegen.datatypemapper.*

/**
 * Resolves the sequence name to be used for tables that require one for the primary key
 */
class PrimaryKeySequenceConfigurer {

    internal val resolvers: MutableList<SequenceForPrimaryKeyResolver> = mutableListOf()

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
    ): PrimaryKeySequenceConfigurer {
        resolvers += SequenceForPrimaryMapperByName(
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
     * This receives a [ColumnMappingProperties] object and returns the name of the sequence if applicable, or null if the column does not require a sequence.
     * Note that the mapper will only be invoked for primary key columns. There's no need to check it explicitly with [ColumnMappingProperties.isPrimaryKey]
     * Example:
     * ```kotlin
     *   generator.mappingConfigurer()
     *       .sequenceForPrimaryKeyResolver(AcmeSequenceMapper)
     *
     *   object AcmeSequenceMapper : SequenceForPrimaryKeyResolver {
     *      override fun invoke(properties: ColumnMappingProperties): String? =
     *          properties.table.value + "_SEQ"
     *  }
     *
     * ```
     * @mapper a function from [ColumnMappingProperties] to [String]
     */
    fun addCustomResolver(mapper: SequenceForPrimaryKeyResolver): PrimaryKeySequenceConfigurer {
        resolvers += mapper
        return this
    }

}
