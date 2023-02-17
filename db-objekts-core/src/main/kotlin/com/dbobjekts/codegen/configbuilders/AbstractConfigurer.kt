package com.dbobjekts.codegen.configbuilders

import com.dbobjekts.codegen.CodeGenerator

/**
 * Base class for all configuration builders
 */
abstract class AbstractConfigurer(protected val generator: CodeGenerator) {
    /**
     * Returns the parent [CodeGenerator] instance to allow for fluent syntax.
     * ```kotlin
     * CodeGenerator()
     *             .withDataSource(AcmeDB.dataSource)
     *         .configurePrimaryKeySequences()
     *             [...]
     *         .and().configureColumnTypeMapping()
     *             [...]
     *         .and().configureOutput()
     *             [...]
     *         .and().generateSourceFiles()
     * ```
     */
    fun and(): CodeGenerator = generator
}
