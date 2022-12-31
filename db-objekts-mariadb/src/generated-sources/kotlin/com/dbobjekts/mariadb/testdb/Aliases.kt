package com.dbobjekts.mariadb.testdb
import com.dbobjekts.mariadb.testdb.core.Core
import com.dbobjekts.mariadb.testdb.hr.Hr
import com.dbobjekts.mariadb.testdb.core.Address
import com.dbobjekts.mariadb.testdb.core.AllTypesNil
import com.dbobjekts.mariadb.testdb.core.Country
import com.dbobjekts.mariadb.testdb.core.Department
import com.dbobjekts.mariadb.testdb.core.Employee
import com.dbobjekts.mariadb.testdb.core.EmployeeAddress
import com.dbobjekts.mariadb.testdb.core.EmployeeDepartment
import com.dbobjekts.mariadb.testdb.hr.Certificate
import com.dbobjekts.mariadb.testdb.hr.Hobby

object Aliases {
    val a = Address
    val atn = AllTypesNil
    val c = Country
    val d = Department
    val e = Employee
    val ea = EmployeeAddress
    val ed = EmployeeDepartment
    val c1 = Certificate
    val h = Hobby
}
     