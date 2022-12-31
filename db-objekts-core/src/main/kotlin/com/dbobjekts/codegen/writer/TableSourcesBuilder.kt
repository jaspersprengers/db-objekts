package com.dbobjekts.codegen.writer

import com.dbobjekts.api.Entity
import com.dbobjekts.api.PackageName
import com.dbobjekts.codegen.metadata.DBColumnDefinition
import com.dbobjekts.codegen.metadata.DBForeignKeyDefinition
import com.dbobjekts.codegen.metadata.DBTableDefinition
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.api.exception.DBObjektsException
import com.dbobjekts.api.exception.StatementBuilderException
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

    /**
     *
     */
/*    private fun generateFieldComment(column: DBColumnDefinition): TableSourcesBuilder {
        return this
    }*/


    fun build(): String {
        val updateMethodSourceBuilder = InsertMethodSourceBuilder(model)
        strBuilder.append("package ${model.packageName}\n\n")

        fun generateField(column: DBColumnDefinition) {
            strBuilder.appendLine("    val ${column.asFieldName()} = ${column.asFactoryMethod()}")
        }

        val importLineBuilder = ImportLineBuilder()
        val typeAliases = listOf(
            "api.AnyColumn"
        )
        val classesToImport = listOf(
            Table::class.java,
            Entity::class.java,
            StatementBuilderException::class.java,
            WriteQueryAccessors::class.java,
            HasUpdateBuilder::class.java,
            InsertBuilderBase::class.java,
            UpdateBuilderBase::class.java
        )
        val updateBuilderInterface = HasUpdateBuilder::class.java.simpleName

        typeAliases.forEach { importLineBuilder.add("com.dbobjekts.$it") }
        classesToImport.forEach { importLineBuilder.add(it.canonicalName) }

        generateImportsForForeignKeys().forEach { importLineBuilder.add(it) }
        strBuilder.append(importLineBuilder.build())
        strBuilder.appendLine()
        val tbl = model.asClassName()
        strBuilder.appendLine("""object $tbl:Table<${tbl}Row>("${model.tableName}"), $updateBuilderInterface<${tbl}UpdateBuilder, ${tbl}InsertBuilder> {""".trimMargin())
        model.columns.forEach {
            //generateFieldComment(it)
            generateField(it)
        }
        val columnNames = model.columns.map { it.asFieldName() }.joinToString(",")

        strBuilder.appendLine("    override val columns: List<AnyColumn> = listOf($columnNames)")
        strBuilder.appendLine(updateMethodSourceBuilder.sourceForToValue())
        strBuilder.appendLine(updateMethodSourceBuilder.sourceForMetaDataVal())
        strBuilder.appendLine("}")
        strBuilder.appendLine(updateMethodSourceBuilder.sourceForBuilderClasses())
        strBuilder.appendLine(updateMethodSourceBuilder.sourceForEntityClass())
        return strBuilder.toString()
    }

}
