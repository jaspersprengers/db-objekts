package com.dbobjekts.integration.mariadb
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.integration.mariadb.core.Core
import com.dbobjekts.integration.mariadb.hr.Hr
object CoreCatalog : Catalog("MARIADB", listOf(Core, Hr))