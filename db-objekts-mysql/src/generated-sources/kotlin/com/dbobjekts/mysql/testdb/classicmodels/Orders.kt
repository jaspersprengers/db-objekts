package com.dbobjekts.mysql.testdb.classicmodels

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.TableRowData
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.DateColumn
import com.dbobjekts.metadata.column.ForeignKeyLongColumn
import com.dbobjekts.metadata.column.LongColumn
import com.dbobjekts.metadata.column.NullableDateColumn
import com.dbobjekts.metadata.column.NullableVarcharColumn
import com.dbobjekts.metadata.column.VarcharColumn
import com.dbobjekts.statement.WriteQueryAccessors
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase

/**           
 * Auto-generated metadata object for db table classicmodels.orders.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: orderNumber
 *
 * Foreign keys to: 
 * References by: classicmodels.customers,classicmodels.orderdetails
 */
object Orders:Table<OrdersRow>("orders"), HasUpdateBuilder<OrdersUpdateBuilder, OrdersInsertBuilder> {
    /**
     * Represents db column classicmodels.orders.orderNumber
     */
    val ordernumber = LongColumn(this, "orderNumber")
    /**
     * Represents db column classicmodels.orders.orderDate
     */
    val orderdate = DateColumn(this, "orderDate")
    /**
     * Represents db column classicmodels.orders.requiredDate
     */
    val requireddate = DateColumn(this, "requiredDate")
    /**
     * Represents db column classicmodels.orders.shippedDate
     */
    val shippeddate = NullableDateColumn(this, "shippedDate")
    /**
     * Represents db column classicmodels.orders.status
     */
    val status = VarcharColumn(this, "status")
    /**
     * Represents db column classicmodels.orders.comments
     */
    val comments = NullableVarcharColumn(this, "comments")
    /**
     * Represents db column classicmodels.orders.customerNumber
     *
     * Foreign key to classicmodels.customers.customerNumber
     */
    val customernumber = ForeignKeyLongColumn(this, "customerNumber", Customers.customernumber)
    override val columns: List<AnyColumn> = listOf(ordernumber,orderdate,requireddate,shippeddate,status,comments,customernumber)
    override fun toValue(values: List<Any?>) = OrdersRow(values[0] as Long,values[1] as java.time.LocalDate,values[2] as java.time.LocalDate,values[3] as java.time.LocalDate?,values[4] as String,values[5] as String?,values[6] as Long)
    override fun metadata(): WriteQueryAccessors<OrdersUpdateBuilder, OrdersInsertBuilder> = WriteQueryAccessors(OrdersUpdateBuilder(), OrdersInsertBuilder())
}

class OrdersUpdateBuilder() : UpdateBuilderBase(Orders) {
    fun ordernumber(value: Long): OrdersUpdateBuilder = put(Orders.ordernumber, value)
    fun orderdate(value: java.time.LocalDate): OrdersUpdateBuilder = put(Orders.orderdate, value)
    fun requireddate(value: java.time.LocalDate): OrdersUpdateBuilder = put(Orders.requireddate, value)
    fun shippeddate(value: java.time.LocalDate?): OrdersUpdateBuilder = put(Orders.shippeddate, value)
    fun status(value: String): OrdersUpdateBuilder = put(Orders.status, value)
    fun comments(value: String?): OrdersUpdateBuilder = put(Orders.comments, value)
    fun customernumber(value: Long): OrdersUpdateBuilder = put(Orders.customernumber, value)
    
    /**
     * FOR INTERNAL USE ONLY
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as OrdersRow
      add(Orders.ordernumber, rowData.ordernumber)
      add(Orders.orderdate, rowData.orderdate)
      add(Orders.requireddate, rowData.requireddate)
      add(Orders.shippeddate, rowData.shippeddate)
      add(Orders.status, rowData.status)
      add(Orders.comments, rowData.comments)
      add(Orders.customernumber, rowData.customernumber)
      return where(Orders.ordernumber.eq(rowData.ordernumber))
    }    
        
}

class OrdersInsertBuilder():InsertBuilderBase(){
    fun ordernumber(value: Long): OrdersInsertBuilder = put(Orders.ordernumber, value)
    fun orderdate(value: java.time.LocalDate): OrdersInsertBuilder = put(Orders.orderdate, value)
    fun requireddate(value: java.time.LocalDate): OrdersInsertBuilder = put(Orders.requireddate, value)
    fun shippeddate(value: java.time.LocalDate?): OrdersInsertBuilder = put(Orders.shippeddate, value)
    fun status(value: String): OrdersInsertBuilder = put(Orders.status, value)
    fun comments(value: String?): OrdersInsertBuilder = put(Orders.comments, value)
    fun customernumber(value: Long): OrdersInsertBuilder = put(Orders.customernumber, value)

    fun mandatoryColumns(ordernumber: Long, orderdate: java.time.LocalDate, requireddate: java.time.LocalDate, status: String, customernumber: Long) : OrdersInsertBuilder {
      mandatory(Orders.ordernumber, ordernumber)
      mandatory(Orders.orderdate, orderdate)
      mandatory(Orders.requireddate, requireddate)
      mandatory(Orders.status, status)
      mandatory(Orders.customernumber, customernumber)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as OrdersRow
      add(Orders.ordernumber, rowData.ordernumber)
      add(Orders.orderdate, rowData.orderdate)
      add(Orders.requireddate, rowData.requireddate)
      add(Orders.shippeddate, rowData.shippeddate)
      add(Orders.status, rowData.status)
      add(Orders.comments, rowData.comments)
      add(Orders.customernumber, rowData.customernumber)
      return execute()
    }    
        
}


data class OrdersRow(
  val ordernumber: Long,
  val orderdate: java.time.LocalDate,
  val requireddate: java.time.LocalDate,
  val shippeddate: java.time.LocalDate?,
  val status: String,
  val comments: String?,
  val customernumber: Long    
) : TableRowData<OrdersUpdateBuilder, OrdersInsertBuilder>(Orders.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>(Pair(Orders.ordernumber, ordernumber))
}
        
