package com.dbobjekts.api

import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.metadata.ColumnFactory
import com.dbobjekts.metadata.column.NonNullableColumn

/**
 * Used to write a custom mapper that determines the type of [com.dbobjekts.metadata.column.Column] based on the metadata provided.
 * Mostly you don't need such a degree of customization, and the normal mapping methods will be sufficient for your needs.
 * Example:
 * ```kotlin
 * class PostCodeMapper : CustomColumnTypeMapper<PostCodeColumn>() {
 *     override fun invoke(properties: ColumnMappingProperties): Class<VarcharColumn>? {
 *         return if ( properties.column == "postcode") PostCodeColumn else null
 *     }
 * }
 * ```
 *
 */
abstract class CustomColumnTypeMapper<C : NonNullableColumn<*>>() : ColumnTypeMapper {

    /**
     * Determines the Column type to use based on the provided [ColumnMappingProperties]
     * @return an instance of a [NonNullableColumn] or null if the mapping does not apply (which will be most cases)
     */
    abstract operator fun invoke(properties: ColumnMappingProperties): Class<C>?

    override fun map(properties: ColumnMappingProperties): AnyColumn? {
        return invoke(properties)?.let {
            val col = ColumnFactory.forClass(it)
            if (properties.isNullable) col.nullable else col
        }
    }


}
