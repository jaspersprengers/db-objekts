package com.dbobjekts.testdb.acme.core
import com.dbobjekts.metadata.Schema
object Core : Schema("CORE", listOf(Address, AllTypes, Country, Department, DutchAddress, Employee, EmployeeAddress, EmployeeDepartment))