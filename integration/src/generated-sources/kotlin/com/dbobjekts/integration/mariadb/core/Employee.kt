package com.dbobjekts.integration.mariadb.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase
import com.dbobjekts.integration.mariadb.hr.Hobby


object Employee:Table("EMPLOYEE"), HasUpdateBuilder<EmployeeUpdateBuilder, EmployeeInsertBuilder> {
    val id = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "id")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "name")
    val salary = com.dbobjekts.metadata.column.DoubleColumn(this, "salary")
    val married = com.dbobjekts.metadata.column.NullableBooleanColumn(this, "married")
    val dateOfBirth = com.dbobjekts.metadata.column.DateColumn(this, "date_of_birth")
    val children = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "children")
    val hobbyId = com.dbobjekts.metadata.column.OptionalForeignKeyVarcharColumn(this, "hobby_id", Hobby.id)
    val managerId = com.dbobjekts.metadata.column.OptionalForeignKeyLongColumn(this, "manager_id", Employee.id)
    override val columns: List<AnyColumn> = listOf(id,name,salary,married,dateOfBirth,children,hobbyId,managerId)
    override fun updater(connection: ConnectionAdapter): EmployeeUpdateBuilder = EmployeeUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapter): EmployeeInsertBuilder = EmployeeInsertBuilder(connection)
}

class EmployeeUpdateBuilder(connection: ConnectionAdapter) : UpdateBuilderBase(Employee, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun name(value: String): EmployeeUpdateBuilder = ct.put(Employee.name, value)
    fun salary(value: Double): EmployeeUpdateBuilder = ct.put(Employee.salary, value)
    fun married(value: Boolean?): EmployeeUpdateBuilder = ct.put(Employee.married, value)
    fun dateOfBirth(value: java.time.LocalDate): EmployeeUpdateBuilder = ct.put(Employee.dateOfBirth, value)
    fun children(value: Int?): EmployeeUpdateBuilder = ct.put(Employee.children, value)
    fun hobbyId(value: String?): EmployeeUpdateBuilder = ct.put(Employee.hobbyId, value)
    fun managerId(value: Long?): EmployeeUpdateBuilder = ct.put(Employee.managerId, value)
}

class EmployeeInsertBuilder(connection: ConnectionAdapter):InsertBuilderBase(Employee, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun name(value: String): EmployeeInsertBuilder = ct.put(Employee.name, value)
    fun salary(value: Double): EmployeeInsertBuilder = ct.put(Employee.salary, value)
    fun married(value: Boolean?): EmployeeInsertBuilder = ct.put(Employee.married, value)
    fun dateOfBirth(value: java.time.LocalDate): EmployeeInsertBuilder = ct.put(Employee.dateOfBirth, value)
    fun children(value: Int?): EmployeeInsertBuilder = ct.put(Employee.children, value)
    fun hobbyId(value: String?): EmployeeInsertBuilder = ct.put(Employee.hobbyId, value)
    fun managerId(value: Long?): EmployeeInsertBuilder = ct.put(Employee.managerId, value)

    fun mandatoryColumns(name: String, salary: Double, dateOfBirth: java.time.LocalDate) : EmployeeInsertBuilder {
      ct.put(Employee.name, name)
      ct.put(Employee.salary, salary)
      ct.put(Employee.dateOfBirth, dateOfBirth)
      return this
    }

}

