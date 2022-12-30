package com.dbobjekts.testdb.acme.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase
import com.dbobjekts.testdb.acme.hr.Hobby
import java.time.LocalDate


object Employee:Table<EmployeeRow>("EMPLOYEE"), HasUpdateBuilder<EmployeeUpdateBuilder, EmployeeInsertBuilder> {
    val id = com.dbobjekts.metadata.column.SequenceKeyLongColumn(this, "ID", "EMPLOYEE_SEQ")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "NAME")
    val salary = com.dbobjekts.metadata.column.DoubleColumn(this, "SALARY")
    val married = com.dbobjekts.metadata.column.NullableBooleanColumn(this, "MARRIED")
    val dateOfBirth = com.dbobjekts.metadata.column.DateColumn(this, "DATE_OF_BIRTH")
    val children = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "CHILDREN")
    val hobbyId = com.dbobjekts.metadata.column.OptionalForeignKeyVarcharColumn(this, "HOBBY_ID", Hobby.id)
    override val columns: List<AnyColumn> = listOf(id,name,salary,married,dateOfBirth,children,hobbyId)
    override fun toValue(values: List<Any?>) =
        EmployeeRow(
            id = values[0] as Long,
            name = values[1] as String,
            salary = values[2] as Double,
            married = values[3] as Boolean?,
            dateOfBirth = values[4] as LocalDate,
            children = values[5] as Int?,
            hobbyId  = values[6] as String?)

    override fun metadata(): WriteQueryAccessors<EmployeeUpdateBuilder, EmployeeInsertBuilder> = WriteQueryAccessors(EmployeeUpdateBuilder(), EmployeeInsertBuilder())
}
data class EmployeeRow(val id: Long,
                  val name: String,
                  val salary: Double,
                  val married: Boolean?,
                  val dateOfBirth: LocalDate,
                  val children: Int?,
                  val hobbyId: String?)

class EmployeeUpdateBuilder() : UpdateBuilderBase(Employee) {
    fun name(value: String): EmployeeUpdateBuilder = put(Employee.name, value)
    fun salary(value: Double): EmployeeUpdateBuilder = put(Employee.salary, value)
    fun married(value: Boolean?): EmployeeUpdateBuilder = put(Employee.married, value)
    fun dateOfBirth(value: java.time.LocalDate): EmployeeUpdateBuilder = put(Employee.dateOfBirth, value)
    fun children(value: Int?): EmployeeUpdateBuilder = put(Employee.children, value)
    fun hobbyId(value: String?): EmployeeUpdateBuilder = put(Employee.hobbyId, value)
}

class EmployeeInsertBuilder():InsertBuilderBase(){
       fun name(value: String): EmployeeInsertBuilder = put(Employee.name, value)
    fun salary(value: Double): EmployeeInsertBuilder = put(Employee.salary, value)
    fun married(value: Boolean?): EmployeeInsertBuilder = put(Employee.married, value)
    fun dateOfBirth(value: java.time.LocalDate): EmployeeInsertBuilder = put(Employee.dateOfBirth, value)
    fun children(value: Int?): EmployeeInsertBuilder = put(Employee.children, value)
    fun hobbyId(value: String?): EmployeeInsertBuilder = put(Employee.hobbyId, value)

    fun mandatoryColumns(name: String, salary: Double, dateOfBirth: java.time.LocalDate) : EmployeeInsertBuilder {
      mandatory(Employee.name, name)
      mandatory(Employee.salary, salary)
      mandatory(Employee.dateOfBirth, dateOfBirth)
      return this
    }

}

