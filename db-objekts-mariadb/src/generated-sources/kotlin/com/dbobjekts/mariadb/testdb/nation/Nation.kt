package com.dbobjekts.mariadb.testdb.nation
import com.dbobjekts.metadata.Schema
object Nation : Schema("nation", listOf(Continents, Countries, CountryLanguages, CountryStats, Guests, Languages, Regions, RegionAreas, Vips))