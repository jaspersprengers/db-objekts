package com.dbobjekts.vendors.h2

import com.dbobjekts.AnyColumn
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.datatypemapper.ColumnTypeMapper
import com.dbobjekts.metadata.Columns
import org.slf4j.LoggerFactory

//see http://h2database.com/html/datatypes.html#decfloat_type

class H2DataTypeMapper : ColumnTypeMapper {
    private val logger = LoggerFactory.getLogger(H2DataTypeMapper::class.java)

    override operator fun invoke(properties: ColumnMappingProperties): AnyColumn? {
        val nullable = properties.isNullable
        val col = properties.jdbcType.uppercase().trim()
        return when (col) {
            "CHARACTER" -> Columns.varcharColumn(nullable)
            "CHARACTER VARYING" -> Columns.varcharColumn(nullable)
            "CHARACTER LARGE OBJECT" -> Columns.varcharColumn(nullable)
            "VARCHAR_IGNORECASE" -> Columns.varcharColumn(nullable)
            "BINARY" -> Columns.byteArrayColumn(nullable)
            "BINARY VARYING" -> Columns.byteArrayColumn(nullable)
            "BINARY LARGE OBJECT" -> Columns.blobColumn(nullable)
            "BOOLEAN" -> Columns.booleanColumn(nullable)
            "TINYINT" -> Columns.byteColumn(nullable)
            "SMALLINT" -> Columns.integerColumn(nullable)
            "INTEGER" -> Columns.integerColumn(nullable)
            "BIGINT" -> Columns.longColumn(nullable)
            "NUMERIC" -> Columns.bigDecimalColumn(nullable)
            "REAL" -> Columns.floatColumn(nullable)
            "DOUBLE PRECISION" -> Columns.doubleColumn(nullable)
            "DECFLOAT" -> Columns.bigDecimalColumn(nullable)
            "DATE" -> Columns.dateColumn(nullable)
            "TIME" -> Columns.timeColumn(nullable)
            "TIME WITH TIME ZONE" -> Columns.offsetDateTimeColumn(nullable)
            "TIMESTAMP" -> Columns.timeStampColumn(nullable)
            "TIMESTAMP WITH TIME ZONE" -> Columns.offsetDateTimeColumn(nullable)
            "INTERVAL" -> {
                logger.error("INTERVAL not supported")
                return null
            }

            "JAVA_OBJECT" -> {
                logger.error("JAVA_OBJECT not supported")
                return null
            }

            "ENUM" -> Columns.varcharColumn(nullable)
            "GEOMETRY" -> {
                logger.error("GEOMETRY not supported")
                return null
            }

            "JSON" -> Columns.byteArrayColumn(nullable)
            "UUID" -> Columns.varcharColumn(nullable)
            "ARRAY" -> {
                logger.error("ARRAY not supported")
                return null
            }

            "ROW" ->  {
                logger.error("ROW not supported")
                return null
            }
            else -> null
        }
    }

    fun mapIntegerType(size: Int, nullable: Boolean): AnyColumn =
        if (size > 8)
            Columns.longColumn(nullable)
        else Columns.integerColumn(nullable)


    companion object {
        val numericTypes = setOf("int", "integer", "bigint")
    }

}

