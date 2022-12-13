package com.dbobjekts.integration.mariadb.catalog.hr

import com.dbobjekts.AnyColumn
import com.dbobjekts.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.update.ColumnForWriteMapContainerImpl
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object Certificate:Table("CERTIFICATE"), HasUpdateBuilder<CertificateUpdateBuilder, CertificateInsertBuilder> {
    val id = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "id")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "name")
    override val columns: List<AnyColumn> = listOf(id,name)
    override fun updater(connection: ConnectionAdapter): CertificateUpdateBuilder = CertificateUpdateBuilder(connection)
    override fun inserter(connection: ConnectionAdapter): CertificateInsertBuilder = CertificateInsertBuilder(connection)
}

class CertificateUpdateBuilder(connection: ConnectionAdapter) : UpdateBuilderBase(Certificate, connection) {
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun name(value: String): CertificateUpdateBuilder = ct.put(Certificate.name, value)
}

class CertificateInsertBuilder(connection: ConnectionAdapter):InsertBuilderBase(Certificate, connection){
    private val ct = ColumnForWriteMapContainerImpl(this)
    override protected fun data(): Set<AnyColumnAndValue> = ct.data

    fun name(value: String): CertificateInsertBuilder = ct.put(Certificate.name, value)

    fun mandatoryColumns(name: String) : CertificateInsertBuilder {
      ct.put(Certificate.name, name)
      return this
    }

}

