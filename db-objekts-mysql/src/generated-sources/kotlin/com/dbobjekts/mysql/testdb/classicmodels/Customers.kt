package com.dbobjekts.mysql.testdb.classicmodels

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.metadata.Table
import com.dbobjekts.api.TableRowData
import com.dbobjekts.metadata.column.IsGeneratedPrimaryKey
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.statement.WriteQueryAccessors
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase


/**           
 * Auto-generated metadata object for db table classicmodels.customers.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: customerNumber
 *
 * Foreign keys: [salesRepEmployeeNumber to classicmodels.employees.employeeNumber] 
 */
object Customers:Table<CustomersRow>("customers"), HasUpdateBuilder<CustomersUpdateBuilder, CustomersInsertBuilder> {
    /**
     * Represents db column classicmodels.customers.customerNumber
     */
    val customernumber = com.dbobjekts.metadata.column.LongColumn(this, "customerNumber")
    /**
     * Represents db column classicmodels.customers.customerName
     */
    val customername = com.dbobjekts.metadata.column.VarcharColumn(this, "customerName")
    /**
     * Represents db column classicmodels.customers.contactLastName
     */
    val contactlastname = com.dbobjekts.metadata.column.VarcharColumn(this, "contactLastName")
    /**
     * Represents db column classicmodels.customers.contactFirstName
     */
    val contactfirstname = com.dbobjekts.metadata.column.VarcharColumn(this, "contactFirstName")
    /**
     * Represents db column classicmodels.customers.phone
     */
    val phone = com.dbobjekts.metadata.column.VarcharColumn(this, "phone")
    /**
     * Represents db column classicmodels.customers.addressLine1
     */
    val addressline1 = com.dbobjekts.metadata.column.VarcharColumn(this, "addressLine1")
    /**
     * Represents db column classicmodels.customers.addressLine2
     */
    val addressline2 = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "addressLine2")
    /**
     * Represents db column classicmodels.customers.city
     */
    val city = com.dbobjekts.metadata.column.VarcharColumn(this, "city")
    /**
     * Represents db column classicmodels.customers.state
     */
    val state = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "state")
    /**
     * Represents db column classicmodels.customers.postalCode
     */
    val postalcode = com.dbobjekts.metadata.column.NullableVarcharColumn(this, "postalCode")
    /**
     * Represents db column classicmodels.customers.country
     */
    val country = com.dbobjekts.metadata.column.VarcharColumn(this, "country")
    /**
     * Represents db column classicmodels.customers.salesRepEmployeeNumber
     *
     * Foreign key to classicmodels.employees.employeeNumber
     */
    val salesrepemployeenumber = com.dbobjekts.metadata.column.OptionalForeignKeyLongColumn(this, "salesRepEmployeeNumber", Employees.employeenumber)
    /**
     * Represents db column classicmodels.customers.creditLimit
     */
    val creditlimit = com.dbobjekts.metadata.column.NullableBigDecimalColumn(this, "creditLimit")
    override val columns: List<AnyColumn> = listOf(customernumber,customername,contactlastname,contactfirstname,phone,addressline1,addressline2,city,state,postalcode,country,salesrepemployeenumber,creditlimit)
    override fun toValue(values: List<Any?>) = CustomersRow(values[0] as Long,values[1] as String,values[2] as String,values[3] as String,values[4] as String,values[5] as String,values[6] as String?,values[7] as String,values[8] as String?,values[9] as String?,values[10] as String,values[11] as Long?,values[12] as java.math.BigDecimal?)
    override fun metadata(): WriteQueryAccessors<CustomersUpdateBuilder, CustomersInsertBuilder> = WriteQueryAccessors(CustomersUpdateBuilder(), CustomersInsertBuilder())
}

class CustomersUpdateBuilder() : UpdateBuilderBase(Customers) {
    fun customernumber(value: Long): CustomersUpdateBuilder = put(Customers.customernumber, value)
    fun customername(value: String): CustomersUpdateBuilder = put(Customers.customername, value)
    fun contactlastname(value: String): CustomersUpdateBuilder = put(Customers.contactlastname, value)
    fun contactfirstname(value: String): CustomersUpdateBuilder = put(Customers.contactfirstname, value)
    fun phone(value: String): CustomersUpdateBuilder = put(Customers.phone, value)
    fun addressline1(value: String): CustomersUpdateBuilder = put(Customers.addressline1, value)
    fun addressline2(value: String?): CustomersUpdateBuilder = put(Customers.addressline2, value)
    fun city(value: String): CustomersUpdateBuilder = put(Customers.city, value)
    fun state(value: String?): CustomersUpdateBuilder = put(Customers.state, value)
    fun postalcode(value: String?): CustomersUpdateBuilder = put(Customers.postalcode, value)
    fun country(value: String): CustomersUpdateBuilder = put(Customers.country, value)
    fun salesrepemployeenumber(value: Long?): CustomersUpdateBuilder = put(Customers.salesrepemployeenumber, value)
    fun creditlimit(value: java.math.BigDecimal?): CustomersUpdateBuilder = put(Customers.creditlimit, value)
    
    /**
     * FOR INTERNAL USE ONLY
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as CustomersRow
      add(Customers.customernumber, rowData.customernumber)
      add(Customers.customername, rowData.customername)
      add(Customers.contactlastname, rowData.contactlastname)
      add(Customers.contactfirstname, rowData.contactfirstname)
      add(Customers.phone, rowData.phone)
      add(Customers.addressline1, rowData.addressline1)
      add(Customers.addressline2, rowData.addressline2)
      add(Customers.city, rowData.city)
      add(Customers.state, rowData.state)
      add(Customers.postalcode, rowData.postalcode)
      add(Customers.country, rowData.country)
      add(Customers.salesrepemployeenumber, rowData.salesrepemployeenumber)
      add(Customers.creditlimit, rowData.creditlimit)
      return where(Customers.customernumber.eq(rowData.customernumber))
    }    
        
}

class CustomersInsertBuilder():InsertBuilderBase(){
    fun customernumber(value: Long): CustomersInsertBuilder = put(Customers.customernumber, value)
    fun customername(value: String): CustomersInsertBuilder = put(Customers.customername, value)
    fun contactlastname(value: String): CustomersInsertBuilder = put(Customers.contactlastname, value)
    fun contactfirstname(value: String): CustomersInsertBuilder = put(Customers.contactfirstname, value)
    fun phone(value: String): CustomersInsertBuilder = put(Customers.phone, value)
    fun addressline1(value: String): CustomersInsertBuilder = put(Customers.addressline1, value)
    fun addressline2(value: String?): CustomersInsertBuilder = put(Customers.addressline2, value)
    fun city(value: String): CustomersInsertBuilder = put(Customers.city, value)
    fun state(value: String?): CustomersInsertBuilder = put(Customers.state, value)
    fun postalcode(value: String?): CustomersInsertBuilder = put(Customers.postalcode, value)
    fun country(value: String): CustomersInsertBuilder = put(Customers.country, value)
    fun salesrepemployeenumber(value: Long?): CustomersInsertBuilder = put(Customers.salesrepemployeenumber, value)
    fun creditlimit(value: java.math.BigDecimal?): CustomersInsertBuilder = put(Customers.creditlimit, value)

    fun mandatoryColumns(customernumber: Long, customername: String, contactlastname: String, contactfirstname: String, phone: String, addressline1: String, city: String, country: String) : CustomersInsertBuilder {
      mandatory(Customers.customernumber, customernumber)
      mandatory(Customers.customername, customername)
      mandatory(Customers.contactlastname, contactlastname)
      mandatory(Customers.contactfirstname, contactfirstname)
      mandatory(Customers.phone, phone)
      mandatory(Customers.addressline1, addressline1)
      mandatory(Customers.city, city)
      mandatory(Customers.country, country)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as CustomersRow
      add(Customers.customernumber, rowData.customernumber)
      add(Customers.customername, rowData.customername)
      add(Customers.contactlastname, rowData.contactlastname)
      add(Customers.contactfirstname, rowData.contactfirstname)
      add(Customers.phone, rowData.phone)
      add(Customers.addressline1, rowData.addressline1)
      add(Customers.addressline2, rowData.addressline2)
      add(Customers.city, rowData.city)
      add(Customers.state, rowData.state)
      add(Customers.postalcode, rowData.postalcode)
      add(Customers.country, rowData.country)
      add(Customers.salesrepemployeenumber, rowData.salesrepemployeenumber)
      add(Customers.creditlimit, rowData.creditlimit)
      return execute()
    }    
        
}


data class CustomersRow(
  val customernumber: Long,
  val customername: String,
  val contactlastname: String,
  val contactfirstname: String,
  val phone: String,
  val addressline1: String,
  val addressline2: String?,
  val city: String,
  val state: String?,
  val postalcode: String?,
  val country: String,
  val salesrepemployeenumber: Long?,
  val creditlimit: java.math.BigDecimal?    
) : TableRowData<CustomersUpdateBuilder, CustomersInsertBuilder>(Customers.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>(Pair(Customers.customernumber, customernumber))
}
        
