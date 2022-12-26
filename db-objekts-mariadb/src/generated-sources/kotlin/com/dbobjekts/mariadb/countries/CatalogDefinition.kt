package com.dbobjekts.mariadb.countries
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.mariadb.countries.nation.Nation
object CatalogDefinition : Catalog("MARIADB", listOf(Nation))