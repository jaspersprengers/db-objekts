package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.ColumnName
import com.dbobjekts.api.SchemaName
import com.dbobjekts.api.TableName

class DBAutoGeneratedKeyDefinition(schema: SchemaName,
                                   table: TableName,
                                   columnName: ColumnName,
                                   columnType: AnyColumn,
                                   comment: String? = null)
   : DBColumnDefinition(schema, table, columnName, columnType, true, false, comment), DBGeneratedPrimaryKeyDefinition, DBGeneratedPrimaryKey {

    override fun prettyPrint(): String =
        "     Auto-generated column $tableName.$columnName maps to ${fullyQualifiedClassName()}."

    override fun toString(): String = columnName.value
}
