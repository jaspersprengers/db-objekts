package com.dbobjekts.postgresql.testdb
import com.dbobjekts.postgresql.testdb.core.Address
import com.dbobjekts.postgresql.testdb.core.AllTypesNil
import com.dbobjekts.postgresql.testdb.core.AutoIncr
import com.dbobjekts.postgresql.testdb.core.Composite
import com.dbobjekts.postgresql.testdb.core.CompositeForeignKey
import com.dbobjekts.postgresql.testdb.core.Country
import com.dbobjekts.postgresql.testdb.core.Department
import com.dbobjekts.postgresql.testdb.core.Employee
import com.dbobjekts.postgresql.testdb.core.EmployeeAddress
import com.dbobjekts.postgresql.testdb.core.EmployeeDepartment
import com.dbobjekts.postgresql.testdb.hr.Certificate
import com.dbobjekts.postgresql.testdb.hr.Hobby

/**  
 * Auto-generated metadata object. Provides a list of unique aliases for each table across all schemas in the catalog. These are used in the SQL that db-Objekts generates.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.
 *
 * You can import the individual values of in this object in your code to use them as convenient shortcuts.
 *
 * ```
 * import com.dbobjekts.postgresql.testdb.Aliases.e
 * [...]
 * transaction.select(e.name)
 *```
 */        
object Aliases {
    val a = Address
    val atn = AllTypesNil
    val ai = AutoIncr
    val c = Composite
    val cfk = CompositeForeignKey
    val c1 = Country
    val d = Department
    val e = Employee
    val ea = EmployeeAddress
    val ed = EmployeeDepartment
    val c2 = Certificate
    val h = Hobby
}
     