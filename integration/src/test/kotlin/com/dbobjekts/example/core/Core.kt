package com.dbobjekts.example.core
import com.dbobjekts.metadata.Schema
object Core : Schema("core", listOf(Address, Country, Department, Employee, EmployeeAddress, EmployeeDepartment, Shape))
