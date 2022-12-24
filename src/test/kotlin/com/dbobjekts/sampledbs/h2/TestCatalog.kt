package com.dbobjekts.sampledbs.h2
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.sampledbs.h2.core.Core
import com.dbobjekts.sampledbs.h2.hr.Hr
object TestCatalog : Catalog("H2", listOf(Core, Hr))
