package com.dbobjekts.integration.mariadb.catalog
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.integration.mariadb.catalog.core.Core
import com.dbobjekts.integration.mariadb.catalog.hr.Hr
object Catalogdefinition : Catalog("MARIADB", listOf(Core, Hr))