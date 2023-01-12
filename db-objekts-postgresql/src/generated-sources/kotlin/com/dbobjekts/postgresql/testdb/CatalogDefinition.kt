package com.dbobjekts.postgresql.testdb
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.postgresql.testdb.core.Core
import com.dbobjekts.postgresql.testdb.hr.Hr
/**
 * Auto-generated metadata object representing a database consisting of one or more schemas.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.
 *
 * A Catalog implementation provides access to all the metadata of a database and must be supplied to the `TransactionManager` upon configuration. 
 */
object CatalogDefinition : Catalog(0, "POSTGRESQL", listOf(Core, Hr))