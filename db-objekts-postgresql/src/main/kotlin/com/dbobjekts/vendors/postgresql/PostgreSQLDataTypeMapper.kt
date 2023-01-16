package com.dbobjekts.vendors.postgresql

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.datatypemapper.VendorDefaultColumnTypeMapper
import com.dbobjekts.metadata.ColumnFactory
import com.dbobjekts.metadata.DefaultTable


class PostgreSQLDataTypeMapper : VendorDefaultColumnTypeMapper {
    override fun map(properties: ColumnMappingProperties): AnyColumn? {
        val nullable = properties.isNullable

        val col = properties.jdbcType.lowercase().trim()
        return when (col) {
            //numeric
            "int2" -> ColumnFactory.integerColumn(nullable)
            "int4" -> ColumnFactory.integerColumn(nullable)
            "int8" -> ColumnFactory.longColumn(nullable)
            "numeric" -> ColumnFactory.bigDecimalColumn(nullable)
            "decimal" -> ColumnFactory.bigDecimalColumn(nullable)
            "float4" -> ColumnFactory.floatColumn(nullable)
            "float8" -> ColumnFactory.doubleColumn(nullable)

            //serial
            "smallserial" -> ColumnFactory.integerColumn(nullable)
            "serial" -> ColumnFactory.integerColumn(nullable)
            "bigserial" -> ColumnFactory.longColumn(nullable)

            //boolean
            "bool" -> ColumnFactory.booleanColumn(nullable)
            "bit" -> ColumnFactory.booleanColumn(nullable)

            //character
            "char" -> ColumnFactory.varcharColumn(nullable)
            "name" -> ColumnFactory.varcharColumn(nullable)
            "text" -> ColumnFactory.varcharColumn(nullable)
            "varchar" -> ColumnFactory.varcharColumn(nullable)
            "bpchar" -> ColumnFactory.varcharColumn(nullable)

            //byte array
            "varbit" -> ColumnFactory.byteArrayColumn(nullable)
            "bytea" -> ColumnFactory.byteArrayColumn(nullable)

            "money" -> moneyColumn(nullable)
            "oid" -> ColumnFactory.longColumn(nullable)

            //date and time
            "date" -> ColumnFactory.dateColumn(nullable)
            "time" -> ColumnFactory.timeColumn(nullable)
            "timetz" -> ColumnFactory.timeColumn(nullable)
            "timestamp" -> ColumnFactory.timeStampColumn(nullable)
            "timestamptz" -> ColumnFactory.offsetDateTimeColumn(nullable)
            "interval" -> intervalColumn(nullable)
            //custom types
            "xml" -> ColumnFactory.xmlColumn(nullable)
            "uuid" -> ColumnFactory.varcharColumn(nullable)
            "json" -> ColumnFactory.varcharColumn(nullable)
            "jsonb" -> ColumnFactory.varcharColumn(nullable)
            "pg_lsn" -> ColumnFactory.longColumn(nullable)
            "tsquery" -> ColumnFactory.varcharColumn(nullable)
            "tsvector" -> ColumnFactory.varcharColumn(nullable)
            "txid_snapshot" -> ColumnFactory.longColumn(nullable)
            "pg_snapshot" -> ColumnFactory.longColumn(nullable)
            //geometric types
            "point" -> ColumnFactory.varcharColumn(nullable)
            "line" -> ColumnFactory.varcharColumn(nullable)
            "lseg" -> ColumnFactory.varcharColumn(nullable)
            "box" -> ColumnFactory.varcharColumn(nullable)
            "path" -> ColumnFactory.varcharColumn(nullable)
            "polygon" -> ColumnFactory.varcharColumn(nullable)
            "circle" -> ColumnFactory.varcharColumn(nullable)
            //network address types
            "inet" -> ColumnFactory.varcharColumn(nullable)
            "cidr" -> ColumnFactory.varcharColumn(nullable)
            "macaddr" -> ColumnFactory.varcharColumn(nullable)
            "macaddr8" -> ColumnFactory.varcharColumn(nullable)
            else -> null
        }
    }

    private fun moneyColumn(nullable: Boolean) =
        if (nullable) NullableMoneyColumn(DefaultTable, "dummy") else MoneyColumn(DefaultTable, "dummy")

    private fun intervalColumn(nullable: Boolean) =
        if (nullable) NullableIntervalColumn(DefaultTable, "dummy") else IntervalColumn(DefaultTable, "dummy")
}
