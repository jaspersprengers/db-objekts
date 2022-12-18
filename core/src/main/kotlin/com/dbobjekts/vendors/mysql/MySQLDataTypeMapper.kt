package com.dbobjekts.vendors.mysql

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.datatypemapper.ColumnTypeMapper
import com.dbobjekts.metadata.ColumnFactory


class MySQLDataTypeMapper : ColumnTypeMapper {

    override fun map(properties: ColumnMappingProperties): AnyColumn? {
        val nullable = properties.isNullable
        val col = properties.jdbcType.uppercase().trim()
        return when (col){
            "CHAR" -> ColumnFactory.varcharColumn(nullable)
            "VARCHAR" -> ColumnFactory.varcharColumn(nullable)
            "TINYTEXT" -> ColumnFactory.varcharColumn(nullable)
            "TEXT" -> ColumnFactory.varcharColumn(nullable)
            "MEDIUMTEXT" -> ColumnFactory.varcharColumn(nullable)
            "LONGTEXT" -> ColumnFactory.varcharColumn(nullable)

            "BLOB" -> ColumnFactory.blobColumn(nullable)
            "LONGBLOB" -> ColumnFactory.clobColumn(nullable)

            "TINYINT" -> ColumnFactory.byteColumn(nullable)
            "SMALLINT" -> ColumnFactory.integerColumn(nullable)
            "MEDIUMINT" -> ColumnFactory.integerColumn(nullable)
            "INT" -> ColumnFactory.integerColumn(nullable)
            "BIGINT" -> ColumnFactory.longColumn(nullable)

            "BINARY" -> ColumnFactory.varcharColumn(nullable)

            "ENUM" -> ColumnFactory.integerColumn(nullable)
            "SET" -> ColumnFactory.integerColumn(nullable)

            "FLOAT" -> ColumnFactory.doubleColumn(nullable)
            "DOUBLE" -> ColumnFactory.doubleColumn(nullable)
            "DECIMAL" -> ColumnFactory.doubleColumn(nullable)

            "DATE" -> ColumnFactory.dateColumn(nullable)
            "DATETIME" -> ColumnFactory.dateColumn(nullable)
            "TIMESTAMP" -> ColumnFactory.timeStampColumn(nullable)
            "TIME" -> ColumnFactory.timeColumn(nullable)

            "BOOLEAN" -> ColumnFactory.booleanColumn(nullable)
            else -> null
        }
    }

    companion object {

    }

}

