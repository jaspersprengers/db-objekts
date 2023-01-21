package com.dbobjekts.demo.db.classicmodels

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
    val orderNumber = LongColumn(this, "orderNumber")
    /**
     * Represents db column classicmodels.orders.orderDate
     */
    val orderDate = DateColumn(this, "orderDate")
    /**
     * Represents db column classicmodels.orders.requiredDate
     */
    val requiredDate = DateColumn(this, "requiredDate")
    /**
     * Represents db column classicmodels.orders.shippedDate
     */
    val shippedDate = NullableDateColumn(this, "shippedDate")
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
    val customerNumber = ForeignKeyLongColumn(this, "customerNumber", Customers.customerNumber)
    override val columns: List<AnyColumn> = listOf(orderNumber,orderDate,requiredDate,shippedDate,status,comments,customerNumber)
    override fun toValue(values: List<Any?>) = OrdersRow(values[0] as Long,values[1] as java.time.LocalDate,values[2] as java.time.LocalDate,values[3] as java.time.LocalDate?,values[4] as String,values[5] as String?,values[6] as Long)
    override fun metadata(): WriteQueryAccessors<OrdersUpdateBuilder, OrdersInsertBuilder> = WriteQueryAccessors(OrdersUpdateBuilder(), OrdersInsertBuilder())
}

class OrdersUpdateBuilder() : UpdateBuilderBase(Orders) {
    fun orderNumber(value: Long): OrdersUpdateBuilder = put(Orders.orderNumber, value)
    fun orderDate(value: java.time.LocalDate): OrdersUpdateBuilder = put(Orders.orderDate, value)
    fun requiredDate(value: java.time.LocalDate): OrdersUpdateBuilder = put(Orders.requiredDate, value)
    fun shippedDate(value: java.time.LocalDate?): OrdersUpdateBuilder = put(Orders.shippedDate, value)
    fun status(value: String): OrdersUpdateBuilder = put(Orders.status, value)
    fun comments(value: String?): OrdersUpdateBuilder = put(Orders.comments, value)
    fun customerNumber(value: Long): OrdersUpdateBuilder = put(Orders.customerNumber, value)
    
    /**
     * FOR INTERNAL USE ONLY
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as OrdersRow
      add(Orders.orderNumber, rowData.orderNumber)
      add(Orders.orderDate, rowData.orderDate)
      add(Orders.requiredDate, rowData.requiredDate)
      add(Orders.shippedDate, rowData.shippedDate)
      add(Orders.status, rowData.status)
      add(Orders.comments, rowData.comments)
      add(Orders.customerNumber, rowData.customerNumber)
      return where(Orders.orderNumber.eq(rowData.orderNumber))
    }    
        
}

class OrdersInsertBuilder():InsertBuilderBase(){
    fun orderNumber(value: Long): OrdersInsertBuilder = put(Orders.orderNumber, value)
    fun orderDate(value: java.time.LocalDate): OrdersInsertBuilder = put(Orders.orderDate, value)
    fun requiredDate(value: java.time.LocalDate): OrdersInsertBuilder = put(Orders.requiredDate, value)
    fun shippedDate(value: java.time.LocalDate?): OrdersInsertBuilder = put(Orders.shippedDate, value)
    fun status(value: String): OrdersInsertBuilder = put(Orders.status, value)
    fun comments(value: String?): OrdersInsertBuilder = put(Orders.comments, value)
    fun customerNumber(value: Long): OrdersInsertBuilder = put(Orders.customerNumber, value)

    fun mandatoryColumns(orderNumber: Long, orderDate: java.time.LocalDate, requiredDate: java.time.LocalDate, status: String, customerNumber: Long) : OrdersInsertBuilder {
      mandatory(Orders.orderNumber, orderNumber)
      mandatory(Orders.orderDate, orderDate)
      mandatory(Orders.requiredDate, requiredDate)
      mandatory(Orders.status, status)
      mandatory(Orders.customerNumber, customerNumber)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as OrdersRow
      add(Orders.orderNumber, rowData.orderNumber)
      add(Orders.orderDate, rowData.orderDate)
      add(Orders.requiredDate, rowData.requiredDate)
      add(Orders.shippedDate, rowData.shippedDate)
      add(Orders.status, rowData.status)
      add(Orders.comments, rowData.comments)
      add(Orders.customerNumber, rowData.customerNumber)
      return execute()
    }    
        
}


data class OrdersRow(
  val orderNumber: Long,
  val orderDate: java.time.LocalDate,
  val requiredDate: java.time.LocalDate,
  val shippedDate: java.time.LocalDate?,
  val status: String,
  val comments: String?,
  val customerNumber: Long    
) : TableRowData<OrdersUpdateBuilder, OrdersInsertBuilder>(Orders.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>(Pair(Orders.orderNumber, orderNumber))
}
        