package com.dbobjekts.vendors.postgresql

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.datatypemapper.ColumnTypeMapper
import com.dbobjekts.metadata.ColumnFactory


class PostgreSQLDataTypeMapper : ColumnTypeMapper {

    override fun map(properties: ColumnMappingProperties): AnyColumn? {
        val nullable = properties.isNullable
        val col = properties.jdbcType.uppercase().trim()
        return when (col) {
            "bool" -> ColumnFactory.booleanColumn(nullable)
            "bit" -> ColumnFactory.booleanColumn(nullable)
            "int8" -> ColumnFactory.longColumn(nullable)
            "bigserial" -> ColumnFactory.longColumn(nullable)
            "oid" -> ColumnFactory.longColumn(nullable)
            "bytea" -> ColumnFactory.byteArrayColumn(nullable)
            "char" -> ColumnFactory.varcharColumn(nullable)
            "bpchar" -> ColumnFactory.varcharColumn(nullable)
            "numeric" -> ColumnFactory.bigDecimalColumn(nullable)
            "int4" -> ColumnFactory.integerColumn(nullable)
            "serial" -> ColumnFactory.integerColumn(nullable)
            "int2" -> ColumnFactory.shortColumn(nullable)
            "smallserial" -> ColumnFactory.shortColumn(nullable)
            "float4" -> ColumnFactory.floatColumn(nullable)
            "float8" -> ColumnFactory.doubleColumn(nullable)
            "money" -> ColumnFactory.doubleColumn(nullable)
            "name" -> ColumnFactory.varcharColumn(nullable)
            "text" -> ColumnFactory.varcharColumn(nullable)
            "varchar" -> ColumnFactory.varcharColumn(nullable)
            "date" -> ColumnFactory.dateColumn(nullable)
            "time" -> ColumnFactory.timeColumn(nullable)
            "timetz" -> ColumnFactory.timeColumn(nullable)
            "timestamp" -> ColumnFactory.timeStampColumn(nullable)
            "timestamptz" -> ColumnFactory.offsetDateTimeColumn(nullable)
            "cardinal_number" -> throw IllegalStateException("$col is not supported")
            "character_data" -> throw IllegalStateException("$col is not supported")
            "sql_identifier" -> throw IllegalStateException("$col is not supported")
            "time_stamp" -> throw IllegalStateException("$col is not supported")
            "yes_or_no" -> throw IllegalStateException("$col is not supported")
            "xml" -> ColumnFactory.timeStampColumn(nullable)
            "refcursor" -> throw IllegalStateException("$col is not supported")
            "_abc" -> throw IllegalStateException("$col is not supported")

            else -> null
        }
    }

    companion object {

    }

}
