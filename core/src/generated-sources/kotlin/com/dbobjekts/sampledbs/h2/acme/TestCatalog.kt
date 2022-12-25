package com.dbobjekts.sampledbs.h2.acme
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.sampledbs.h2.acme.core.Core
import com.dbobjekts.sampledbs.h2.acme.hr.Hr
object TestCatalog : Catalog("H2", listOf(Core, Hr))
