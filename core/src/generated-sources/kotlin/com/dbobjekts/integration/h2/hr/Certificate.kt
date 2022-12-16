package com.dbobjekts.integration.h2.hr

import com.dbobjekts.AnyColumn
import com.dbobjekts.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase
import com.dbobjekts.integration.h2.core.Employee


object Certificate:Table("CERTIFICATE"), HasUpdateBuilder<CertificateUpdateBuilder, CertificateInsertBuilder> {
    val id = com.dbobjekts.metadata.column.LongColumn(this, "ID")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "NAME")
    val employeeId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "EMPLOYEE_ID", Employee.id)
    override val columns: List<AnyColumn> = listOf(id,name,employeeId)
    override fun updater(connection: ConnectionAdapter): CertificateUpdateBuilder = CertificateUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapter): CertificateInsertBuilder = CertificateInsertBuilder(connection)
}

class CertificateUpdateBuilder(connection: ConnectionAdapter) : UpdateBuilderBase(Certificate, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun id(value: Long): CertificateUpdateBuilder = ct.put(Certificate.id, value)
    fun name(value: String): CertificateUpdateBuilder = ct.put(Certificate.name, value)
    fun employeeId(value: Long): CertificateUpdateBuilder = ct.put(Certificate.employeeId, value)
}

class CertificateInsertBuilder(connection: ConnectionAdapter):InsertBuilderBase(Certificate, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun id(value: Long): CertificateInsertBuilder = ct.put(Certificate.id, value)
    fun name(value: String): CertificateInsertBuilder = ct.put(Certificate.name, value)
    fun employeeId(value: Long): CertificateInsertBuilder = ct.put(Certificate.employeeId, value)

    fun mandatoryColumns(id: Long, name: String, employeeId: Long) : CertificateInsertBuilder {
      ct.put(Certificate.id, id)
      ct.put(Certificate.name, name)
      ct.put(Certificate.employeeId, employeeId)
      return this
    }

}

