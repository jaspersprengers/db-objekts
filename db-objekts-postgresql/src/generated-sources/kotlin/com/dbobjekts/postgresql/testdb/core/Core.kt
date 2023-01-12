package com.dbobjekts.postgresql.testdb.core
import com.dbobjekts.metadata.Schema
object Core : Schema("core", listOf(Address, AllTypesNil, AutoIncr, Composite, CompositeForeignKey, Country, Department, Employee, EmployeeAddress, EmployeeDepartment))