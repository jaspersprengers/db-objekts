package com.dbobjekts.codegen.datatypemapper

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.ColumnName
import com.dbobjekts.api.SchemaName
import com.dbobjekts.api.TableName
import com.dbobjekts.codegen.metadata.ColumnMetaData

data class ColumnMappingProperties(val schema: SchemaName,
                                   val table: TableName,
                                   val column: ColumnName,
                                   /**
                                    * The JDBC type as returned for the DB's metadata
                                    */
                                   val jdbcType: String,
                                   /**
                                    * The vendor-specific mapping for the jdbc type
                                    */
                                   val vendorDefault: AnyColumn?,
                                   val isNullable: Boolean,
                                   val isPrimaryKey: Boolean) {

    companion object {

        fun of(schema: String = "public",
               table: String = "dummy",
               column: String = "dummy",
               jdbcType: String,
               isNullable: Boolean = false,
               isPrimaryKey: Boolean = false) = ColumnMappingProperties(SchemaName(schema), TableName(table), ColumnName(column), jdbcType,null, isNullable, isPrimaryKey)

        fun fromMetaData(schema: SchemaName,
                         table: TableName,
                         meta: ColumnMetaData): ColumnMappingProperties = ColumnMappingProperties(schema, table, meta.columnName, meta.columnType, null, meta.nullable, meta.isPrimaryKey)
    }
}
