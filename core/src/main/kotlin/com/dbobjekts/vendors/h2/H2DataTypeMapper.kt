package com.dbobjekts.vendors.h2

import com.dbobjekts.AnyColumn
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.datatypemapper.ColumnTypeMapper
import com.dbobjekts.metadata.Columns
import com.dbobjekts.metadata.DefaultTable
import org.slf4j.LoggerFactory

//see http://h2database.com/html/datatypes.html

class H2DataTypeMapper : ColumnTypeMapper {
    private val logger = LoggerFactory.getLogger(H2DataTypeMapper::class.java)

    override operator fun invoke(properties: ColumnMappingProperties): AnyColumn? {
        val nullable = properties.isNullable
        val col = properties.jdbcType.uppercase().trim()
        val objectColumnPattern = Regex("(JAVA_OBJECT|OBJECT|OTHER)")

        return when {
            //character columns as string
            col == "CHARACTER" -> Columns.varcharColumn(nullable)
            col == "CHARACTER VARYING" -> Columns.varcharColumn(nullable)
            col == "CHARACTER LARGE OBJECT" -> Columns.varcharColumn(nullable)
            col == "VARCHAR_IGNORECASE" -> Columns.varcharColumn(nullable)
            //binary columns
            col == "BINARY" -> Columns.byteArrayColumn(nullable)
            col == "BINARY VARYING" -> Columns.byteArrayColumn(nullable)
            col == "BINARY LARGE OBJECT" -> Columns.blobColumn(nullable)
            col == "JSON" -> Columns.byteArrayColumn(nullable)
            //tiny integers and boolean as INT
            col == "BOOLEAN" -> Columns.booleanColumn(nullable)
            col == "TINYINT" -> Columns.byteColumn(nullable)
            col == "SMALLINT" -> Columns.integerColumn(nullable)
            col == "INTEGER" -> Columns.integerColumn(nullable)
            //large numbers and floats
            col == "BIGINT" -> Columns.longColumn(nullable)
            col == "NUMERIC" -> Columns.bigDecimalColumn(nullable)
            col == "DECFLOAT" -> Columns.bigDecimalColumn(nullable)
            col == "REAL" -> Columns.floatColumn(nullable)
            col == "DOUBLE PRECISION" -> Columns.doubleColumn(nullable)
            //date and time
            col == "DATE" -> Columns.dateColumn(nullable)
            col == "TIME" -> Columns.timeColumn(nullable)
            col == "TIME WITH TIME ZONE" -> Columns.offsetDateTimeColumn(nullable)
            col == "TIMESTAMP" -> Columns.timeStampColumn(nullable)
            col == "TIMESTAMP WITH TIME ZONE" -> Columns.offsetDateTimeColumn(nullable)
            col == "ENUM" -> Columns.varcharColumn(nullable)
            //special types
            col == "UUID" -> if (nullable) UUID_NIL else UUID
            col.startsWith("INTERVAL") -> if (nullable) INTERVAL_NIL else INTERVAL
            col.matches(objectColumnPattern) ->  if (nullable) H2_OBJECT_NIL else H2_OBJECT

            col.contains("GEOMETRY") -> Columns.varcharColumn(nullable)

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

        val H2_OBJECT = H2ObjectColumn(table, DUMMY)
        val H2_OBJECT_NIL = NullableH2ObjectColumn(table, DUMMY)

        val OBJECT_ARRAY = ObjectArrayColumn(table, DUMMY)
        val OBJECT_ARRAY_NIL = NullableObjectArrayColumn(table, DUMMY)
    }

}

