package com.dbobjekts.codegen.validation

import com.dbobjekts.api.exception.CodeGenerationException
import com.dbobjekts.codegen.metadata.DBCatalogDefinition
import com.dbobjekts.metadata.Catalog
import java.lang.StringBuilder

/**
 * Information container of a code generation validation result.
 */
data class ValidationResult(
    private val catalog: Catalog,
    private val definition: DBCatalogDefinition
) {
    val differences: List<String>

    init {
        differences = definition.diff(catalog)
    }

    /**
     * Checks if there are any differences detected between [Catalog] and [DBCatalogDefinition] and puts the results in a [CodeGenerationException].
     * @throws com.dbobjekts.api.exception.CodeGenerationException
     */
    fun assertNoDifferences() {
        if (differences.isNotEmpty()) {
            val builder =
                StringBuilder("A mismatch was detected between Catalog ${catalog::class.java.name} and database metamodel ${definition.name}\n\n")
            differences.forEach { builder.append(it).append('\n') }
            throw CodeGenerationException(builder.toString())
        }
    }
}
