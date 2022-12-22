package com.dbobjekts.integration.h2
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.integration.h2.inventory.Inventory
import com.dbobjekts.integration.h2.operations.Operations
object TestCatalog : Catalog("H2", listOf(Inventory, Operations))