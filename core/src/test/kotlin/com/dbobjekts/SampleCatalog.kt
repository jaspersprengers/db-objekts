package com.dbobjekts

import com.dbobjekts.api.AnyColumnAndValue
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.metadata.Schema
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.*
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.metadata.WriteQueryAccessors
import com.dbobjekts.statement.update.UpdateBuilderBase

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

object Certificate : Table("CERTIFICATE"), HasUpdateBuilder<CertificateUpdateBuilder, CertificateInsertBuilder> {
    val id = SequenceKeyLongColumn(this, "ID", "CERTIFICATE_SEQ")
    val name = VarcharColumn(this, "NAME")
    val employeeId = ForeignKeyLongColumn(this, "EMPLOYEE_ID", Employee.id)
    override val columns = listOf(id, name, employeeId)

    override fun metadata(): WriteQueryAccessors<CertificateUpdateBuilder, CertificateInsertBuilder> =
        WriteQueryAccessors(CertificateUpdateBuilder(), CertificateInsertBuilder())
}

class CertificateUpdateBuilder() : UpdateBuilderBase(Certificate) {

    override fun data(): Set<AnyColumnAndValue> {
        TODO("Not yet implemented")
    }
}

class CertificateInsertBuilder() : InsertBuilderBase() {

    override fun data(): Set<AnyColumnAndValue> {
        TODO("Not yet implemented")
    }
}

object Hobby : Table("HOBBY"), HasUpdateBuilder<HobbyUpdateBuilder, HobbyInsertBuilder> {
    val id = VarcharColumn(this, "ID")
    val name = VarcharColumn(this, "NAME")
    override val columns = listOf(id, name)
    override fun metadata(): WriteQueryAccessors<HobbyUpdateBuilder, HobbyInsertBuilder> =
        WriteQueryAccessors(HobbyUpdateBuilder(), HobbyInsertBuilder())
}

class HobbyUpdateBuilder() : UpdateBuilderBase(Hobby) {

    override fun data(): Set<AnyColumnAndValue> {
        TODO("Not yet implemented")
    }
}

class HobbyInsertBuilder() : InsertBuilderBase() {

    override fun data(): Set<AnyColumnAndValue> {
        TODO("Not yet implemented")
    }
}

object Core : Schema("CORE", listOf(Address, Country, Department, Employee, EmployeeAddress, EmployeeDepartment))

object Country : Table("COUNTRY"), HasUpdateBuilder<CountryUpdateBuilder, CountryInsertBuilder> {
    val id = VarcharColumn(this, "ID")
    val name = VarcharColumn(this, "NAME")
    override val columns = listOf(id, name)
    override fun metadata(): WriteQueryAccessors<CountryUpdateBuilder, CountryInsertBuilder> =
        WriteQueryAccessors(CountryUpdateBuilder(), CountryInsertBuilder())
}

class CountryUpdateBuilder() : UpdateBuilderBase(Country) {

    override fun data(): Set<AnyColumnAndValue> {
        TODO("Not yet implemented")
    }
}

class CountryInsertBuilder() : InsertBuilderBase() {

    override fun data(): Set<AnyColumnAndValue> {
        TODO("Not yet implemented")
    }
}

object Department : Table("DEPARTMENT"), HasUpdateBuilder<DepartmentUpdateBuilder, DepartmentInsertBuilder> {
    val id = SequenceKeyLongColumn(this, "ID", "DEPARTMENT_SEQ")
    val name = VarcharColumn(this, "NAME")
    override val columns = listOf(id, name)
    override fun metadata(): WriteQueryAccessors<DepartmentUpdateBuilder, DepartmentInsertBuilder> =
        WriteQueryAccessors(DepartmentUpdateBuilder(), DepartmentInsertBuilder())
}

class DepartmentUpdateBuilder() : UpdateBuilderBase(Department) {

    override fun data(): Set<AnyColumnAndValue> {
        TODO("Not yet implemented")
    }
}

class DepartmentInsertBuilder() : InsertBuilderBase() {

    override fun data(): Set<AnyColumnAndValue> {
        TODO("Not yet implemented")
    }
}

object Employee : Table("EMPLOYEE"), HasUpdateBuilder<EmployeeUpdateBuilder, EmployeeInsertBuilder> {
    val id = SequenceKeyLongColumn(this, "ID", "EMPLOYEE_SEQ")
    val name = VarcharColumn(this, "NAME")
    val salary = DoubleColumn(this, "SALARY")
    val married = NullableBooleanColumn(this, "MARRIED")
    val dateOfBirth = DateColumn(this, "DATE_OF_BIRTH")
    val children = NullableIntegerColumn(this, "CHILDREN")
    val hobbyId = OptionalForeignKeyVarcharColumn(this, "HOBBY_ID", Hobby.id)
    override val columns = listOf(id, name, salary, married, dateOfBirth, children, hobbyId)
    override fun metadata(): WriteQueryAccessors<EmployeeUpdateBuilder, EmployeeInsertBuilder> =
        WriteQueryAccessors(EmployeeUpdateBuilder(), EmployeeInsertBuilder())
}

class EmployeeUpdateBuilder() : UpdateBuilderBase(Employee) {

    override fun data(): Set<AnyColumnAndValue> {
        TODO("Not yet implemented")
    }
}

class EmployeeInsertBuilder() : InsertBuilderBase() {

    override fun data(): Set<AnyColumnAndValue> {
        TODO("Not yet implemented")
    }
}

object EmployeeAddress : Table("EMPLOYEE_ADDRESS"), HasUpdateBuilder<EmployeeAddressUpdateBuilder, EmployeeAddressInsertBuilder> {
    val employeeId = ForeignKeyLongColumn(this, "EMPLOYEE_ID", Employee.id)
    val addressId = ForeignKeyLongColumn(this, "ADDRESS_ID", Address.id)
    val kind = VarcharColumn(this, "KIND")
    override val columns = listOf(employeeId, addressId, kind)
    override fun metadata(): WriteQueryAccessors<EmployeeAddressUpdateBuilder, EmployeeAddressInsertBuilder> =
        WriteQueryAccessors(EmployeeAddressUpdateBuilder(), EmployeeAddressInsertBuilder())
}

class EmployeeAddressUpdateBuilder() : UpdateBuilderBase(EmployeeAddress) {

    override fun data(): Set<AnyColumnAndValue> {
        TODO("Not yet implemented")
    }
}

class EmployeeAddressInsertBuilder() : InsertBuilderBase() {

    override fun data(): Set<AnyColumnAndValue> {
        TODO("Not yet implemented")
    }
}

object Address : Table("ADDRESS"), HasUpdateBuilder<AddressUpdateBuilder, AddressInsertBuilder> {
    val id = SequenceKeyLongColumn(this, "ID", "ADDRESS_SEQ")
    val street = VarcharColumn(this, "STREET")
    val countryId = ForeignKeyVarcharColumn(this, "COUNTRY_ID", Country.id)
    override val columns = listOf(id, street, countryId)
    override fun metadata(): WriteQueryAccessors<AddressUpdateBuilder, AddressInsertBuilder> =
        WriteQueryAccessors(AddressUpdateBuilder(), AddressInsertBuilder())
}

class AddressUpdateBuilder() : UpdateBuilderBase(Address) {

    override fun data(): Set<AnyColumnAndValue> {
        TODO("Not yet implemented")
    }
}

class AddressInsertBuilder() : InsertBuilderBase() {

    override fun data(): Set<AnyColumnAndValue> {
        TODO("Not yet implemented")
    }
}

object EmployeeDepartment : Table("EMPLOYEE_DEPARTMENT"),
    HasUpdateBuilder<EmployeeDepartmentUpdateBuilder, EmployeeDepartmentInsertBuilder> {
    val employeeId = ForeignKeyLongColumn(this, "EMPLOYEE_ID", Employee.id)
    val departmentId = ForeignKeyLongColumn(this, "DEPARTMENT_ID", Department.id)
    override val columns = listOf(employeeId, departmentId)
    override fun metadata(): WriteQueryAccessors<EmployeeDepartmentUpdateBuilder, EmployeeDepartmentInsertBuilder> =
        WriteQueryAccessors(EmployeeDepartmentUpdateBuilder(), EmployeeDepartmentInsertBuilder())
}

class EmployeeDepartmentUpdateBuilder() : UpdateBuilderBase(EmployeeDepartment) {

    override fun data(): Set<AnyColumnAndValue> {
        TODO("Not yet implemented")
    }
}

class EmployeeDepartmentInsertBuilder() : InsertBuilderBase() {

    override fun data(): Set<AnyColumnAndValue> {
        TODO("Not yet implemented")
    }
}
