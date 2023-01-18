package com.dbobjekts.codegen.writer

import com.dbobjekts.api.PackageName
import com.dbobjekts.codegen.metadata.DBCatalogDefinition
import com.dbobjekts.codegen.metadata.DBSchemaDefinition
import com.dbobjekts.util.StringUtil
import org.apache.commons.io.FileUtils
import org.slf4j.LoggerFactory
import java.io.File

class SourcesGenerator(
    val basedir: String,
    val basePackage: PackageName,
    val catalog: DBCatalogDefinition
) {
    private val logger = LoggerFactory.getLogger(SourcesGenerator::class.java)

    fun generate() {
        val writer = SourceFileWriter(basedir)

        fun writeSourceFile(
            schema: DBSchemaDefinition?,
            fileName: String,
            source: String
        ) {
            val forMattedFileName =
                if (StringUtil.isCamelCase(fileName)) StringUtil.initUpperCase(fileName) else StringUtil.snakeToCamel(fileName, true)

            val pkg = schema?.let { basePackage.concat(schema.asPackage) } ?: basePackage

            writer.write(
                source,
                pkg,
                fileName = "$forMattedFileName.kt"
            )
        }

        val rootpackageDir = File(writer.packagePath(basePackage))
        logger.warn("Deleting directory recursively: $rootpackageDir")
        FileUtils.deleteDirectory(rootpackageDir)

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
                writeSourceFile(schemaDefinition, tableDefinition.tableName.metaDataObjectName, source)
            }
        }

        writeSourceFile(null, catalog.name, CatalogCodeBuilder(catalog).createFileSource())
        writeSourceFile(null, "Aliases", AliasCodeBuilder(catalog).createFileSource())


    }


}
