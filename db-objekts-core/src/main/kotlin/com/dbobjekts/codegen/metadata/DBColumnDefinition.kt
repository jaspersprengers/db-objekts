package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.*
import com.dbobjekts.metadata.column.IsEnumColumn
import com.dbobjekts.metadata.column.IsForeignKey
import com.dbobjekts.metadata.column.SequenceKeyColumn

open class DBColumnDefinition(
    val schemaName: SchemaName,
    val tableName: TableName,
    val columnName: ColumnName,
    val column: AnyColumn,
    val jdbcType: String,
    val isSinglePrimaryKey: Boolean = false,
    val isCompositePrimaryKey: Boolean = false,
    val comment: String? = null
) : DBObjectDefinition {

    override val packageName: PackageName = PackageName.fromClass(column::class.java)

    /**
     * @return com.dbobjekts.acme.AddressColumn(this, "address")
     */
    open fun asFactoryMethod(): String {
        val optionalParam = if ( column is IsEnumColumn) ", ${column.valueClass.name}::class.java" else ""
        return """${column.simpleClassName()}(this, "$columnName"$optionalParam)"""
    }

    override fun fullyQualifiedClassName(): String = packageName.concat(column.simpleClassName()).toString()

    override fun toString(): String = columnName.value

    open fun prettyPrint(): String = "     Column $tableName.$columnName maps to ${fullyQualifiedClassName()}."

    fun diff(codeObject: AnyColumn): List<String> {
        val dbToStr = when (val dbColumn = this) {
            is DBForeignKeyDefinition -> "${column.qualifiedClassName()} to ${dbColumn.parentTable.value}.${dbColumn.parentColumn.value}"
            is DBSequenceKeyDefinition -> "${column.qualifiedClassName()} with ${schemaName}.${dbColumn.sequence}"
            else -> column.qualifiedClassName()
        }
        val codeToStr = when (val metadataCol = codeObject) {
            is IsForeignKey<*, *> -> "${metadataCol.qualifiedClassName()} to ${metadataCol.parentColumn.table.dbName}.${metadataCol.parentColumn.nameInTable}"
            is SequenceKeyColumn<*> -> "${metadataCol.qualifiedClassName()} with ${metadataCol.qualifiedSequence()}"
            else -> metadataCol.qualifiedClassName()
        }
        return if (dbToStr != codeToStr)
            return listOf("Type mismatch in $tableName.$columnName: DB class is $dbToStr, but metadata class is $codeToStr")
        else listOf()
    }

}
