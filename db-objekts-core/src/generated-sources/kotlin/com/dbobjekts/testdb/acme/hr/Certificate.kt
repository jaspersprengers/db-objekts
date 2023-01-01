package com.dbobjekts.testdb.acme.hr

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.TableRowData
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase
import com.dbobjekts.testdb.acme.core.Employee


object Certificate:Table<CertificateRow>("CERTIFICATE"), HasUpdateBuilder<CertificateUpdateBuilder, CertificateInsertBuilder> {
    val id = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "ID")
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "NAME")
    val employeeId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "EMPLOYEE_ID", Employee.id)
    override val columns: List<AnyColumn> = listOf(id,name,employeeId)
    override fun toValue(values: List<Any?>) = CertificateRow(values[0] as Long,values[1] as String,values[2] as Long)
    override fun metadata(): WriteQueryAccessors<CertificateUpdateBuilder, CertificateInsertBuilder> = WriteQueryAccessors(CertificateUpdateBuilder(), CertificateInsertBuilder())
}

class CertificateUpdateBuilder() : UpdateBuilderBase(Certificate) {
    fun name(value: String): CertificateUpdateBuilder = put(Certificate.name, value)
    fun employeeId(value: Long): CertificateUpdateBuilder = put(Certificate.employeeId, value)

    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as CertificateRow
      add(Certificate.id, rowData.id)
      add(Certificate.name, rowData.name)
      add(Certificate.employeeId, rowData.employeeId)
      return where (Certificate.id.eq(rowData.id))
    }    
        
}

class CertificateInsertBuilder():InsertBuilderBase(){
    fun name(value: String): CertificateInsertBuilder = put(Certificate.name, value)
    fun employeeId(value: Long): CertificateInsertBuilder = put(Certificate.employeeId, value)

    fun mandatoryColumns(name: String, employeeId: Long) : CertificateInsertBuilder {
      mandatory(Certificate.name, name)
      mandatory(Certificate.employeeId, employeeId)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as CertificateRow
      add(Certificate.name, rowData.name)
      add(Certificate.employeeId, rowData.employeeId)
      return execute()
    }    
        
}


data class CertificateRow(
val id: Long = 0,
  val name: String,
  val employeeId: Long    
) : TableRowData<CertificateUpdateBuilder, CertificateInsertBuilder>(Certificate.metadata())
        
