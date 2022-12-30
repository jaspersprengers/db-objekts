package com.dbobjekts.codegen.writer

import com.dbobjekts.codegen.metadata.DBColumnDefinition
import com.dbobjekts.codegen.metadata.DBGeneratedPrimaryKey
import com.dbobjekts.codegen.metadata.DBTableDefinition
import com.dbobjekts.codegen.metadata.ReservedKeywords
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.metadata.column.NullableColumn
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase
import com.dbobjekts.util.StringUtil

 data class FieldData(
    val field: String,
    val fieldType: String,
    val defaultClause: String,
    val nullable: Boolean,
    val autoGenPK: Boolean
)

class InsertMethodSourceBuilder(tableDefinition: DBTableDefinition) {

    private val tableName = tableDefinition.asClassName()

     val fields: List<FieldData> = tableDefinition.columns.map { colDef ->
        val fieldName =
            ReservedKeywords.prependIfReserved(StringUtil.snakeToCamel(colDef.columnName.value.lowercase()))
        val isNullable = colDef.column is NullableColumn<*>
        val dataType = StringUtil.classToString(colDef.column.valueClass) + (if (isNullable) "?" else "")
        val defaultClause: String = getDefaultValue(colDef)?.let { " = $it" } ?: ""
        val autoGenPk = colDef is DBGeneratedPrimaryKey
        FieldData(fieldName, dataType, defaultClause, isNullable, autoGenPk)
    }

    fun sourceForToValue(): String {
        val elements = fields.mapIndexed { i,field ->
            "values[$i] as ${field.fieldType}"
        }.joinToString(",")
        return "    override fun toValue(values: List<Any?>) = ${tableName}Row($elements)"
    }

    fun sourceForStatefulEntity(): String {
        val elements = fields.mapIndexed { i,field ->
            "\n    val ${field.field}: ${field.fieldType}"
        }.joinToString(",")
        return "data class ${tableName}Row($elements)"
    }

    fun sourceForMetaDataVal(): String {

        val accessorClass = WriteQueryAccessors::class.java.simpleName
        val updateBuilder = "${tableName}UpdateBuilder"
        val insertBuilder = "${tableName}InsertBuilder"

        return "    override fun metadata(): $accessorClass<$updateBuilder, $insertBuilder> = $accessorClass($updateBuilder(), $insertBuilder())"
    }

    fun sourceForBuilderClasses(): String {
        val updateBuilderBase = UpdateBuilderBase::class.java.simpleName
        val insertBuilderBase = InsertBuilderBase::class.java.simpleName

        fun writeMethod(data: FieldData, returnType: String) =  "    fun ${data.field}(value: ${data.fieldType}): $returnType = put($tableName.${data.field}, value)"

        val allMethodsExceptPK = fields.filterNot { it.autoGenPK }
        val nonNullFields = allMethodsExceptPK.filterNot { it.nullable || it.autoGenPK}

        val mandatoryColumnsMethod = if (nonNullFields.isEmpty()) "" else
"""
    fun mandatoryColumns(${nonNullFields.map { f -> "${f.field}: ${f.fieldType}" }.joinToString(", ")}) : ${tableName}InsertBuilder {
${nonNullFields.map { f -> "      mandatory($tableName.${f.field}, ${f.field})" }.joinToString("\n")}
      return this
    }
"""
        val updateBuilder = "${tableName}UpdateBuilder"
        val insertBuilder = "${tableName}InsertBuilder"

return """
class $updateBuilder() : $updateBuilderBase($tableName) {
${allMethodsExceptPK.map { d -> writeMethod(d, updateBuilder) }.joinToString("\n")}
}

class $insertBuilder():$insertBuilderBase(){
   ${allMethodsExceptPK.map { d -> writeMethod(d, insertBuilder) }.joinToString("\n")}
$mandatoryColumnsMethod
}
"""
    }


    private fun getDefaultValue(colDef: DBColumnDefinition): String? {
        if (!(colDef.column is NullableColumn<*>))
            return null
        val clz = colDef.column.valueClass
        return if (!clz.isPrimitive) "null"
        else {
            when (clz.simpleName) {
                "byte" -> "0"
                "short" -> "0"
                "int" -> "0"
                "float" -> "0f"
                "long" -> "0l"
                "double" -> "0d"
                "boolean" -> "false"
                else -> null
            }
        }
    }


}
