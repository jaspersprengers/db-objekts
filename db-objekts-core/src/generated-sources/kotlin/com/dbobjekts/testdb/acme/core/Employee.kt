package com.dbobjekts.testdb.acme.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.TableRowData
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.DateColumn
import com.dbobjekts.metadata.column.DoubleColumn
import com.dbobjekts.metadata.column.NullableBooleanColumn
import com.dbobjekts.metadata.column.NullableIntegerColumn
import com.dbobjekts.metadata.column.OptionalForeignKeyVarcharColumn
import com.dbobjekts.metadata.column.SequenceKeyLongColumn
import com.dbobjekts.metadata.column.VarcharColumn
import com.dbobjekts.statement.WriteQueryAccessors
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase
import com.dbobjekts.testdb.acme.hr.Hobby

/**           
 * Auto-generated metadata object for db table CORE.EMPLOYEE.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: ID
 *
 * Foreign keys to: 
 * References by: HR.HOBBY,CORE.EMPLOYEE_ADDRESS,CORE.EMPLOYEE_DEPARTMENT,HR.CERTIFICATE
 */
object Employee:Table<EmployeeRow>("EMPLOYEE"), HasUpdateBuilder<EmployeeUpdateBuilder, EmployeeInsertBuilder> {
    /**
     * Represents db column CORE.EMPLOYEE.ID
     */
    val id = SequenceKeyLongColumn(this, "ID", "EMPLOYEE_SEQUENCE")
    /**
     * Represents db column CORE.EMPLOYEE.NAME
     */
    val name = VarcharColumn(this, "NAME")
    /**
     * Represents db column CORE.EMPLOYEE.SALARY
     */
    val salary = DoubleColumn(this, "SALARY")
    /**
     * Represents db column CORE.EMPLOYEE.MARRIED
     */
    val married = NullableBooleanColumn(this, "MARRIED")
    /**
     * Represents db column CORE.EMPLOYEE.DATE_OF_BIRTH
     */
    val dateOfBirth = DateColumn(this, "DATE_OF_BIRTH")
    /**
     * Represents db column CORE.EMPLOYEE.CHILDREN
     */
    val children = NullableIntegerColumn(this, "CHILDREN")
    /**
     * Represents db column CORE.EMPLOYEE.HOBBY_ID
     *
     * Foreign key to HR.HOBBY.ID
     */
    val hobbyId = OptionalForeignKeyVarcharColumn(this, "HOBBY_ID", Hobby.id)
    override val columns: List<AnyColumn> = listOf(id,name,salary,married,dateOfBirth,children,hobbyId)
    override fun toValue(values: List<Any?>) = EmployeeRow(values[0] as Long,values[1] as String,values[2] as Double,values[3] as Boolean?,values[4] as java.time.LocalDate,values[5] as Int?,values[6] as String?)
    override fun metadata(): WriteQueryAccessors<EmployeeUpdateBuilder, EmployeeInsertBuilder> = WriteQueryAccessors(EmployeeUpdateBuilder(), EmployeeInsertBuilder())
}

class EmployeeUpdateBuilder() : UpdateBuilderBase(Employee) {
    fun name(value: String): EmployeeUpdateBuilder = put(Employee.name, value)
    fun salary(value: Double): EmployeeUpdateBuilder = put(Employee.salary, value)
    fun married(value: Boolean?): EmployeeUpdateBuilder = put(Employee.married, value)
    fun dateOfBirth(value: java.time.LocalDate): EmployeeUpdateBuilder = put(Employee.dateOfBirth, value)
    fun children(value: Int?): EmployeeUpdateBuilder = put(Employee.children, value)
    fun hobbyId(value: String?): EmployeeUpdateBuilder = put(Employee.hobbyId, value)
    
    /**
     * FOR INTERNAL USE ONLY
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as EmployeeRow
      add(Employee.id, rowData.id)
      add(Employee.name, rowData.name)
      add(Employee.salary, rowData.salary)
      add(Employee.married, rowData.married)
      add(Employee.dateOfBirth, rowData.dateOfBirth)
      add(Employee.children, rowData.children)
      add(Employee.hobbyId, rowData.hobbyId)
      return where(Employee.id.eq(rowData.id))
    }    
        
}

class EmployeeInsertBuilder():InsertBuilderBase(){
    fun name(value: String): EmployeeInsertBuilder = put(Employee.name, value)
    fun salary(value: Double): EmployeeInsertBuilder = put(Employee.salary, value)
    fun married(value: Boolean?): EmployeeInsertBuilder = put(Employee.married, value)
    fun dateOfBirth(value: java.time.LocalDate): EmployeeInsertBuilder = put(Employee.dateOfBirth, value)
    fun children(value: Int?): EmployeeInsertBuilder = put(Employee.children, value)
    fun hobbyId(value: String?): EmployeeInsertBuilder = put(Employee.hobbyId, value)

    fun mandatoryColumns(name: String, salary: Double, dateOfBirth: java.time.LocalDate) : EmployeeInsertBuilder {
      mandatory(Employee.name, name)
      mandatory(Employee.salary, salary)
      mandatory(Employee.dateOfBirth, dateOfBirth)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as EmployeeRow
      add(Employee.name, rowData.name)
      add(Employee.salary, rowData.salary)
      add(Employee.married, rowData.married)
      add(Employee.dateOfBirth, rowData.dateOfBirth)
      add(Employee.children, rowData.children)
      add(Employee.hobbyId, rowData.hobbyId)
      return execute()
    }    
        
}


data class EmployeeRow(
val id: Long = 0,
  val name: String,
  val salary: Double,
  val married: Boolean?,
  val dateOfBirth: java.time.LocalDate,
  val children: Int?,
  val hobbyId: String?    
) : TableRowData<EmployeeUpdateBuilder, EmployeeInsertBuilder>(Employee.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>(Pair(Employee.id, id))
}
        
