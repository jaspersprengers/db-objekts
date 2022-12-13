package com.dbobjekts.integration.h2.custom

import com.dbobjekts.AnyColumn
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.datatypemapper.ColumnTypeMapper
import com.dbobjekts.metadata.DefaultTable

object AddressTypeMapper : ColumnTypeMapper {
    override fun invoke(properties: ColumnMappingProperties): AnyColumn? =
        if (properties.column.value.uppercase() == "KIND") AddressTypeColumn(DefaultTable, properties.column.value) else null
}
