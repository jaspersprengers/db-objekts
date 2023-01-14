package com.dbobjekts.testdb.acme.core

import com.dbobjekts.metadata.Table
import com.dbobjekts.api.TableRowData
import com.dbobjekts.statement.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.metadata.joins.JoinBase
import com.dbobjekts.metadata.joins.JoinType
import com.dbobjekts.metadata.joins.TableJoinChain
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase
import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.AnyTable


/**           
 * Auto-generated metadata object for db table CORE.DEPARTMENT.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: ID
 *
 * Foreign keys to: 
 * References by: CORE.EMPLOYEE_DEPARTMENT
 */
object Department:Table<DepartmentRow>("DEPARTMENT"), HasUpdateBuilder<DepartmentUpdateBuilder, DepartmentInsertBuilder> {
    /**
     * Represents db column CORE.DEPARTMENT.ID
     */
    val id = com.dbobjekts.metadata.column.SequenceKeyLongColumn(this, "ID", "DEPARTMENT_SEQ")
    /**
     * Represents db column CORE.DEPARTMENT.NAME
     */
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "NAME")
    override val columns: List<AnyColumn> = listOf(id,name)
    override fun toValue(values: List<Any?>) = DepartmentRow(values[0] as Long,values[1] as String)
    override fun metadata(): WriteQueryAccessors<DepartmentUpdateBuilder, DepartmentInsertBuilder> = WriteQueryAccessors(DepartmentUpdateBuilder(), DepartmentInsertBuilder())

    fun leftJoin(table: com.dbobjekts.testdb.acme.core.EmployeeDepartment): com.dbobjekts.testdb.acme.core.EmployeeDepartmentJoinChain = com.dbobjekts.testdb.acme.core.EmployeeDepartmentJoinChain(this)._join(table, JoinType.LEFT)
    fun innerJoin(table: com.dbobjekts.testdb.acme.core.EmployeeDepartment): com.dbobjekts.testdb.acme.core.EmployeeDepartmentJoinChain = com.dbobjekts.testdb.acme.core.EmployeeDepartmentJoinChain(this)._join(table, JoinType.INNER)
    fun rightJoin(table: com.dbobjekts.testdb.acme.core.EmployeeDepartment): com.dbobjekts.testdb.acme.core.EmployeeDepartmentJoinChain = com.dbobjekts.testdb.acme.core.EmployeeDepartmentJoinChain(this)._join(table, JoinType.RIGHT)                      
       
}

class DepartmentJoinChain(table: AnyTable, joins: List<JoinBase> = listOf()) : TableJoinChain(table, joins) {
    
    fun leftJoin(table: com.dbobjekts.testdb.acme.core.EmployeeDepartment): com.dbobjekts.testdb.acme.core.EmployeeDepartmentJoinChain = com.dbobjekts.testdb.acme.core.EmployeeDepartmentJoinChain(this.table, this.joins)._join(table, JoinType.LEFT)
    fun innerJoin(table: com.dbobjekts.testdb.acme.core.EmployeeDepartment): com.dbobjekts.testdb.acme.core.EmployeeDepartmentJoinChain = com.dbobjekts.testdb.acme.core.EmployeeDepartmentJoinChain(this.table, this.joins)._join(table, JoinType.INNER)
    fun rightJoin(table: com.dbobjekts.testdb.acme.core.EmployeeDepartment): com.dbobjekts.testdb.acme.core.EmployeeDepartmentJoinChain = com.dbobjekts.testdb.acme.core.EmployeeDepartmentJoinChain(this.table, this.joins)._join(table, JoinType.RIGHT)
}


class DepartmentUpdateBuilder() : UpdateBuilderBase(Department) {
    fun name(value: String): DepartmentUpdateBuilder = put(Department.name, value)
    
    /**
     * FOR INTERNAL USE ONLY
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as DepartmentRow
      add(Department.id, rowData.id)
      add(Department.name, rowData.name)
      return where(Department.id.eq(rowData.id))
    }    
        
}

class DepartmentInsertBuilder():InsertBuilderBase(){
    fun name(value: String): DepartmentInsertBuilder = put(Department.name, value)

    fun mandatoryColumns(name: String) : DepartmentInsertBuilder {
      mandatory(Department.name, name)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as DepartmentRow
      add(Department.name, rowData.name)
      return execute()
    }    
        
}


data class DepartmentRow(
val id: Long = 0,
  val name: String    
) : TableRowData<DepartmentUpdateBuilder, DepartmentInsertBuilder>(Department.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>(Pair(Department.id, id))
}
        
