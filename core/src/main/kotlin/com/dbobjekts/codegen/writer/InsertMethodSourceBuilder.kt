package com.dbobjekts.codegen.writer

import com.dbobjekts.codegen.metadata.DBColumnDefinition
import com.dbobjekts.codegen.metadata.DBGeneratedPrimaryKey
import com.dbobjekts.codegen.metadata.DBTableDefinition
import com.dbobjekts.codegen.metadata.ReservedKeywords
import com.dbobjekts.metadata.column.NullableColumn
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

    fun sourceForMetaDataVal(): String {
        val updateBuilder = "${tableName}UpdateBuilder"
        val insertBuilder = "${tableName}InsertBuilder"

        return "    override val metadata: WriteQueryAccessors<$updateBuilder, $insertBuilder> = WriteQueryAccessors($updateBuilder(), $insertBuilder())"
    }

    fun sourceForBuilderClasses(): String {

        fun writeMethod(data: FieldData, updateOrInsert: String = "Update", returnType: String) =  "    fun ${data.field}(value: ${data.fieldType}): $returnType = ct.put($tableName.${data.field}, value)"

        val (pk, allMethodsExceptPK) = fields.partition { it.autoGenPK }
        val (nullFields, nonNullFields) = allMethodsExceptPK.partition { it.nullable || it.autoGenPK}

        val mandatoryColumnsMethod = if (nonNullFields.isEmpty()) "" else
"""
    fun mandatoryColumns(${nonNullFields.map { f -> "${f.field}: ${f.fieldType}" }.joinToString(", ")}) : ${tableName}InsertBuilder {
${nonNullFields.map { f -> "      ct.put($tableName.${f.field}, ${f.field})" }.joinToString("\n")}
      return this
    }
"""
        val updateBuilder = "${tableName}UpdateBuilder"
        val insertBuilder = "${tableName}InsertBuilder"

return """
class $updateBuilder() : UpdateBuilderBase($tableName) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override fun data(): Set<AnyColumnAndValue> = ct.data

${allMethodsExceptPK.map { d -> writeMethod(d, "Update", updateBuilder) }.joinToString("\n")}
}

class $insertBuilder():InsertBuilderBase(){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override fun data(): Set<AnyColumnAndValue> = ct.data

${allMethodsExceptPK.map { d -> writeMethod(d, "Insert", insertBuilder) }.joinToString("\n")}
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
