package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.ColumnName
import com.dbobjekts.api.PackageName
import com.dbobjekts.api.TableName
import com.dbobjekts.metadata.column.IsForeignKey
import com.dbobjekts.metadata.column.SequenceKeyColumn
import java.lang.IllegalStateException

open class DBColumnDefinition(
    val tableName: TableName,
    val columnName: ColumnName,
    val column: AnyColumn,
    val comment: String? = null
) : DBObjectDefinition {

    override val packageName: PackageName = PackageName.fromClass(column::class.java)

    open fun asFactoryMethod(): String = """${column::class.java.name}(this, "$columnName")"""

    override fun fullyQualifiedClassName(): String = packageName.concat(column::class.java.simpleName).toString()

    override fun toString(): String = columnName.value

    open fun prettyPrint(): String = "     Column $tableName.$columnName maps to ${fullyQualifiedClassName()}."

    fun diff(codeObject: AnyColumn): List<String> {
        val dbToStr = when (val c = this) {
            is DBForeignKeyDefinition -> "${column.javaClass} to ${c.parentTable}.${c.parentColumn}"
            is DBSequenceKeyDefinition -> "${column.javaClass} with ${c.sequence}"
            else -> column.javaClass.toString()
        }
        val codeToStr = when (val c = codeObject) {
            is IsForeignKey<*, *> -> "${c.javaClass} to ${c.parentColumn.table.tableName.value}.${c.parentColumn.nameInTable}"
            is SequenceKeyColumn<*> -> "${c.javaClass} with ${c.qualifiedSequence()}"
            else -> c.javaClass.toString()
        }
        return if (dbToStr != codeToStr)
            return listOf("Type mismatch in $tableName.$columnName: DB specifies $dbToStr, catalog is $codeToStr")
        else listOf()
    }

}
