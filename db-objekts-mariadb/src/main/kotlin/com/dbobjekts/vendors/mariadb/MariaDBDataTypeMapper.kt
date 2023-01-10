package com.dbobjekts.vendors.mariadb

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.datatypemapper.VendorDefaultColumnTypeMapper
import com.dbobjekts.metadata.ColumnFactory


class MariaDBDataTypeMapper : VendorDefaultColumnTypeMapper {
    // See https://mariadb.com/kb/en/data-types/
    override fun map(properties: ColumnMappingProperties): AnyColumn? {
        val nullable = properties.isNullable

        val decTypes = Regex("(DECIMAL|DEC|NUMERIC|FIXED|NUMBER)")

        val col = properties.jdbcType.uppercase().trim()
        return when {

            col.startsWith("TINYINT") || col.startsWith("INT1") -> ColumnFactory.byteColumn(nullable)

            col == "BOOLEAN" -> ColumnFactory.booleanColumn(nullable)
            col.startsWith("SMALLINT") || col.startsWith("INT2") -> ColumnFactory.integerColumn(nullable)
            col.startsWith("MEDIUMINT") || col.startsWith("INT3") -> ColumnFactory.integerColumn(nullable)
            col.startsWith("INT") || col.startsWith("INT4") || col.startsWith("INTEGER") -> ColumnFactory.longColumn(nullable)

            col.startsWith("BIGINT") || col.startsWith("INT8") -> ColumnFactory.longColumn(nullable)
            col.matches(decTypes) -> ColumnFactory.bigDecimalColumn(nullable)
            col.startsWith("FLOAT") -> ColumnFactory.floatColumn(nullable)
            col.startsWith("DOUBLE") -> ColumnFactory.doubleColumn(nullable)
            col.startsWith("BIT") -> ColumnFactory.booleanColumn(nullable)

            col.contains("BINARY") -> ColumnFactory.byteArrayColumn(nullable)
            col == "CHAR BYTE" -> ColumnFactory.byteArrayColumn(nullable)//alias for binary

            col.contains("BLOB") -> ColumnFactory.byteArrayColumn(nullable)
            col.startsWith("CHAR") -> ColumnFactory.varcharColumn(nullable)
            col == "ENUM" -> ColumnFactory.varcharColumn(nullable)
            //col == "INET4" -> .integerColumn(nullable)
            //col == "INET6" -> .integerColumn(nullable)
            col == "JSON" -> ColumnFactory.varcharColumn(nullable)
            col.contains("TEXT") -> ColumnFactory.varcharColumn(nullable)

            //col == "ROW" -> .varcharColumn(nullable)
            col.startsWith("VARCHAR") -> ColumnFactory.varcharColumn(nullable)
            col == "SET" -> ColumnFactory.varcharColumn(nullable)
            //col == "UUID" -> .varcharColumn(nullable)

            col == "DATE" -> ColumnFactory.dateColumn(nullable)
            col.startsWith("TIMESTAMP") -> ColumnFactory.dateTimeColumn(nullable)
            col.startsWith("TIME") -> ColumnFactory.timeColumn(nullable)
            col.startsWith("DATETIME") -> ColumnFactory.dateTimeColumn(nullable)
            col.startsWith("YEAR") -> ColumnFactory.integerColumn(nullable)

            else -> null
        }
    }

    companion object {

    }

}


