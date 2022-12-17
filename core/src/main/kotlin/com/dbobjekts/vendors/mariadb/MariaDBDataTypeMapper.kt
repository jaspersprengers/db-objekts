package com.dbobjekts.vendors.mariadb

import com.dbobjekts.AnyColumn
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.datatypemapper.ColumnTypeMapper
import com.dbobjekts.metadata.Columns


class MariaDBDataTypeMapper : ColumnTypeMapper {
    // See https://mariadb.com/kb/en/data-types/
    override operator fun invoke(properties: ColumnMappingProperties): AnyColumn? {
        val nullable = properties.isNullable

        val decTypes = Regex("(DECIMAL|DEC|NUMERIC|FIXED|NUMBER)")

        val col = properties.jdbcType.uppercase().trim()
        return when {

            col == "TINYINT" || col == "INT1" -> Columns.byteColumn(nullable)

            col == "BOOLEAN" -> Columns.booleanColumn(nullable)
            col == "SMALLINT" || col == "INT2" -> Columns.integerColumn(nullable)
            col == "MEDIUMINT" || col == "INT3" -> Columns.integerColumn(nullable)
            col == "INT" || col == "INT4" || col == "INTEGER" -> Columns.longColumn(nullable)

            col == "BIGINT" || col == "INT8" -> Columns.longColumn(nullable)
            col.matches(decTypes) -> Columns.bigDecimalColumn(nullable)
            col == "FLOAT" -> Columns.floatColumn(nullable)
            col == "DOUBLE" -> Columns.doubleColumn(nullable)
            col == "DOUBLE PRECISION" -> Columns.doubleColumn(nullable)
            col == "BIT" -> Columns.booleanColumn(nullable)

            col.contains("BINARY") -> Columns.byteArrayColumn(nullable)
            col.contains("BLOB") -> Columns.byteArrayColumn(nullable)
            col == "CHAR" -> Columns.varcharColumn(nullable)
            col == "CHAR BYTE" -> Columns.byteArrayColumn(nullable)
            col == "ENUM" -> Columns.varcharColumn(nullable)
            //col == "INET4" -> .integerColumn(nullable)
            //col == "INET6" -> .integerColumn(nullable)
            col == "JSON" -> Columns.varcharColumn(nullable)
            col.contains("TEXT") -> Columns.varcharColumn(nullable)

            //col == "ROW" -> .varcharColumn(nullable)
            col == "VARCHAR" -> Columns.varcharColumn(nullable)
            col == "SET" -> Columns.varcharColumn(nullable)
            //col == "UUID" -> .varcharColumn(nullable)

            col == "DATE" -> Columns.dateColumn(nullable)
            col == "TIME" -> Columns.timeColumn(nullable)
            col == "DATETIME" -> Columns.dateColumn(nullable)
            col == "TIMESTAMP" -> Columns.timeStampColumn(nullable)
            col == "YEAR" -> Columns.timeStampColumn(nullable)

            else -> null
        }
    }

    companion object {

    }

}

