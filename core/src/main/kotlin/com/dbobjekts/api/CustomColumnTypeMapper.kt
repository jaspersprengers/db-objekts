package com.dbobjekts.api

import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.datatypemapper.ColumnTypeMapper
import com.dbobjekts.metadata.ColumnFactory
import com.dbobjekts.metadata.column.NonNullableColumn


abstract class CustomColumnTypeMapper<C : NonNullableColumn<*>>() : ColumnTypeMapper {

    abstract operator fun invoke(properties: ColumnMappingProperties): Class<C>?

    override fun map(properties: ColumnMappingProperties): AnyColumn? {
        return invoke(properties)?.let { ColumnFactory.forClass(it) }
    }


}
