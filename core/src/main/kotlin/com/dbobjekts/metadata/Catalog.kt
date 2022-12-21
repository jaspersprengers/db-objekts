package com.dbobjekts.metadata

import com.dbobjekts.vendors.Vendor
import com.dbobjekts.vendors.Vendors
import java.lang.IllegalStateException

open class Catalog (
    val vendor: String,
    val schemas: List<Schema> = listOf()
)  {

    constructor(vendor: Vendor, schemas: List<Schema> = listOf()) : this(vendor.name, schemas)

    val tables: List<Table>
    private val aliases: TableAliases

    init {
        schemas.forEach { it.withCatalog(this) }
        val builder = TableAliasesBuilder()
        schemas.forEach { builder.addSchema(it) }
        aliases = builder.build()
        tables = schemas.flatMap { it.tables }
    }

    internal fun assertContainsTable(table: Table): Table {
        if (!table.isInitialized()) {
            throw IllegalStateException(
                "Table ${table.tableName} is not associated with a Schema yet. " +
                        "This typically happens when it does not belong to the Catalog associated with the current TransactionManager or when you have not provided a Catalog when building the TransactionManager. You must provide a Catalog in order to use the metadata objects in q ueries."

            )
        }
        if (tables.none { it.schemaAndName() == table.schemaAndName() }){
            throw IllegalStateException("Table ${table.schemaAndName()} does not belong to the Catalog associated with the current TransactionManager")
        }
        return table
    }

    fun schemaByName(name: String): Schema? = schemas.find { it.schemaName.value.contentEquals(name, true) }

    fun aliasForTable(table: Table): String = aliases.aliasForSchemaAndTable(table.schemaName(), table.tableName)

    override fun toString(): String = this::class.java.canonicalName

}

internal object PlaceHolderCatalog : Catalog(Vendors.H2.name)
