package com.dbobjekts.codegen.writer

import com.dbobjekts.codegen.ProgressLogger
import com.dbobjekts.codegen.metadata.DBCatalogDefinition
import com.dbobjekts.metadata.SchemaAndTable
import com.dbobjekts.metadata.TableAliasesBuilder

class AliasCodeBuilder(val catalog: DBCatalogDefinition, private val logger: ProgressLogger) {
  private val strBuilder = StringBuilder()

  fun createFileSource(): String {

    strBuilder.append("package ${catalog.packageName}\n")
    strBuilder.append("import com.dbobjekts.vendors.${catalog.vendor.asClassName}\n")
    //strBuilder.append("import com.dbobjekts.metadata.Catalog\n")
    catalog.schemas.forEach { strBuilder.append("import ${it.fullyQualifiedClassName()}\n") }

    catalog.schemas.flatMap {it.tables} .forEach { strBuilder.append("import ${it.fullyQualifiedClassName()}\n") }

    strBuilder.append(createObjectBody())
    return strBuilder.toString()
  }

  fun createObjectBody(): String {
    val schemaAndTables: List<SchemaAndTable> = catalog.schemas.flatMap { schema -> schema.tables.map { SchemaAndTable(schema.schemaName, it.tableName) } }

    val builder = TableAliasesBuilder()
    builder.add(schemaAndTables)
    val aliases = builder.build()

    val lines = schemaAndTables.map {st -> "    val ${aliases.aliasForSchemaAndTable(st)} = ${st.table.capitalCamelCase()}" }


      return """
object Aliases {
${lines.joinToString("\n")}
}
     """

  }

}
