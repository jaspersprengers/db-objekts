package com.dbobjekts.codegen.metadata

import com.dbobjekts.AnyColumn
import com.dbobjekts.ColumnName
import com.dbobjekts.PackageName
import com.dbobjekts.TableName

open class DBColumnDefinition(val tableName: TableName,
                              val columnName: ColumnName,
                              val column: AnyColumn,
                              val comment: String? = null): DBObjectDefinition {

    override val packageName: PackageName = PackageName.fromClass(column::class.java)

    open fun asFactoryMethod(): String = """${column::class.java.name}(this, "$columnName")"""

    override fun fullyQualifiedClassName(): String = packageName.concat(column::class.java.simpleName).toString()

    override fun toString(): String = columnName.value

    open fun prettyPrint(): String = "     Column $tableName.$columnName maps to ${fullyQualifiedClassName()}."

}
