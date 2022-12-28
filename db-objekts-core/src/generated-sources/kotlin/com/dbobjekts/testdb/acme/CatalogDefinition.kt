package com.dbobjekts.testdb.acme
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.testdb.acme.core.Core
import com.dbobjekts.testdb.acme.hr.Hr
import com.dbobjekts.testdb.acme.library.Library
object CatalogDefinition : Catalog("H2", listOf(Core, Hr, Library))