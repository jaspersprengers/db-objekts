package com.dbobjekts.sampledbs.h2.acme.hr

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase
import com.dbobjekts.sampledbs.h2.acme.core.Employee


object Certificate:Table("CERTIFICATE"), HasUpdateBuilder<CertificateUpdateBuilder, CertificateInsertBuilder> {
    val id = com.dbobjekts.metadata.column.SequenceKeyLongColumn(this, "ID", "CERTIFICATE_SEQ")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "NAME")
    val employeeId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "EMPLOYEE_ID", Employee.id)
    override val columns: List<AnyColumn> = listOf(id, name, employeeId)
    override fun metadata(): WriteQueryAccessors<CertificateUpdateBuilder, CertificateInsertBuilder> = WriteQueryAccessors(
        CertificateUpdateBuilder(), CertificateInsertBuilder()
    )
}

class CertificateUpdateBuilder() : UpdateBuilderBase(Certificate) {
    fun name(value: String): CertificateUpdateBuilder = put(Certificate.name, value)
    fun employeeId(value: Long): CertificateUpdateBuilder = put(Certificate.employeeId, value)
}

class CertificateInsertBuilder():InsertBuilderBase(){
       fun name(value: String): CertificateInsertBuilder = put(Certificate.name, value)
    fun employeeId(value: Long): CertificateInsertBuilder = put(Certificate.employeeId, value)

    fun mandatoryColumns(name: String, employeeId: Long) : CertificateInsertBuilder {
      mandatory(Certificate.name, name)
      mandatory(Certificate.employeeId, employeeId)
      return this
    }

}
