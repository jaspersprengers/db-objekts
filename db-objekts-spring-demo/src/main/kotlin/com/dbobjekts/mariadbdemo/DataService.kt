package com.dbobjekts.mariadbdemo

import com.dbobjekts.api.TransactionManager
import org.springframework.stereotype.Service
import com.dbobjekts.mariadb.testdb.core.Employee

@Service
class DataService(val transactionManager: TransactionManager) {

    fun insertBasicEmployeeData(name: String, married: Boolean): EmployeeEntity {
        return transactionManager {
            val id = it.insert(Employee).execute()
            EmployeeEntity(id, name)
        }
    }

    fun getEmployees(): List<EmployeeEntity> {
        return transactionManager {
            it.select(Employee.id, Employee.name).asList().map { EmployeeEntity(it) }
        }
    }
}
