package com.dbobjekts.example
import com.dbobjekts.example.core.Core
import com.dbobjekts.example.custom.Custom
import com.dbobjekts.example.hr.Hr
import com.dbobjekts.metadata.Catalog

object Catalogdefinition : Catalog("h2", listOf(Core, Custom, Hr))
