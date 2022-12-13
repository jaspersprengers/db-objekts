package com.dbobjekts.integration.mariadb.catalog.hr
import com.dbobjekts.metadata.Schema
object Hr : Schema("hr", listOf(Certificate, EmployeeCertificate))