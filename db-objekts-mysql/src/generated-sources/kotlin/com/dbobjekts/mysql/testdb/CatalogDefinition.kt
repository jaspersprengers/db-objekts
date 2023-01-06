package com.dbobjekts.mysql.testdb
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.mysql.testdb.core.Core
import com.dbobjekts.mysql.testdb.hr.Hr
object CatalogDefinition : Catalog(0, "MYSQL", listOf(Core, Hr))