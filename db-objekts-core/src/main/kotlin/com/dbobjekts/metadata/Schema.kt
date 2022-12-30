package com.dbobjekts.metadata

import com.dbobjekts.api.AnyTable
import com.dbobjekts.api.SchemaName

/**
 * Parent of all the generated [Schema] objects that represent the schemas in the database and act as metadata for the query engine.
 * As an end user you have no dealings directly with [Schema] implementations.
 */
open class Schema(
    var catalog: Catalog,
    val schemaName: SchemaName,
    val tables: List<Table<*>>
) {

    constructor(name: String, tables: List<AnyTable>) : this(PlaceHolderCatalog, SchemaName(name), tables)

    init {
        tables.forEach { it.withSchema(this) }
    }

    internal fun withCatalog(catalog: Catalog) {
        this.catalog = catalog
    }

    internal val dottedName: String = if (schemaName.value.isEmpty()) "" else "$schemaName."

    internal fun aliasForTable(table: AnyTable): String = catalog.aliasForTable(table)

    override fun toString(): String = schemaName.value

}
