package com.dbobjekts.vendors.mysql

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.datatypemapper.VendorDefaultColumnTypeMapper
import com.dbobjekts.metadata.ColumnFactory


class PostgreSQLDataTypeMapper : VendorDefaultColumnTypeMapper {
    override fun map(properties: ColumnMappingProperties): AnyColumn? {
        val nullable = properties.isNullable

        val decTypes = Regex("(DECIMAL|DEC|NUMERIC|FIXED|NUMBER)")

        val col = properties.jdbcType.uppercase().trim()
        return when {
            col.startsWith("TINYINT") || col.startsWith("INT1") -> ColumnFactory.byteColumn(nullable)
            else -> null
        }
    }

    companion object {

    }

}


