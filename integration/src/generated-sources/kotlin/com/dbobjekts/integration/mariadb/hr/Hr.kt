package com.dbobjekts.integration.mariadb.hr
import com.dbobjekts.metadata.Schema
object Hr : Schema("hr", listOf(Certificate, EmployeeCertificate, Hobby))