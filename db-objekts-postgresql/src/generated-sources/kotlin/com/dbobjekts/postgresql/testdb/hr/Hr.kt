package com.dbobjekts.postgresql.testdb.hr
import com.dbobjekts.metadata.Schema
object Hr : Schema("hr", listOf(Certificate, Hobby))