package com.dbobjekts.mysql.testdb.classicmodels
import com.dbobjekts.metadata.Schema

/**            
 * Auto-generated metadata object representing a schema consisting of one or more tables.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.             
*/
object Classicmodels : Schema("classicmodels", listOf(Customers, Employees, Offices, Orderdetails, Orders, Payments, Productlines, Products)){

    /** 
     * Refers to metadata object for table customers 
     */
    val customers = Customers
        
    /** 
     * Refers to metadata object for table employees 
     */
    val employees = Employees
        
    /** 
     * Refers to metadata object for table offices 
     */
    val offices = Offices
        
    /** 
     * Refers to metadata object for table orderdetails 
     */
    val orderdetails = Orderdetails
        
    /** 
     * Refers to metadata object for table orders 
     */
    val orders = Orders
        
    /** 
     * Refers to metadata object for table payments 
     */
    val payments = Payments
        
    /** 
     * Refers to metadata object for table productlines 
     */
    val productlines = Productlines
        
    /** 
     * Refers to metadata object for table products 
     */
    val products = Products
        
}
      