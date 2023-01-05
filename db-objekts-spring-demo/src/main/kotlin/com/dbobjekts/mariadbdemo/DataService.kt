package com.dbobjekts.mariadbdemo

import com.dbobjekts.api.TransactionManager
import com.dbobjekts.mariadb.testdb.core.Employee
import org.springframework.stereotype.Service

@Service
class DataService(val transactionManager: TransactionManager) {

    fun insertBasicEmployeeData(name: String, married: Boolean): EmployeeEntity {
        return transactionManager {
            val id = it.insert(Employee).mandatoryColumns(name, married).execute()
            EmployeeEntity(id, name)
        }
    }

    fun getEmployees(): List<EmployeeEntity> {
        return transactionManager {
            it.select(Employee.id, Employee.name).asList().map { EmployeeEntity(it) }
        }
    }
}
