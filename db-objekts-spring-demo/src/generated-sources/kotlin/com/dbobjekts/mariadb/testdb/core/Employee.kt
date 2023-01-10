package com.dbobjekts.mariadb.testdb.core

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.TableRowData
import com.dbobjekts.statement.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase
import com.dbobjekts.mariadb.testdb.hr.Hobby



/**           
 * Auto-generated metadata object for db table core.EMPLOYEE.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: id
 *
 * Foreign keys: [hobby_id to hr.HOBBY.id] 
 */
object Employee:Table<EmployeeRow>("EMPLOYEE"), HasUpdateBuilder<EmployeeUpdateBuilder, EmployeeInsertBuilder> {
    /**
     * Represents db column core.EMPLOYEE.id
     */
    val id = com.dbobjekts.metadata.column.AutoKeyLongColumn(this, "id")
    /**
     * Represents db column core.EMPLOYEE.name
     */
    val name = com.dbobjekts.metadata.column.VarcharColumn(this, "name")
    /**
     * Represents db column core.EMPLOYEE.salary
     */
    val salary = com.dbobjekts.metadata.column.DoubleColumn(this, "salary")
    /**
     * Represents db column core.EMPLOYEE.married
     */
    val married = com.dbobjekts.metadata.column.NumberAsBooleanColumn(this, "married")
    /**
     * Represents db column core.EMPLOYEE.date_of_birth
     */
    val dateOfBirth = com.dbobjekts.metadata.column.DateColumn(this, "date_of_birth")
    /**
     * Represents db column core.EMPLOYEE.children
     */
    val children = com.dbobjekts.metadata.column.NullableIntegerColumn(this, "children")
    /**
     * Represents db column core.EMPLOYEE.hobby_id
     *
     * Foreign key to hr.HOBBY.id
     */
    val hobbyId = com.dbobjekts.metadata.column.OptionalForeignKeyVarcharColumn(this, "hobby_id", Hobby.id)
    override val columns: List<AnyColumn> = listOf(id,name,salary,married,dateOfBirth,children,hobbyId)
    override fun toValue(values: List<Any?>) = EmployeeRow(values[0] as Long,values[1] as String,values[2] as Double,values[3] as Boolean,values[4] as java.time.LocalDate,values[5] as Int?,values[6] as String?)
    override fun metadata(): WriteQueryAccessors<EmployeeUpdateBuilder, EmployeeInsertBuilder> = WriteQueryAccessors(EmployeeUpdateBuilder(), EmployeeInsertBuilder())
}

class EmployeeUpdateBuilder() : UpdateBuilderBase(Employee) {
    fun name(value: String): EmployeeUpdateBuilder = put(Employee.name, value)
    fun salary(value: Double): EmployeeUpdateBuilder = put(Employee.salary, value)
    fun married(value: Boolean): EmployeeUpdateBuilder = put(Employee.married, value)
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
      return where (Employee.id.eq(rowData.id))
    }    
        
}

class EmployeeInsertBuilder():InsertBuilderBase(){
    fun name(value: String): EmployeeInsertBuilder = put(Employee.name, value)
    fun salary(value: Double): EmployeeInsertBuilder = put(Employee.salary, value)
    fun married(value: Boolean): EmployeeInsertBuilder = put(Employee.married, value)
    fun dateOfBirth(value: java.time.LocalDate): EmployeeInsertBuilder = put(Employee.dateOfBirth, value)
    fun children(value: Int?): EmployeeInsertBuilder = put(Employee.children, value)
    fun hobbyId(value: String?): EmployeeInsertBuilder = put(Employee.hobbyId, value)

    fun mandatoryColumns(name: String, salary: Double, married: Boolean, dateOfBirth: java.time.LocalDate) : EmployeeInsertBuilder {
      mandatory(Employee.name, name)
      mandatory(Employee.salary, salary)
      mandatory(Employee.married, married)
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
  val married: Boolean,
  val dateOfBirth: java.time.LocalDate,
  val children: Int?,
  val hobbyId: String?    
) : TableRowData<EmployeeUpdateBuilder, EmployeeInsertBuilder>(Employee.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>(Pair(Employee.id, id))
}
        
