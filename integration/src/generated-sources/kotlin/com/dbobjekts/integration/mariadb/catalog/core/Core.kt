package com.dbobjekts.integration.mariadb.catalog.core
import com.dbobjekts.metadata.Schema
object Core : Schema("core", listOf(Address, Country, Employee))