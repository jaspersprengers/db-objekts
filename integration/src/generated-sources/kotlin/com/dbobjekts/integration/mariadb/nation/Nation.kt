package com.dbobjekts.integration.mariadb.nation
import com.dbobjekts.metadata.Schema
object Nation : Schema("nation", listOf(Continents, Countries, CountryLanguages, CountryStats, Guests, Languages, Regions, RegionAreas, Vips))