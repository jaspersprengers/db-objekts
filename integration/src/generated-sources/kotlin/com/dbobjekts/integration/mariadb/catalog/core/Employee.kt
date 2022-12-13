package com.dbobjekts.integration.mariadb.catalog.core

import com.dbobjekts.AnyColumn
import com.dbobjekts.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Employee:Table("EMPLOYEE"), HasUpdateBuilder<EmployeeUpdateBuilder, EmployeeInsertBuilder> {
    val id = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "id")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "name")
    val dateOfBirth = com.dbobjekts.metadata.column.DateColumn(this, "date_of_birth")
    val managerId = com.dbobjekts.metadata.column.OptionalForeignKeyLongColumn(this, "manager_id", Employee.id)
    override val columns: List<AnyColumn> = listOf(id,name,dateOfBirth,managerId)
    override fun updater(connection: ConnectionAdapter): EmployeeUpdateBuilder = EmployeeUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapter): EmployeeInsertBuilder = EmployeeInsertBuilder(connection)
}

class EmployeeUpdateBuilder(connection: ConnectionAdapter) : UpdateBuilderBase(Employee, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun name(value: String): EmployeeUpdateBuilder = ct.put(Employee.name, value)
    fun dateOfBirth(value: java.time.LocalDate): EmployeeUpdateBuilder = ct.put(Employee.dateOfBirth, value)
    fun managerId(value: Long?): EmployeeUpdateBuilder = ct.put(Employee.managerId, value)
}

class EmployeeInsertBuilder(connection: ConnectionAdapter):InsertBuilderBase(Employee, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun name(value: String): EmployeeInsertBuilder = ct.put(Employee.name, value)
    fun dateOfBirth(value: java.time.LocalDate): EmployeeInsertBuilder = ct.put(Employee.dateOfBirth, value)
    fun managerId(value: Long?): EmployeeInsertBuilder = ct.put(Employee.managerId, value)

    fun mandatoryColumns(name: String, dateOfBirth: java.time.LocalDate) : EmployeeInsertBuilder {
      ct.put(Employee.name, name)
      ct.put(Employee.dateOfBirth, dateOfBirth)
      return this
    }

}

