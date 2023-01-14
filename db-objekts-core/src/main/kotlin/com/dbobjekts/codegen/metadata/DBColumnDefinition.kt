package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.*
import com.dbobjekts.metadata.column.IsForeignKey
import com.dbobjekts.metadata.column.SequenceKeyColumn

open class DBColumnDefinition(
    val schemaName: SchemaName,
    val tableName: TableName,
    val columnName: ColumnName,
    val column: AnyColumn,
    val isSinglePrimaryKey: Boolean = false,
    val isCompositePrimaryKey: Boolean = false,
    val comment: String? = null
) : DBObjectDefinition {

    override val packageName: PackageName = PackageName.fromClass(column::class.java)

    /**
     * @return com.dbobjekts.acme.AddressColumn(this, "address")
     */
    open fun asFactoryMethod(): String = """${column::class.java.simpleName}(this, "$columnName")"""

    override fun fullyQualifiedClassName(): String = packageName.concat(column::class.java.simpleName).toString()

    override fun toString(): String = columnName.value

    open fun prettyPrint(): String = "     Column $tableName.$columnName maps to ${fullyQualifiedClassName()}."

    fun diff(codeObject: AnyColumn): List<String> {
        val dbToStr = when (val dbColumn = this) {
            is DBForeignKeyDefinition -> "${column.javaClass} to ${dbColumn.parentTable}.${dbColumn.parentColumn}"
            is DBSequenceKeyDefinition -> "${column.javaClass} with ${schemaName}.${dbColumn.sequence}"
            else -> column.javaClass.toString()
        }
        val codeToStr = when (val metadataCol = codeObject) {
            is IsForeignKey<*, *> -> "${metadataCol.javaClass} to ${metadataCol.parentColumn.table.tableName}.${metadataCol.parentColumn.nameInTable}"
            is SequenceKeyColumn<*> -> "${metadataCol.javaClass} with ${metadataCol.qualifiedSequence()}"
            else -> metadataCol.javaClass.toString()
        }
        return if (dbToStr != codeToStr)
            return listOf("Type mismatch in $tableName.$columnName: DB class is $dbToStr, but metadata class is $codeToStr")
        else listOf()
    }

}
