package com.dbobjekts.mysql.testdb
import com.dbobjekts.mysql.testdb.classicmodels.Customers
import com.dbobjekts.mysql.testdb.classicmodels.Employees
import com.dbobjekts.mysql.testdb.classicmodels.Offices
import com.dbobjekts.mysql.testdb.classicmodels.Orderdetails
import com.dbobjekts.mysql.testdb.classicmodels.Orders
import com.dbobjekts.mysql.testdb.classicmodels.Payments
import com.dbobjekts.mysql.testdb.classicmodels.Productlines
import com.dbobjekts.mysql.testdb.classicmodels.Products
import com.dbobjekts.mysql.testdb.core.Address
import com.dbobjekts.mysql.testdb.core.AllTypesNil
import com.dbobjekts.mysql.testdb.core.Country
import com.dbobjekts.mysql.testdb.core.Department
import com.dbobjekts.mysql.testdb.core.Employee
import com.dbobjekts.mysql.testdb.core.EmployeeAddress
import com.dbobjekts.mysql.testdb.core.EmployeeDepartment
import com.dbobjekts.mysql.testdb.hr.Certificate
import com.dbobjekts.mysql.testdb.hr.Hobby

/**  
 * Auto-generated metadata object. Provides a list of unique aliases for each table across all schemas in the catalog. These are used in the SQL that db-Objekts generates.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.
 *
 * You can import the individual values of in this object in your code to use them as convenient shortcuts.
 *
 * ```
 * import com.dbobjekts.mysql.testdb.Aliases.e
 * [...]
 * transaction.select(e.name)
 *```
 */        
object Aliases {
    val c = Customers
    val e = Employees
    val o = Offices
    val o1 = Orderdetails
    val o2 = Orders
    val p = Payments
    val p1 = Productlines
    val p2 = Products
    val a = Address
    val atn = AllTypesNil
    val c1 = Country
    val d = Department
    val e1 = Employee
    val ea = EmployeeAddress
    val ed = EmployeeDepartment
    val c2 = Certificate
    val h = Hobby
}
     