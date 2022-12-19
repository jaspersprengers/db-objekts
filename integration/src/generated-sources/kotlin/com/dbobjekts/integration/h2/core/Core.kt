package com.dbobjekts.integration.h2.core
import com.dbobjekts.metadata.Schema
object Core : Schema("CORE", listOf(Address, AllTypes, Country, Department, Employee, EmployeeAddress, EmployeeDepartment, Shape, Tuples))