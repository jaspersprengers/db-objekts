package com.dbobjekts.vendors.mariadb

import com.dbobjekts.AnyColumn
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.datatypemapper.ColumnTypeMapper
import com.dbobjekts.metadata.ColumnFactory


class MariaDBDataTypeMapper : ColumnTypeMapper {
    // See https://mariadb.com/kb/en/data-types/
    override fun map(properties: ColumnMappingProperties): AnyColumn? {
        val nullable = properties.isNullable

        val decTypes = Regex("(DECIMAL|DEC|NUMERIC|FIXED|NUMBER)")

        val col = properties.jdbcType.uppercase().trim()
        return when {

            col == "TINYINT" || col == "INT1" -> ColumnFactory.byteColumn(nullable)

            col == "BOOLEAN" -> ColumnFactory.booleanColumn(nullable)
            col == "SMALLINT" || col == "INT2" -> ColumnFactory.integerColumn(nullable)
            col == "MEDIUMINT" || col == "INT3" -> ColumnFactory.integerColumn(nullable)
            col == "INT" || col == "INT4" || col == "INTEGER" -> ColumnFactory.longColumn(nullable)

            col == "BIGINT" || col == "INT8" -> ColumnFactory.longColumn(nullable)
            col.matches(decTypes) -> ColumnFactory.bigDecimalColumn(nullable)
            col == "FLOAT" -> ColumnFactory.floatColumn(nullable)
            col == "DOUBLE" -> ColumnFactory.doubleColumn(nullable)
            col == "DOUBLE PRECISION" -> ColumnFactory.doubleColumn(nullable)
            col == "BIT" -> ColumnFactory.booleanColumn(nullable)

            col.contains("BINARY") -> ColumnFactory.byteArrayColumn(nullable)
            col.contains("BLOB") -> ColumnFactory.byteArrayColumn(nullable)
            col == "CHAR" -> ColumnFactory.varcharColumn(nullable)
            col == "CHAR BYTE" -> ColumnFactory.byteArrayColumn(nullable)
            col == "ENUM" -> ColumnFactory.varcharColumn(nullable)
            //col == "INET4" -> .integerColumn(nullable)
            //col == "INET6" -> .integerColumn(nullable)
            col == "JSON" -> ColumnFactory.varcharColumn(nullable)
            col.contains("TEXT") -> ColumnFactory.varcharColumn(nullable)

            //col == "ROW" -> .varcharColumn(nullable)
            col == "VARCHAR" -> ColumnFactory.varcharColumn(nullable)
            col == "SET" -> ColumnFactory.varcharColumn(nullable)
            //col == "UUID" -> .varcharColumn(nullable)

            col == "DATE" -> ColumnFactory.dateColumn(nullable)
            col == "TIME" -> ColumnFactory.timeColumn(nullable)
            col == "DATETIME" -> ColumnFactory.dateColumn(nullable)
            col == "TIMESTAMP" -> ColumnFactory.timeStampColumn(nullable)
            col == "YEAR" -> ColumnFactory.timeStampColumn(nullable)

            else -> null
        }
    }

    companion object {

    }

}

