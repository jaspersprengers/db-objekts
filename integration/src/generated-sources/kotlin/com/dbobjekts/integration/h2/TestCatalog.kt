package com.dbobjekts.integration.h2
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.integration.h2.library.Library
object TestCatalog : Catalog("H2", listOf(Library))