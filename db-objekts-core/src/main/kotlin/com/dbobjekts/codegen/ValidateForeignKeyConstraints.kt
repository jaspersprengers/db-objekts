package com.dbobjekts.codegen

import com.dbobjekts.api.SchemaName
import com.dbobjekts.api.TableName
import com.dbobjekts.codegen.metadata.DBCatalogDefinition

object ValidateForeignKeyConstraints {
    operator fun invoke(catalogDefinition: DBCatalogDefinition): Boolean {
        val excluded: Set<Pair<SchemaName, TableName>> = getExcluded(catalogDefinition)
        val included = catalogDefinition.schemas.flatMap { it.tables }
        return included.all {
            if (it.foreignKeys().isEmpty()) true else it.foreignKeys()
                .all { !excluded.contains(Pair(it.parentSchema, it.parentTable)) }
        }
    }

    fun reportMissing(catalogDefinition: DBCatalogDefinition): List<Pair<String, String>> {
        val excluded: Set<Pair<SchemaName, TableName>> = getExcluded(catalogDefinition)
        val included = catalogDefinition.schemas.flatMap { it.tables }
        val fks = included.flatMap { it.foreignKeys() }
        return fks.filter {
            excluded.contains(Pair(it.parentSchema, it.parentTable))
        }.map {
            Pair(
                it.parentSchema.value + "." + it.tableName.value + "." + it.columnName.value,
                it.parentSchema.value + "." + it.parentTable + "." + it.parentColumn.value
            )
        }
    }

    private fun getExcluded(catalogDefinition: DBCatalogDefinition): Set<Pair<SchemaName, TableName>> =
        catalogDefinition.schemas
            .flatMap { it.excludedTables }
            .map { Pair(it.schema, it.tableName) }
            .toSet()

}
