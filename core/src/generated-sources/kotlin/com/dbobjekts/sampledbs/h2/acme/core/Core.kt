package com.dbobjekts.sampledbs.h2.acme.core
import com.dbobjekts.metadata.Schema
object Core : Schema("CORE", listOf(Address, AllTypes, Country, Department, Employee, EmployeeAddress, EmployeeDepartment))
