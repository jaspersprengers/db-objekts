package com.dbobjekts.codegen.writer

import com.dbobjekts.PackageName
import com.dbobjekts.codegen.ProgressLogger
import com.dbobjekts.codegen.metadata.DBCatalogDefinition
import com.dbobjekts.codegen.metadata.DBSchemaDefinition
import com.dbobjekts.util.StringUtil

class SourcesGenerator(
    val basedir: String?,
    val basePackage : PackageName,
    val writer: SourceWriter,
    val catalog: DBCatalogDefinition,
    private val logger: ProgressLogger
) {

    fun generate() {
        catalog.schemas.forEach { schemaDefinition ->
            val schemaFileBuilder = SchemaCodeBuilder(schemaDefinition, logger)
            writeSourceFile(
                schemaDefinition,
                schemaDefinition.schemaName.value,
                schemaFileBuilder.buildForApplication()
            )
            schemaDefinition.tables.forEach { tableDefinition ->
                val builder = TableSourcesBuilder(catalog.packageName, tableDefinition, logger)
                val source = builder.build()
                writeSourceFile(schemaDefinition, tableDefinition.tableName.value, source)
            }
        }

        writeSourceFile(null, catalog.name, CatalogCodeBuilder(catalog, logger).createFileSource())
        writeSourceFile(null, "Aliases", AliasCodeBuilder(catalog, logger).createFileSource())
    }

    private fun writeSourceFile(
        schema: DBSchemaDefinition?,
        fileName: String,
        source: String
    ) {
        val forMattedFileName = StringUtil.snakeToCamel(fileName, true)

        val pkg = schema?.let { basePackage.concat(schema.asPackage) } ?: basePackage

        writer.write(
            source,
            pkg,
            basedir,
            fileName = "$forMattedFileName.kt"
        )
    }

}
