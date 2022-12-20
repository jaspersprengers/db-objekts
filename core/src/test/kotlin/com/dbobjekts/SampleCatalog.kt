package com.dbobjekts

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.metadata.Schema
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.*
import com.dbobjekts.statement.update.TableMetaData

object Hr : Schema("HR", listOf(Certificate, Hobby))

object Catalogdefinition : Catalog("H2", listOf(Core, Hr))

object Aliases {
    val a = Address
    val c = Country
    val d = Department
    val e = Employee
    val ea = EmployeeAddress
    val ed = EmployeeDepartment
    val c1 = Certificate
    val h = Hobby
}

object Certificate : Table("CERTIFICATE") {
    val id = SequenceKeyLongColumn(this, "ID", "CERTIFICATE_SEQ")
    val name = VarcharColumn(this, "NAME")
    val employeeId = ForeignKeyLongColumn(this, "EMPLOYEE_ID", Employee.id)
    override val columns: List<AnyColumn> = listOf(id, name, employeeId)
    //val metaData = TableMetaData(columns, EmployeeUpdateBuilder(connection), EmployeeInsertBuilder(connection))
}

object Hobby : Table("HOBBY") {
    val id = VarcharColumn(this, "ID")
    val name = VarcharColumn(this, "NAME")
    override val columns: List<AnyColumn> = listOf(id, name)
}


object Core : Schema("CORE", listOf(Address, Country, Department, Employee, EmployeeAddress, EmployeeDepartment))

object Country : Table("COUNTRY") {
    val id = VarcharColumn(this, "ID")
    val name = VarcharColumn(this, "NAME")
    override val columns: List<AnyColumn> = listOf(id, name)
}

object Department : Table("DEPARTMENT") {
    val id = SequenceKeyLongColumn(this, "ID", "DEPARTMENT_SEQ")
    val name = VarcharColumn(this, "NAME")
    override val columns: List<AnyColumn> = listOf(id, name)
}

object Employee : Table("EMPLOYEE") {
    val id = SequenceKeyLongColumn(this, "ID", "EMPLOYEE_SEQ")
    val name = VarcharColumn(this, "NAME")
    val salary = DoubleColumn(this, "SALARY")
    val married = NullableBooleanColumn(this, "MARRIED")
    val dateOfBirth = DateColumn(this, "DATE_OF_BIRTH")
    val children = NullableIntegerColumn(this, "CHILDREN")
    val hobbyId = OptionalForeignKeyVarcharColumn(this, "HOBBY_ID", Hobby.id)
    override val columns: List<AnyColumn> = listOf(id, name, salary, married, dateOfBirth, children, hobbyId)
}

object EmployeeAddress : Table("EMPLOYEE_ADDRESS") {
    val employeeId = ForeignKeyLongColumn(this, "EMPLOYEE_ID", Employee.id)
    val addressId = ForeignKeyLongColumn(this, "ADDRESS_ID", Address.id)
    val kind = VarcharColumn(this, "KIND")
    override val columns: List<AnyColumn> = listOf(employeeId, addressId, kind)
}

object Address : Table("ADDRESS") {
    val id = SequenceKeyLongColumn(this, "ID", "ADDRESS_SEQ")
    val street = VarcharColumn(this, "STREET")
    val countryId = ForeignKeyVarcharColumn(this, "COUNTRY_ID", Country.id)
    override val columns: List<AnyColumn> = listOf(id, street, countryId)
}

object EmployeeDepartment : Table("EMPLOYEE_DEPARTMENT") {
    val employeeId = ForeignKeyLongColumn(this, "EMPLOYEE_ID", Employee.id)
    val departmentId = ForeignKeyLongColumn(this, "DEPARTMENT_ID", Department.id)
    override val columns: List<AnyColumn> = listOf(employeeId, departmentId)
}
