package com.dbobjekts.vendors.mysql

import com.dbobjekts.AnyColumn
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.datatypemapper.ColumnTypeMapper
import com.dbobjekts.metadata.Columns


class MySQLDataTypeMapper : ColumnTypeMapper {

    override operator fun invoke(properties: ColumnMappingProperties): AnyColumn? {
        val nullable = properties.isNullable
        val col = properties.jdbcType.uppercase().trim()
        return when (col){
            "CHAR" -> Columns.varcharColumn(nullable)
            "VARCHAR" -> Columns.varcharColumn(nullable)
            "TINYTEXT" -> Columns.varcharColumn(nullable)
            "TEXT" -> Columns.varcharColumn(nullable)
            "MEDIUMTEXT" -> Columns.varcharColumn(nullable)
            "LONGTEXT" -> Columns.varcharColumn(nullable)

            "BLOB" -> Columns.blobColumn(nullable)
            "LONGBLOB" -> Columns.clobColumn(nullable)

            "TINYINT" -> Columns.byteColumn(nullable)
            "SMALLINT" -> Columns.integerColumn(nullable)
            "MEDIUMINT" -> Columns.integerColumn(nullable)
            "INT" -> Columns.integerColumn(nullable)
            "BIGINT" -> Columns.longColumn(nullable)

            "BINARY" -> Columns.varcharColumn(nullable)

            "ENUM" -> Columns.integerColumn(nullable)
            "SET" -> Columns.integerColumn(nullable)

            "FLOAT" -> Columns.doubleColumn(nullable)
            "DOUBLE" -> Columns.doubleColumn(nullable)
            "DECIMAL" -> Columns.doubleColumn(nullable)

            "DATE" -> Columns.dateColumn(nullable)
            "DATETIME" -> Columns.dateColumn(nullable)
            "TIMESTAMP" -> Columns.timeStampColumn(nullable)
            "TIME" -> Columns.timeColumn(nullable)

            "BOOLEAN" -> Columns.booleanColumn(nullable)
            else -> null
        }
    }

    companion object {
        val numericTypes = setOf("int", "bigint", "decimal")
    }

}

