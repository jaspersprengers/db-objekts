package com.dbobjekts.integration.mariadb.catalog
import com.dbobjekts.integration.mariadb.catalog.core.Core
import com.dbobjekts.integration.mariadb.catalog.hr.Hr
import com.dbobjekts.integration.mariadb.catalog.core.Address
import com.dbobjekts.integration.mariadb.catalog.core.Country
import com.dbobjekts.integration.mariadb.catalog.core.Employee
import com.dbobjekts.integration.mariadb.catalog.hr.Certificate
import com.dbobjekts.integration.mariadb.catalog.hr.EmployeeCertificate

object Aliases {
    val a = Address
    val c = Country
    val e = Employee
    val c1 = Certificate
    val ec = EmployeeCertificate
}
     