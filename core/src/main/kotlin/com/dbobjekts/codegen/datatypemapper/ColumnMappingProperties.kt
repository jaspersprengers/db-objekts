package com.dbobjekts.codegen.datatypemapper

import com.dbobjekts.ColumnName
import com.dbobjekts.SchemaName
import com.dbobjekts.TableName
import com.dbobjekts.codegen.metadata.ColumnMetaData

data class ColumnMappingProperties(val schema: SchemaName,
                                   val table: TableName,
                                   val column: ColumnName,
                                   val jdbcType: String,
                                   val isNullable: Boolean,
                                   val isPrimaryKey: Boolean) {

    companion object {

        fun of(schema: String = "public",
               table: String = "dummy",
               column: String = "dummy",
               jdbcType: String,
               isNullable: Boolean = false,
               isPrimaryKey: Boolean = false) = ColumnMappingProperties(SchemaName(schema), TableName(table), ColumnName(column), jdbcType, isNullable, isPrimaryKey)

        fun fromMetaData(schema: SchemaName,
                         table: TableName,
                         meta: ColumnMetaData): ColumnMappingProperties = ColumnMappingProperties(schema, table, meta.columnName, meta.columnType, meta.nullable, meta.isPrimaryKey)
    }

}

