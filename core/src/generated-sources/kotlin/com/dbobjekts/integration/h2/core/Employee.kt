package com.dbobjekts.integration.h2.core

import com.dbobjekts.AnyColumn
import com.dbobjekts.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase
import com.dbobjekts.integration.h2.hr.Hobby


object Employee:Table("EMPLOYEE"), HasUpdateBuilder<EmployeeUpdateBuilder, EmployeeInsertBuilder> {
    val id = com.dbobjekts.metadata.column.SequenceKeyLongColumn(this, "ID", "EMPLOYEE_SEQ")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "NAME")
    val salary = com.dbobjekts.metadata.column.DoubleColumn(this, "SALARY")
    val married = com.dbobjekts.metadata.column.NullableBooleanColumn(this, "MARRIED")
    val dateOfBirth = com.dbobjekts.metadata.column.DateColumn(this, "DATE_OF_BIRTH")
    val children = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "CHILDREN")
    val hobbyId = com.dbobjekts.metadata.column.OptionalForeignKeyVarcharColumn(this, "HOBBY_ID", Hobby.id)
    val createdDt = com.dbobjekts.metadata.column.TimeStampColumn(this, "CREATED_DT")
    val modifiedDt = com.dbobjekts.metadata.column.NullableTimeStampColumn(this, "MODIFIED_DT")
    override val columns: List<AnyColumn> = listOf(id,name,salary,married,dateOfBirth,children,hobbyId,createdDt,modifiedDt)
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
    fun createdDt(value: java.time.Instant): EmployeeUpdateBuilder = ct.put(Employee.createdDt, value)
    fun modifiedDt(value: java.time.Instant?): EmployeeUpdateBuilder = ct.put(Employee.modifiedDt, value)
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
    fun createdDt(value: java.time.Instant): EmployeeInsertBuilder = ct.put(Employee.createdDt, value)
    fun modifiedDt(value: java.time.Instant?): EmployeeInsertBuilder = ct.put(Employee.modifiedDt, value)

    fun mandatoryColumns(name: String, salary: Double, dateOfBirth: java.time.LocalDate, createdDt: java.time.Instant) : EmployeeInsertBuilder {
      ct.put(Employee.name, name)
      ct.put(Employee.salary, salary)
      ct.put(Employee.dateOfBirth, dateOfBirth)
      ct.put(Employee.createdDt, createdDt)
      return this
    }

}

