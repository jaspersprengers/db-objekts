package com.dbobjekts.codegen.datatypemapper

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.ColumnTypeMapper
import com.dbobjekts.api.exception.CodeGenerationException
import com.dbobjekts.metadata.DefaultTable
import com.dbobjekts.metadata.column.*

class ColumnTypeMapperForEnum<C : Enum<C>>(
    val columnNamePattern: String,
    val enumClass: Class<C>,
    val table: String? = null,
    val schema: String? = null,
    val exactMatch: Boolean = false
) : ColumnTypeMapper {

    fun invoke(properties: ColumnMappingProperties): Class<C>? {
        val tableMatch = table?.let { properties.table.value.equals(it, true) } ?: true
        val schemaMatch = schema?.let { properties.schema.value.equals(it, true) } ?: true
        return properties.column.value.let { col ->
            if (schemaMatch && tableMatch && if (exactMatch) columnNamePattern.equals(col, true) else col.contains(
                    columnNamePattern,
                    true
                )
            ) {
                enumClass
            } else null
        }
    }

    override fun map(properties: ColumnMappingProperties): AnyColumn? {
        return invoke(properties)?.let {
            val nullable = properties.isNullable
            val defaultType: AnyColumn? = properties.defaultMappingType
            return if (defaultType == null) null else when {
                defaultType is IntegerNumericColumn && nullable -> NullableEnumAsIntColumn(DefaultTable, "dummy", enumClass)
                defaultType is IntegerNumericColumn && !nullable -> EnumAsIntColumn(DefaultTable, "dummy", enumClass)
                defaultType is IsCharacterColumn && nullable -> NullableEnumAsStringColumn(DefaultTable, "dummy", enumClass)
                defaultType is IsCharacterColumn && !nullable -> EnumAsStringColumn(DefaultTable, "dummy", enumClass)
                else -> throw CodeGenerationException("Default column type ${defaultType!!::class.java} is not supported for mapping to an enum. It must be an integer numeric or varchar column.")
            }
        }
    }


}
