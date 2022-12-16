package com.dbobjekts.vendors.h2

import com.dbobjekts.AnyColumn
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.datatypemapper.ColumnTypeMapper
import com.dbobjekts.metadata.Columns
import com.dbobjekts.metadata.DefaultTable
import org.slf4j.LoggerFactory

//see http://h2database.com/html/datatypes.html#decfloat_type

class H2DataTypeMapper : ColumnTypeMapper {
    private val logger = LoggerFactory.getLogger(H2DataTypeMapper::class.java)

    override operator fun invoke(properties: ColumnMappingProperties): AnyColumn? {
        val nullable = properties.isNullable
        val col = properties.jdbcType.uppercase().trim()
        return when (col) {
            //character columns as string
            "CHARACTER" -> Columns.varcharColumn(nullable)
            "CHARACTER VARYING" -> Columns.varcharColumn(nullable)
            "CHARACTER LARGE OBJECT" -> Columns.varcharColumn(nullable)
            "VARCHAR_IGNORECASE" -> Columns.varcharColumn(nullable)
            //binary columns
            "BINARY" -> Columns.byteArrayColumn(nullable)
            "BINARY VARYING" -> Columns.byteArrayColumn(nullable)
            "BINARY LARGE OBJECT" -> Columns.blobColumn(nullable)
            "JSON" -> Columns.byteArrayColumn(nullable)
            //tiny integers and boolean as INT
            "BOOLEAN" -> Columns.booleanColumn(nullable)
            "TINYINT" -> Columns.byteColumn(nullable)
            "SMALLINT" -> Columns.integerColumn(nullable)
            "INTEGER" -> Columns.integerColumn(nullable)
            //large numbers and floats
            "BIGINT" -> Columns.longColumn(nullable)
            "NUMERIC" -> Columns.bigDecimalColumn(nullable)
            "DECFLOAT" -> Columns.bigDecimalColumn(nullable)
            "REAL" -> Columns.floatColumn(nullable)
            "DOUBLE PRECISION" -> Columns.doubleColumn(nullable)
            //date and time
            "DATE" -> Columns.dateColumn(nullable)
            "TIME" -> Columns.timeColumn(nullable)
            "TIME WITH TIME ZONE" -> Columns.offsetDateTimeColumn(nullable)
            "TIMESTAMP" -> Columns.timeStampColumn(nullable)
            "TIMESTAMP WITH TIME ZONE" -> Columns.offsetDateTimeColumn(nullable)
            "ENUM" -> Columns.varcharColumn(nullable)
            //special types
            "UUID" -> if (nullable) UUID_NIL else UUID
            //currently unsupported
            "INTERVAL" -> {
                logger.warn("INTERVAL data type is not supported")
                return null
            }

            "JAVA_OBJECT" -> {
                logger.warn("JAVA_OBJECT data type is not supported")
                return null
            }

            "GEOMETRY" -> {
                logger.warn("GEOMETRY data type is not supported")
                return null
            }

            "ARRAY" -> {
                logger.warn("ARRAY data type is not supported")
                return null
            }

            "ROW" -> {
                logger.warn("ROW data type is not supported")
                return null
            }

            else -> null
        }
    }

    companion object {
        private val table = DefaultTable
        private const val DUMMY = "dummy"
        val SUPPORTED_TYPES = listOf<String>(
            "CHARACTER", "CHARACTER VARYING", "CHARACTER LARGE OBJECT",
            "VARCHAR_IGNORECASE",
            "BINARY",
            "BINARY VARYING",
            "BINARY LARGE OBJECT",
            "JSON",
            "BOOLEAN",
            "TINYINT",
            "SMALLINT",
            "INTEGER",
            "BIGINT",
            "NUMERIC",
            "DECFLOAT",
            "REAL",
            "DOUBLE PRECISION",
            "DATE",
            "TIME",
            "TIME WITH TIME ZONE",
            "TIMESTAMP",
            "TIMESTAMP WITH TIME ZONE",
            "ENUM",
            "UUID"
        )
        val UUID = UUIDColumn(table, DUMMY)
        val UUID_NIL = NullableUUIDColumn(table, DUMMY)

    }

}

