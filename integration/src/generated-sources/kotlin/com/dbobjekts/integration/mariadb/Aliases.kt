package com.dbobjekts.integration.mariadb
import com.dbobjekts.integration.mariadb.core.Core
import com.dbobjekts.integration.mariadb.hr.Hr
import com.dbobjekts.integration.mariadb.core.Address
import com.dbobjekts.integration.mariadb.core.Country
import com.dbobjekts.integration.mariadb.core.Department
import com.dbobjekts.integration.mariadb.core.Employee
import com.dbobjekts.integration.mariadb.core.EmployeeAddress
import com.dbobjekts.integration.mariadb.core.EmployeeDepartment
import com.dbobjekts.integration.mariadb.hr.Certificate
import com.dbobjekts.integration.mariadb.hr.EmployeeCertificate
import com.dbobjekts.integration.mariadb.hr.Hobby

object Aliases {
    val a = Address
    val c = Country
    val d = Department
    val e = Employee
    val ea = EmployeeAddress
    val ed = EmployeeDepartment
    val c1 = Certificate
    val ec = EmployeeCertificate
    val h = Hobby
}
     