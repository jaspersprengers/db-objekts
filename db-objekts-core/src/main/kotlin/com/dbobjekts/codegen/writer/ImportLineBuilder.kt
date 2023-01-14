package com.dbobjekts.codegen.writer

import com.dbobjekts.api.PackageName
import com.dbobjekts.api.SchemaName
import com.dbobjekts.api.TableName

internal class ImportLineBuilder(private val basePackage: PackageName) {

    private val cache = mutableSetOf<String>()

    fun addImportsForForeignKeys(schema: SchemaName, linkedTables: List<Pair<SchemaName, TableName>>) {
        linkedTables.filter { (s, t) -> s != schema }.forEach {
            addClass(it.first, it.second.capitalCamelCase())
            addClass(it.first, it.second.capitalCamelCase() + "JoinChain")
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
