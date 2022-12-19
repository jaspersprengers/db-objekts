package com.dbobjekts.integration.mariadb.hr

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase
import com.dbobjekts.integration.mariadb.core.Employee


object EmployeeCertificate:Table("EMPLOYEE_CERTIFICATE"), HasUpdateBuilder<EmployeeCertificateUpdateBuilder, EmployeeCertificateInsertBuilder> {
    val employeeId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "employee_id", Employee.id)
    val certificateId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "certificate_id", Certificate.id)
    val datePassed = com.dbobjekts.metadata.column.DateColumn(this, "date_passed")
    val dateExpires = com.dbobjekts.metadata.column.NullableDateColumn(this, "date_expires")
    override val columns: List<AnyColumn> = listOf(employeeId,certificateId,datePassed,dateExpires)
    override fun updater(connection: ConnectionAdapter): EmployeeCertificateUpdateBuilder = EmployeeCertificateUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapter): EmployeeCertificateInsertBuilder = EmployeeCertificateInsertBuilder(connection)
}

class EmployeeCertificateUpdateBuilder(connection: ConnectionAdapter) : UpdateBuilderBase(EmployeeCertificate, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun employeeId(value: Long): EmployeeCertificateUpdateBuilder = ct.put(EmployeeCertificate.employeeId, value)
    fun certificateId(value: Long): EmployeeCertificateUpdateBuilder = ct.put(EmployeeCertificate.certificateId, value)
    fun datePassed(value: java.time.LocalDate): EmployeeCertificateUpdateBuilder = ct.put(EmployeeCertificate.datePassed, value)
    fun dateExpires(value: java.time.LocalDate?): EmployeeCertificateUpdateBuilder = ct.put(EmployeeCertificate.dateExpires, value)
}

class EmployeeCertificateInsertBuilder(connection: ConnectionAdapter):InsertBuilderBase(EmployeeCertificate, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun employeeId(value: Long): EmployeeCertificateInsertBuilder = ct.put(EmployeeCertificate.employeeId, value)
    fun certificateId(value: Long): EmployeeCertificateInsertBuilder = ct.put(EmployeeCertificate.certificateId, value)
    fun datePassed(value: java.time.LocalDate): EmployeeCertificateInsertBuilder = ct.put(EmployeeCertificate.datePassed, value)
    fun dateExpires(value: java.time.LocalDate?): EmployeeCertificateInsertBuilder = ct.put(EmployeeCertificate.dateExpires, value)

    fun mandatoryColumns(employeeId: Long, certificateId: Long, datePassed: java.time.LocalDate) : EmployeeCertificateInsertBuilder {
      ct.put(EmployeeCertificate.employeeId, employeeId)
      ct.put(EmployeeCertificate.certificateId, certificateId)
      ct.put(EmployeeCertificate.datePassed, datePassed)
      return this
    }

}

