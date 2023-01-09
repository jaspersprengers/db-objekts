package com.dbobjekts.mariadb.testdb.hr

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.TableRowData
import com.dbobjekts.metadata.column.IsGeneratedPrimaryKey
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.api.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase
import com.dbobjekts.mariadb.testdb.core.Employee



/**           
 * Auto-generated metadata object for db table hr.CERTIFICATE.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: id
 *
 * Foreign keys: [employee_id to core.EMPLOYEE.id] 
 */
object Certificate:Table<CertificateRow>("CERTIFICATE"), HasUpdateBuilder<CertificateUpdateBuilder, CertificateInsertBuilder> {
    /**
     * Represents db column hr.CERTIFICATE.id
     */
    val id = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "id")
    /**
     * Represents db column hr.CERTIFICATE.name
     */
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "name")
    /**
     * Represents db column hr.CERTIFICATE.employee_id
     *
     * Foreign key to core.EMPLOYEE.id
     */
    val employeeId = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "employee_id", Employee.id)
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
) : TableRowData<CertificateUpdateBuilder, CertificateInsertBuilder>(Certificate.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>(Pair(Certificate.id, id))
}
        
