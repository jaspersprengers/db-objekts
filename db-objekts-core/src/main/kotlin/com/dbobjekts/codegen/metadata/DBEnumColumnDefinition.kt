package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.*

class DBEnumColumnDefinition<T : Enum<T>>(schema: SchemaName,
                             table: TableName,
                             columnName: ColumnName,
                             columnType: AnyColumn,
                             private val enumClass: Class<T>,
                             jdbcType: String,
                             isSinglePrimaryKey: Boolean = false,
                             isCompositePrimaryKey: Boolean = false,
                             comment: String? = null)
   : DBColumnDefinition(schema, table, columnName, columnType, jdbcType, isSinglePrimaryKey, isCompositePrimaryKey, comment) {

    override fun asFactoryMethod(): String = """${column.simpleClassName()}(this, "$columnName", ${enumClass.simpleName}::class.java)"""

    override fun toString(): String = columnName.value
}
