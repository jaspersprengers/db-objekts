package com.dbobjekts.integration.h2.core
import com.dbobjekts.metadata.Schema
object Core : Schema("CORE", listOf(Address, Country, Department, Employee, EmployeeAddress, EmployeeDepartment, Shape))