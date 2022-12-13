package com.dbobjekts.integration.h2

import com.dbobjekts.integration.h2.core.Core
import com.dbobjekts.integration.h2.custom.Custom
import com.dbobjekts.integration.h2.hr.Hr
import com.dbobjekts.integration.h2.s1.S1
import com.dbobjekts.integration.h2.s2.S2
import com.dbobjekts.metadata.Catalog


object Catalogdefinition : Catalog("h2", listOf(Core, Custom, Hr, S1, S2)) {
}
