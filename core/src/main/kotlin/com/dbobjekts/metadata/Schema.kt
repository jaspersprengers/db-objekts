package com.dbobjekts.metadata

import com.dbobjekts.SchemaName

open class Schema(
    var catalog: Catalog,
    val schemaName: SchemaName,
    val tables: List<Table>
) {

    constructor(name: String, tables: List<Table>) : this(NilSchemaCatalog, SchemaName(name), tables)

    init {
        tables.forEach { it.withSchema(this) }
    }

     fun withCatalog(catalog: Catalog) {
        this.catalog = catalog
    }

    val dottedName: String = if (schemaName.value.isEmpty()) "" else "$schemaName."

    fun aliasForTable(table: Table): String = catalog.aliasForTable(table)

    override fun toString(): String = schemaName.value

}
