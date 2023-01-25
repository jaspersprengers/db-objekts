package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.ColumnName
import com.dbobjekts.api.SchemaName
import com.dbobjekts.api.TableName

class DBSequenceKeyDefinition(schema: SchemaName,
                              table: TableName,
                              name: ColumnName,
                              val sequence: String,
                              columnType: AnyColumn,
                              jdbcType: String,
                              comment: String? = null)
   : DBColumnDefinition(schema, table, name, columnType, jdbcType, true, false, comment), DBGeneratedPrimaryKeyDefinition, DBGeneratedPrimaryKey {

    override fun asFactoryMethod(): String = """${column.simpleClassName()}(this, "$columnName", "$sequence")"""

    override fun prettyPrint(): String =
        "     Sequence primary key column $tableName.$columnName maps to ${fullyQualifiedClassName()}. Sequence: $sequence"

    override fun toString(): String = columnName.value

}
