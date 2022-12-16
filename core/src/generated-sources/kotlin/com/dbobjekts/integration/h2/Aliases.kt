package com.dbobjekts.integration.h2
import com.dbobjekts.integration.h2.core.Address
import com.dbobjekts.integration.h2.core.Country
import com.dbobjekts.integration.h2.core.Department
import com.dbobjekts.integration.h2.core.Employee
import com.dbobjekts.integration.h2.core.EmployeeAddress
import com.dbobjekts.integration.h2.core.EmployeeDepartment
import com.dbobjekts.integration.h2.core.Shape
import com.dbobjekts.integration.h2.custom.Tuples
import com.dbobjekts.integration.h2.hr.Certificate
import com.dbobjekts.integration.h2.hr.Hobby

object Aliases {
    val a = Address
    val c = Country
    val d = Department
    val e = Employee
    val ea = EmployeeAddress
    val ed = EmployeeDepartment
    val s = Shape
    val t = Tuples
    val c1 = Certificate
    val h = Hobby
}
