package com.dbobjekts.integration.h2
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.integration.h2.core.Core
import com.dbobjekts.integration.h2.custom.Custom
import com.dbobjekts.integration.h2.hr.Hr
object H2catalog : Catalog("H2", listOf(Core, Custom, Hr))