package com.dbobjekts.vendors.postgresql

import com.dbobjekts.AnyColumn
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.datatypemapper.ColumnTypeMapper
import com.dbobjekts.metadata.Columns


class PostgreSQLDataTypeMapper : ColumnTypeMapper {

    override operator fun invoke(properties: ColumnMappingProperties): AnyColumn? {
        val nullable = properties.isNullable
        val col = properties.jdbcType.uppercase().trim()
        return when (col) {
            "bool" -> Columns.booleanColumn(nullable)
            "bit" -> Columns.booleanColumn(nullable)
            "int8" -> Columns.longColumn(nullable)
            "bigserial" -> Columns.longColumn(nullable)
            "oid" -> Columns.longColumn(nullable)
            "bytea" -> Columns.byteArrayColumn(nullable)
            "char" -> Columns.varcharColumn(nullable)
            "bpchar" -> Columns.varcharColumn(nullable)
            "numeric" -> Columns.bigDecimalColumn(nullable)
            "int4" -> Columns.integerColumn(nullable)
            "serial" -> Columns.integerColumn(nullable)
            "int2" -> Columns.shortColumn(nullable)
            "smallserial" -> Columns.shortColumn(nullable)
            "float4" -> Columns.floatColumn(nullable)
            "float8" -> Columns.doubleColumn(nullable)
            "money" -> Columns.doubleColumn(nullable)
            "name" -> Columns.varcharColumn(nullable)
            "text" -> Columns.varcharColumn(nullable)
            "varchar" -> Columns.varcharColumn(nullable)
            "date" -> Columns.dateColumn(nullable)
            "time" -> Columns.timeColumn(nullable)
            "timetz" -> Columns.timeColumn(nullable)
            "timestamp" -> Columns.timeStampColumn(nullable)
            "timestamptz" -> Columns.offsetDateTimeColumn(nullable)
            "cardinal_number" -> throw IllegalStateException("$col is not supported")
            "character_data" -> throw IllegalStateException("$col is not supported")
            "sql_identifier" -> throw IllegalStateException("$col is not supported")
            "time_stamp" -> throw IllegalStateException("$col is not supported")
            "yes_or_no" -> throw IllegalStateException("$col is not supported")
            "xml" -> Columns.timeStampColumn(nullable)
            "refcursor" -> throw IllegalStateException("$col is not supported")
            "_abc" -> throw IllegalStateException("$col is not supported")

            else -> null
        }
    }

    companion object {

    }

}
