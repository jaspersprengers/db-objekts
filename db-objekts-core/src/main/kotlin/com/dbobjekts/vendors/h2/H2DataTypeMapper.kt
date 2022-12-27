package com.dbobjekts.vendors.h2

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.datatypemapper.ColumnTypeMapper
import com.dbobjekts.codegen.datatypemapper.VendorDefaultColumnTypeMapper
import com.dbobjekts.metadata.ColumnFactory
import com.dbobjekts.metadata.DefaultTable
import org.slf4j.LoggerFactory

//see http://h2database.com/html/datatypes.html

class H2DataTypeMapper : VendorDefaultColumnTypeMapper {
    private val logger = LoggerFactory.getLogger(H2DataTypeMapper::class.java)

    override fun map(properties: ColumnMappingProperties): AnyColumn? {
        val nullable = properties.isNullable
        val col = properties.jdbcType.uppercase().trim()
        val objectColumnPattern = Regex("(JAVA_OBJECT|OBJECT|OTHER)")

        return when {
            //character columns as string
            col == "CHARACTER" -> ColumnFactory.varcharColumn(nullable)
            col == "CHARACTER VARYING" -> ColumnFactory.varcharColumn(nullable)
            col == "CHARACTER LARGE OBJECT" -> ColumnFactory.varcharColumn(nullable)
            col == "VARCHAR_IGNORECASE" -> ColumnFactory.varcharColumn(nullable)
            //binary columns
            col == "BINARY" -> ColumnFactory.byteArrayColumn(nullable)
            col == "BINARY VARYING" -> ColumnFactory.byteArrayColumn(nullable)
            col == "BINARY LARGE OBJECT" -> ColumnFactory.blobColumn(nullable)
            col == "JSON" -> ColumnFactory.byteArrayColumn(nullable)
            //tiny integers and boolean as INT
            col == "BOOLEAN" -> ColumnFactory.booleanColumn(nullable)
            col == "TINYINT" -> ColumnFactory.byteColumn(nullable)
            col == "SMALLINT" -> ColumnFactory.integerColumn(nullable)
            col == "INTEGER" -> ColumnFactory.integerColumn(nullable)
            //large numbers and floats
            col == "BIGINT" -> ColumnFactory.longColumn(nullable)
            col == "NUMERIC" -> ColumnFactory.bigDecimalColumn(nullable)
            col == "DECFLOAT" -> ColumnFactory.bigDecimalColumn(nullable)
            col == "REAL" -> ColumnFactory.floatColumn(nullable)
            col == "DOUBLE PRECISION" -> ColumnFactory.doubleColumn(nullable)
            //date and time
            col == "DATE" -> ColumnFactory.dateColumn(nullable)
            col == "TIME" -> ColumnFactory.timeColumn(nullable)
            col == "TIME WITH TIME ZONE" -> ColumnFactory.offsetDateTimeColumn(nullable)
            col == "TIMESTAMP" -> ColumnFactory.timeStampColumn(nullable)
            col == "TIMESTAMP WITH TIME ZONE" -> ColumnFactory.offsetDateTimeColumn(nullable)
            col == "ENUM" -> ColumnFactory.varcharColumn(nullable)
            //special types
            col == "UUID" -> if (nullable) UUID_NIL else UUID
            col.startsWith("INTERVAL") -> if (nullable) INTERVAL_NIL else INTERVAL
            //col.matches(objectColumnPattern) ->  Columns.

            col.contains("GEOMETRY") -> ColumnFactory.varcharColumn(nullable)

            col.contains("ARRAY") -> if (nullable) OBJECT_ARRAY_NIL else OBJECT_ARRAY

            col == "ROW" -> {
                logger.warn("ROW data type is not supported")
                return null
            }

            else -> null
        }
    }

    companion object {
        private val table = DefaultTable
        private const val DUMMY = "dummy"

        val UUID = UUIDColumn(table, DUMMY)
        val UUID_NIL = NullableUUIDColumn(table, DUMMY)

        val INTERVAL = IntervalColumn(table, DUMMY)
        val INTERVAL_NIL = NullableIntervalColumn(table, DUMMY)

        val OBJECT_ARRAY = ObjectArrayColumn(table, DUMMY)
        val OBJECT_ARRAY_NIL = NullableObjectArrayColumn(table, DUMMY)
    }

}

