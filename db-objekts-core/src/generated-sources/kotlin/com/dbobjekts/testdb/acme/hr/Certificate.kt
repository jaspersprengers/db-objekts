package com.dbobjekts.testdb.acme.hr

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.TableRowData
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.AutoKeyLongColumn
import com.dbobjekts.metadata.column.ForeignKeyLongColumn
import com.dbobjekts.metadata.column.VarcharColumn
import com.dbobjekts.statement.WriteQueryAccessors
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase
import com.dbobjekts.testdb.acme.core.Employee

/**           
 * Auto-generated metadata object for db table HR.CERTIFICATE.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: ID
 *
 * Foreign keys to: 
 * References by: CORE.EMPLOYEE
 */
object Certificate:Table<CertificateRow>("CERTIFICATE"), HasUpdateBuilder<CertificateUpdateBuilder, CertificateInsertBuilder> {
    /**
     * Represents db column HR.CERTIFICATE.ID
     */
    val id = AutoKeyLongColumn(this, "ID")
    /**
     * Represents db column HR.CERTIFICATE.NAME
     */
    val name = VarcharColumn(this, "NAME")
    /**
     * Represents db column HR.CERTIFICATE.EMPLOYEE_ID
     *
     * Foreign key to CORE.EMPLOYEE.ID
     */
    val employeeId = ForeignKeyLongColumn(this, "EMPLOYEE_ID", Employee.id)
    override val columns: List<AnyColumn> = listOf(id,name,employeeId)
    override fun toValue(values: List<Any?>) = CertificateRow(values[0] as Long,values[1] as String,values[2] as Long)
    override fun metadata(): WriteQueryAccessors<CertificateUpdateBuilder, CertificateInsertBuilder> = WriteQueryAccessors(CertificateUpdateBuilder(), CertificateInsertBuilder())
}

class CertificateUpdateBuilder() : UpdateBuilderBase(Certificate) {
    fun name(value: String): CertificateUpdateBuilder = put(Certificate.name, value)
    fun employeeId(value: Long): CertificateUpdateBuilder = put(Certificate.employeeId, value)
    
    /**
     * FOR INTERNAL USE ONLY
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as CertificateRow
      add(Certificate.id, rowData.id)
      add(Certificate.name, rowData.name)
      add(Certificate.employeeId, rowData.employeeId)
      return where(Certificate.id.eq(rowData.id))
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
) : TableRowData<CertificateUpdateBuilder, CertificateInsertBuilder>(Certificate.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>(Pair(Certificate.id, id))
}
        
