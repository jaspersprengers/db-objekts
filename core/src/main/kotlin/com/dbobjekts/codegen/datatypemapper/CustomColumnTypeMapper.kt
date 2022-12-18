package com.dbobjekts.codegen.datatypemapper

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.ColumnFactory
import com.dbobjekts.metadata.column.NonNullableColumn


abstract class CustomColumnTypeMapper<C : NonNullableColumn<*>>() : ColumnTypeMapper {

    abstract operator fun invoke(properties: ColumnMappingProperties): Class<C>?

    override fun map(properties: ColumnMappingProperties): AnyColumn? {
        return invoke(properties)?.let { ColumnFactory.forClass(it) }
    }


}
