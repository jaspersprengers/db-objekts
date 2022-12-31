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
    val autoGenPK: Boolean,
    val regularPK: Boolean
)

class InsertMethodSourceBuilder(tableDefinition: DBTableDefinition) {

    private val tableName = tableDefinition.asClassName()

    val fields: List<FieldData>
    val allFieldsExceptAutoPK: List<FieldData>
    val allFieldsExceptPK: List<FieldData>
    val nonNullFields: List<FieldData>
    val primaryKey: FieldData?

    init {
        fields = tableDefinition.columns.map { colDef ->
            val fieldName =
                ReservedKeywords.prependIfReserved(StringUtil.snakeToCamel(colDef.columnName.value.lowercase()))
            val isNullable = colDef.column is NullableColumn<*>
            val dataType = StringUtil.classToString(colDef.column.valueClass) + (if (isNullable) "?" else "")
            val defaultClause: String = getDefaultValue(colDef)?.let { " = $it" } ?: ""
            val autoGenPk = colDef is DBGeneratedPrimaryKey
            FieldData(fieldName, dataType, defaultClause, isNullable, autoGenPk, colDef.isPrimaryKey)
        }
        allFieldsExceptAutoPK = fields.filterNot { it.autoGenPK }
        allFieldsExceptPK = fields.filterNot { it.regularPK || it.autoGenPK }
        nonNullFields = allFieldsExceptAutoPK.filterNot { it.nullable || it.autoGenPK }
        primaryKey = fields.filter { it.regularPK || it.autoGenPK }.firstOrNull()
    }

    fun sourceForToValue(): String {
        val elements = fields.mapIndexed { i, field ->
            "values[$i] as ${field.fieldType}"
        }.joinToString(",")
        return "    override fun toValue(values: List<Any?>) = ${tableName}Row($elements)"
    }

    fun sourceForUpdateRowMethod(): String {
        if (primaryKey == null)
            return """
    override fun updateRow(entity: Entity<*, *>): Long = 
      throw RuntimeException("Sorry, but you cannot use entity-based update for table ${tableName}. There must be exactly one column marked as primary key.")                
            """

        val elements = fields.mapIndexed { i, field ->
            "      add($tableName.${field.field}, entity.${field.field})"
        }.joinToString("\n")
        val pkCol = primaryKey.field
        val source = """
    override fun updateRow(entity: Entity<*, *>): Long {
      entity as ${tableName}Row
$elements
      return where (${tableName}.$pkCol.eq(entity.$pkCol))
    }    
        """
        return source
    }

    fun sourceForEntityClass(): String {
        val elements = fields.map { f ->
            "  val ${f.field}: ${f.fieldType}"
        }.joinToString(",\n")
        val source = """
data class ${tableName}Row(
$elements    
) : Entity<${tableName}UpdateBuilder, ${tableName}InsertBuilder>(${tableName}.metadata())
        """
        return source
    }

    private fun sourceForInsertRowMethod(): String {
        val elements = allFieldsExceptAutoPK.mapIndexed { i, field ->
            "      add($tableName.${field.field}, entity.${field.field})"
        }.joinToString("\n")
        val source = """
    override fun insertRow(entity: Entity<*, *>): Long {
      entity as ${tableName}Row
$elements
      return execute()
    }    
        """
        return source
    }

    private fun sourceForMandatoryColumnsMethod(): String {
        return if (nonNullFields.isEmpty()) "" else
            """
    fun mandatoryColumns(${nonNullFields.map { f -> "${f.field}: ${f.fieldType}" }.joinToString(", ")}) : ${tableName}InsertBuilder {
${nonNullFields.map { f -> "      mandatory($tableName.${f.field}, ${f.field})" }.joinToString("\n")}
      return this
    }
"""
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

        fun writeMethod(data: FieldData, returnType: String) =
            "    fun ${data.field}(value: ${data.fieldType}): $returnType = put($tableName.${data.field}, value)"

        val updateRowMethodSource = sourceForUpdateRowMethod()
        val insertRowMethodSource = sourceForInsertRowMethod()
        val mandatoryColumnsMethod = sourceForMandatoryColumnsMethod()

        val updateBuilder = "${tableName}UpdateBuilder"
        val insertBuilder = "${tableName}InsertBuilder"

        return """
class $updateBuilder() : $updateBuilderBase($tableName) {
${allFieldsExceptAutoPK.map { d -> writeMethod(d, updateBuilder) }.joinToString("\n")}
$updateRowMethodSource
}

class $insertBuilder():$insertBuilderBase(){
${allFieldsExceptAutoPK.map { d -> writeMethod(d, insertBuilder) }.joinToString("\n")}
$mandatoryColumnsMethod
$insertRowMethodSource
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
