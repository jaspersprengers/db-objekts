package com.dbobjekts.codegen.writer

import com.dbobjekts.codegen.metadata.DBSchemaDefinition

class SchemaCodeBuilder(val schema: DBSchemaDefinition) {
    private val strBuilder = StringBuilder()

    fun buildForApplication(): String {
        strBuilder.append("package ${schema.packageName}\n")
        strBuilder.append("import com.dbobjekts.metadata.Schema\n")
        val tables = schema.tables.map { it.asClassName() }.joinToString(", ")
        val source = """
           object ${schema.schemaName.metaDataObjectName} : Schema("${schema.schemaName.value}", listOf($tables))
      """.trimIndent()
        strBuilder.append(source)
        return strBuilder.toString()
    }


}
