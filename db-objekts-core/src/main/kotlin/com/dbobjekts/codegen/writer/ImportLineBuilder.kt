package com.dbobjekts.codegen.writer

import com.dbobjekts.api.PackageName
import com.dbobjekts.api.SchemaName
import com.dbobjekts.api.TableName
import com.dbobjekts.codegen.metadata.DBForeignKeyDefinition

internal class ImportLineBuilder(private val basePackage: PackageName) {

    private val cache = mutableSetOf<String>()

    fun addImportsForForeignKeys(schema: SchemaName, linkedTables: List<DBForeignKeyDefinition>) {
        linkedTables.filter { fk -> fk.parentSchema != schema }.forEach {
            addClass(it.parentSchema, it.parentTable.capitalCamelCase())
        }
    }

    fun addClass(schema: SchemaName, clz: String): ImportLineBuilder {
        add("${basePackage.createSubPackageForSchema(schema)}.$clz")
        return this
    }

    fun addClasses(vararg classes: Class<*>): ImportLineBuilder {
        classes.forEach { add(it.canonicalName) }
        return this
    }

    fun add(portion: String): ImportLineBuilder {
        cache.add(portion)
        return this
    }

    fun build(): String {
        return cache.toList().sorted().map {
            "import $it"
        }.joinToString("\n")
    }

}
