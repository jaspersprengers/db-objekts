package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.ColumnName
import com.dbobjekts.api.TableName

class DBSequenceKeyDefinition(table: TableName,
                              name: ColumnName,
                              val sequence: String,
                              columnType: AnyColumn,
                              comment: String? = null)
   : DBColumnDefinition(table, name, columnType, comment), DBPrimaryKeyDefinition, DBGeneratedPrimaryKey {

    override fun asFactoryMethod(): String = """${fullyQualifiedClassName()}(this, "$columnName", "$sequence")"""

    override fun prettyPrint(): String =
        "     Sequence primary key column $tableName.$columnName maps to ${fullyQualifiedClassName()}. Sequence: $sequence"

    override fun toString(): String = columnName.value

}
