package com.dbobjekts.demo.db.core
import com.dbobjekts.metadata.Schema
object Core : Schema("core", listOf(Address, Country, Department, Employee, EmployeeAddress, EmployeeDepartment))