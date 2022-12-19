package com.dbobjekts.integration.mariadb.core
import com.dbobjekts.metadata.Schema
object Core : Schema("core", listOf(Address, Country, Department, Employee, EmployeeAddress, EmployeeDepartment))