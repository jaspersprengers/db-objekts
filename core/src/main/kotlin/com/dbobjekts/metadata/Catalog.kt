package com.dbobjekts.metadata

open class Catalog(
    val vendor: String,
    val schemas: List<Schema>
) {

    val tables: List<Table>
    private val aliases: TableAliases

    init {
        schemas.forEach { it.withCatalog(this) }
        val builder = TableAliasesBuilder()
        schemas.forEach { builder.addSchema(it) }
        aliases = builder.build()
        tables = schemas.flatMap { it.tables }
    }

    fun schemaByName(name: String): Schema? = schemas.find { it.schemaName.value.contentEquals(name) }

    fun aliasForTable(table: Table): String = aliases.aliasForSchemaAndTable(table.schemaName(), table.tableName)

    override fun toString(): String = this::class.java.canonicalName

}

 object NilSchemaCatalog : Catalog("h2", listOf())
