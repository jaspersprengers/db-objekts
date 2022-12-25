package com.dbobjekts.sampledbs.h2
import com.dbobjekts.sampledbs.h2.core.Core
import com.dbobjekts.sampledbs.h2.hr.Hr
import com.dbobjekts.sampledbs.h2.core.Address
import com.dbobjekts.sampledbs.h2.core.AllTypes
import com.dbobjekts.sampledbs.h2.core.Country
import com.dbobjekts.sampledbs.h2.core.Department
import com.dbobjekts.sampledbs.h2.core.Employee
import com.dbobjekts.sampledbs.h2.core.EmployeeAddress
import com.dbobjekts.sampledbs.h2.core.EmployeeDepartment
import com.dbobjekts.sampledbs.h2.hr.Certificate
import com.dbobjekts.sampledbs.h2.hr.Hobby

object Aliases {
    val a = Address
    val at = AllTypes
    val c = Country
    val d = Department
    val e = Employee
    val ea = EmployeeAddress
    val ed = EmployeeDepartment
    val c1 = Certificate
    val h = Hobby
}
