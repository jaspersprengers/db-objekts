package com.dbobjekts.codegen.writer

import com.dbobjekts.api.PackageName
import com.dbobjekts.codegen.metadata.DBCatalogDefinition
import com.dbobjekts.codegen.metadata.DBSchemaDefinition
import com.dbobjekts.util.StringUtil
import org.slf4j.LoggerFactory

class SourcesGenerator(
    val basedir: String,
    val basePackage: PackageName,
    val catalog: DBCatalogDefinition
) {
    private val writer = SourceFileWriter()
    private val logger = LoggerFactory.getLogger(SourcesGenerator::class.java)

    fun generate() {
        catalog.schemas.forEach { schemaDefinition ->
            val schemaFileBuilder = SchemaCodeBuilder(schemaDefinition)
            writeSourceFile(
                schemaDefinition,
                schemaDefinition.schemaName.value,
                schemaFileBuilder.buildForApplication()
            )
            schemaDefinition.tables.forEach { tableDefinition ->
                val builder = TableSourcesBuilder(catalog.packageName, tableDefinition)
                val source = builder.build()
                writeSourceFile(schemaDefinition, tableDefinition.tableName.value, source)
            }
        }

        writeSourceFile(null, catalog.name, CatalogCodeBuilder(catalog).createFileSource())
        writeSourceFile(null, "Aliases", AliasCodeBuilder(catalog).createFileSource())
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
