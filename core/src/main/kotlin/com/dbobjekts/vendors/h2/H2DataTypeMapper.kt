package com.dbobjekts.vendors.h2

import com.dbobjekts.AnyColumn
import com.dbobjekts.codegen.datatypemapper.ColumnMappingProperties
import com.dbobjekts.codegen.datatypemapper.ColumnTypeMapper
import com.dbobjekts.metadata.Columns


class H2DataTypeMapper : ColumnTypeMapper {

    private val charOrVarchar = Regex("(?:CHAR|VARCHAR)\\((?:\\d+?)\\)")
    private val double = Regex("DOUBLE(?:\\(\\d+\\))?")
    private val anyInt = Regex("(?:SMALLINT|INT|INTEGER)(?:\\(\\d+\\))?")
    private val bigInt = Regex("(?:ID|BIGINT|IDENTITY)(?:\\(\\d+\\))?")

    override operator fun invoke(properties: ColumnMappingProperties): AnyColumn? {
        val nullable = properties.isNullable
        val col = properties.jdbcType.uppercase().trim()
        return when {
            anyInt.matches(col) -> Columns.integerColumn(nullable)
            bigInt.matches(col) -> mapIntegerType(9, nullable)
            charOrVarchar.matches(col)-> Columns.varcharColumn(nullable)
            double.matches(col) -> Columns.doubleColumn(nullable)
            col == "ENUM" -> Columns.integerColumn(nullable)
            col == "TINYINT" -> Columns.byteColumn(nullable)
            col == "BLOB" -> Columns.blobColumn(nullable)
            col == "CLOB" -> Columns.clobColumn(nullable)
            col == "CHAR" -> Columns.varcharColumn(nullable)
            col == "VARCHAR" -> Columns.varcharColumn(nullable)
            col == "FLOAT" -> Columns.floatColumn(nullable)
            col == "REAL" -> Columns.floatColumn(nullable)
            col == "TIME" -> Columns.timeColumn(nullable)
            col == "DATE" -> Columns.dateColumn(nullable)
            col == "TIMESTAMP" -> Columns.timeStampColumn(nullable)
            col == "TIMESTAMP WITH TIME ZONE" -> Columns.offsetDateTimeColumn(nullable)
            col == "BOOLEAN" -> Columns.booleanColumn(nullable)
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

