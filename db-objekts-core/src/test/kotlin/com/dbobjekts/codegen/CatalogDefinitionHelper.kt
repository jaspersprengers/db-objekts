package com.dbobjekts.codegen

import com.dbobjekts.codegen.metadata.DBCatalogDefinition
import com.dbobjekts.codegen.metadata.DBColumnDefinition
import com.dbobjekts.codegen.metadata.DBTableDefinition


class CatalogDefinitionHelper(val definition: DBCatalogDefinition) {

    fun getTable(schema: String? = null, table: String): DBTableDefinition? {
        return definition.schemas.filter { schema?.equals(it.schemaName.value, true) ?: true }.flatMap { it.tables }
            .filter { it.tableName.value.equals(table, true) }.firstOrNull()
    }

    fun getColumn(tableDef: DBTableDefinition, column: String): DBColumnDefinition? {
        return tableDef.columns.filter { it.columnName.value.equals(column, true) }.firstOrNull()
    }

    fun getForeignKey(tableDef: DBTableDefinition, column: String): DBColumnDefinition? {
        return tableDef.foreignKeys().filter { it.columnName.value.equals(column, true) }.firstOrNull()
    }

    companion object {
        fun forCatalog(definition: DBCatalogDefinition): CatalogDefinitionHelper {
            return CatalogDefinitionHelper(definition)
        }
    }
}
