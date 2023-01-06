package com.dbobjekts.mariadb.testdb
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.mariadb.testdb.core.Core
import com.dbobjekts.mariadb.testdb.hr.Hr
import com.dbobjekts.mariadb.testdb.nation.Nation
object CatalogDefinition : Catalog(0, "MARIADB", listOf(Core, Hr, Nation))