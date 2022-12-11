package com.dbobjekts.codegen.writer

import com.dbobjekts.PackageName
import com.dbobjekts.codegen.ProgressLogger
import com.dbobjekts.codegen.metadata.DBColumnDefinition
import com.dbobjekts.codegen.metadata.DBForeignKeyDefinition
import com.dbobjekts.codegen.metadata.DBTableDefinition

class TableSourcesBuilder(
    val basePackage: PackageName,
    val model: DBTableDefinition,
    private val logger: ProgressLogger
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
            "${basePackage.createSubPackageForSchema(fk.parentSchema)}.${fk.parentTable.capitalCamelCase()}\n"}.distinct()

    /**
     *
     */
    private fun generateFieldComment(column: DBColumnDefinition): TableSourcesBuilder {
       /* strBuilder.append("    /**\n")
        if (column.comment.isDefined) {
            strBuilder.append("     * ${column.comment.get}\n")
        }
        strBuilder.append("     * Corresponds to ${tableDefinition.name}.${column.name} of type ${column.typeName}\n")
        strBuilder.append("     */\n")*/
        return this
    }


    fun build(): String {
        val msb = InsertMethodSourceBuilder(model)
        strBuilder.append("package ${model.packageName}\n\n")

        fun generateField(column: DBColumnDefinition) {
            strBuilder.appendLine("    val ${column.asFieldName()} = ${column.asFactoryMethod()}")
        }

        val importLineBuilder = ImportLineBuilder()
        val imports = listOf("AnyColumn","AnyColumnAndValue","jdbc.ConnectionAdapter","metadata.Table","statement.update.ColumnForWriteMapContainerImpl","statement.update.HasUpdateBuilder","statement.insert.InsertBuilderBase","statement.update.UpdateBuilderBase")
        imports.forEach { importLineBuilder.add("com.dbobjekts.$it") }
        generateImportsForForeignKeys().forEach { importLineBuilder.add(it) }
        strBuilder.append(importLineBuilder.build())
        strBuilder.appendLine()
        val tbl = model.asClassName()
        strBuilder.appendLine("""object $tbl:Table("${model.tableName}"), HasUpdateBuilder<${tbl}UpdateBuilder, ${tbl}InsertBuilder> {""".trimMargin())
        model.columns.forEach {
            generateFieldComment(it)
            generateField(it)
        }
        val columnNames = model.columns.map { it.asFieldName() }.joinToString(",")
        strBuilder.appendLine("    override val columns: List<AnyColumn> = listOf($columnNames)")

        strBuilder.appendLine(msb.sourceForUpdateMethod())
        strBuilder.appendLine(msb.sourceForInsertMethod())
        strBuilder.appendLine("}")
        strBuilder.appendLine(msb.sourceForBuilderClasses())
        return strBuilder.toString()
    }

}
