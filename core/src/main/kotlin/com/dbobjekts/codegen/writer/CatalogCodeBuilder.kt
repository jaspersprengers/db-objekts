package com.dbobjekts.codegen.writer

import com.dbobjekts.codegen.ProgressLogger
import com.dbobjekts.codegen.metadata.DBCatalogDefinition

class CatalogCodeBuilder(val catalog: DBCatalogDefinition, private val logger: ProgressLogger) {
    private val strBuilder = StringBuilder()

    fun createFileSource(): String {
        strBuilder.append("package ${catalog.packageName}\n")
        strBuilder.append("import com.dbobjekts.metadata.Catalog\n")
        catalog.schemas.forEach { strBuilder.append("import ${it.fullyQualifiedClassName()}\n") }

        strBuilder.append(createObjectBody())
        return strBuilder.toString()
    }

    private fun createObjectBody(): String {
        val schemas = catalog.schemas.map { it.asClassName() }.joinToString(", ")
        return """
           object ${catalog.asClassName()} : Catalog("${catalog.vendor.vendorName}", listOf($schemas))
       """.trimIndent()

    }

}
