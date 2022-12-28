package com.dbobjekts.mariadb.testdb.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase
import com.dbobjekts.mariadb.testdb.hr.Hobby


object Employee:Table("EMPLOYEE"), HasUpdateBuilder<EmployeeUpdateBuilder, EmployeeInsertBuilder> {
    val id = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "id")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "name")
    val salary = com.dbobjekts.metadata.column.DoubleColumn(this, "salary")
    val married = com.dbobjekts.metadata.column.NumberAsBooleanColumn(this, "married")
    val dateOfBirth = com.dbobjekts.metadata.column.DateColumn(this, "date_of_birth")
    val children = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "children")
    val hobbyId = com.dbobjekts.metadata.column.OptionalForeignKeyVarcharColumn(this, "hobby_id", Hobby.id)
    override val columns: List<AnyColumn> = listOf(id,name,salary,married,dateOfBirth,children,hobbyId)
    override fun metadata(): WriteQueryAccessors<EmployeeUpdateBuilder, EmployeeInsertBuilder> = WriteQueryAccessors(EmployeeUpdateBuilder(), EmployeeInsertBuilder())
}

class EmployeeUpdateBuilder() : UpdateBuilderBase(Employee) {
    fun name(value: String): EmployeeUpdateBuilder = put(Employee.name, value)
    fun salary(value: Double): EmployeeUpdateBuilder = put(Employee.salary, value)
    fun married(value: Boolean): EmployeeUpdateBuilder = put(Employee.married, value)
    fun dateOfBirth(value: java.time.LocalDate): EmployeeUpdateBuilder = put(Employee.dateOfBirth, value)
    fun children(value: Int?): EmployeeUpdateBuilder = put(Employee.children, value)
    fun hobbyId(value: String?): EmployeeUpdateBuilder = put(Employee.hobbyId, value)
}

class EmployeeInsertBuilder():InsertBuilderBase(){
       fun name(value: String): EmployeeInsertBuilder = put(Employee.name, value)
    fun salary(value: Double): EmployeeInsertBuilder = put(Employee.salary, value)
    fun married(value: Boolean): EmployeeInsertBuilder = put(Employee.married, value)
    fun dateOfBirth(value: java.time.LocalDate): EmployeeInsertBuilder = put(Employee.dateOfBirth, value)
    fun children(value: Int?): EmployeeInsertBuilder = put(Employee.children, value)
    fun hobbyId(value: String?): EmployeeInsertBuilder = put(Employee.hobbyId, value)

    fun mandatoryColumns(name: String, salary: Double, married: Boolean, dateOfBirth: java.time.LocalDate) : EmployeeInsertBuilder {
      mandatory(Employee.name, name)
      mandatory(Employee.salary, salary)
      mandatory(Employee.married, married)
      mandatory(Employee.dateOfBirth, dateOfBirth)
      return this
    }

}

