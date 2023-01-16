package com.dbobjekts.mysql.testdb.core
import com.dbobjekts.metadata.Schema
object Core : Schema("core", listOf(Address, AllTypesNil, Country, Department, Employee, EmployeeAddress, EmployeeDepartment))