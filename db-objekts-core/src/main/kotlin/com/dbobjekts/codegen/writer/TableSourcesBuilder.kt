package com.dbobjekts.codegen.writer

import com.dbobjekts.api.PackageName
import com.dbobjekts.api.TableRowData
import com.dbobjekts.codegen.metadata.DBColumnDefinition
import com.dbobjekts.codegen.metadata.DBForeignKeyDefinition
import com.dbobjekts.codegen.metadata.DBTableDefinition
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.joins.JoinBase
import com.dbobjekts.metadata.joins.JoinType
import com.dbobjekts.metadata.joins.TableJoinChain
import com.dbobjekts.statement.WriteQueryAccessors
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase

class TableSourcesBuilder(
    val basePackage: PackageName,
    val model: DBTableDefinition
) {

    val strBuilder = StringBuilder()

    /**
     * FKs to parents in a different schema/package need to be imported
     */
    private fun generateImportsForForeignKeys(): List<String> =
        model.columns.filter {
            it is DBForeignKeyDefinition && it.parentSchema.value != model.schema.value
        }.map { col ->
            val fk = col as DBForeignKeyDefinition
            "${basePackage.createSubPackageForSchema(fk.parentSchema)}.${fk.parentTable.capitalCamelCase()}\n"
        }.distinct()

    fun build(): String {
        val detailedSourceBuilder = TableDetailsSourceBuilder(model)
        strBuilder.append("package ${model.packageName}\n\n")

        fun generateField(column: DBColumnDefinition) {
            val fk = if (column is DBForeignKeyDefinition) {
                "\n     *\n     * Foreign key to ${column.parentSchema.value}.${column.parentTable.value}.${column.parentColumn.value}"
            } else ""
            strBuilder.appendLine("    /**\n     * Represents db column ${column.schemaName.value}.${column.tableName.value}.${column.columnName}$fk\n     */")
            strBuilder.appendLine("    val ${column.asFieldName()} = ${column.asFactoryMethod()}")
        }

        val importLineBuilder = ImportLineBuilder()
        val typeAliases = listOf(
            "api.AnyColumn", "api.AnyTable"
        )
        val classesToImport = listOf(
            Table::class.java,
            TableRowData::class.java,
            WriteQueryAccessors::class.java,
            HasUpdateBuilder::class.java,
            JoinBase::class.java,
            JoinType::class.java,
            TableJoinChain::class.java,
            InsertBuilderBase::class.java,
            UpdateBuilderBase::class.java
        )
        val updateBuilderInterface = HasUpdateBuilder::class.java.simpleName

        typeAliases.forEach { importLineBuilder.add("com.dbobjekts.$it") }
        classesToImport.forEach { importLineBuilder.add(it.canonicalName) }

        generateImportsForForeignKeys().forEach { importLineBuilder.add(it) }
        strBuilder.appendLine(importLineBuilder.build())
        strBuilder.appendLine()
        val tbl = model.asClassName()
        strBuilder.appendLine(detailedSourceBuilder.sourceForTableComment())
        strBuilder.appendLine("""object $tbl:Table<${tbl}Row>("${model.tableName}"), $updateBuilderInterface<${tbl}UpdateBuilder, ${tbl}InsertBuilder> {""".trimMargin())
        model.columns.forEach {
            generateField(it)
        }
        val columnNames = model.columns.map { it.asFieldName() }.joinToString(",")
        strBuilder.appendLine("    override val columns: List<AnyColumn> = listOf($columnNames)")
        strBuilder.appendLine(detailedSourceBuilder.sourceForToValue())
        strBuilder.appendLine(detailedSourceBuilder.sourceForMetaDataVal())
        strBuilder.appendLine(detailedSourceBuilder.sourceForJoinMethods(basePackage))
        strBuilder.appendLine("}")
        strBuilder.appendLine(detailedSourceBuilder.sourceForJoinChainClass(basePackage))
        strBuilder.appendLine(detailedSourceBuilder.sourceForBuilderClasses())
        strBuilder.appendLine(detailedSourceBuilder.sourceForRowDataClass())
        return strBuilder.toString()
    }

}
