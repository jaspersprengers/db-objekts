package com.dbobjekts.demo.db.classicmodels

import com.dbobjekts.api.AnyColumn
import com.dbobjekts.api.TableRowData
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.DoubleColumn
import com.dbobjekts.metadata.column.ForeignKeyLongColumn
import com.dbobjekts.metadata.column.ForeignKeyVarcharColumn
import com.dbobjekts.metadata.column.IntegerColumn
import com.dbobjekts.metadata.column.LongColumn
import com.dbobjekts.statement.WriteQueryAccessors
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.HasUpdateBuilder
import com.dbobjekts.statement.update.UpdateBuilderBase

/**           
 * Auto-generated metadata object for db table classicmodels.orderdetails.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: [orderNumber, productCode]
 *
 * Foreign keys to: 
 * References by: classicmodels.orders,classicmodels.products
 */
object Orderdetails:Table<OrderdetailsRow>("orderdetails"), HasUpdateBuilder<OrderdetailsUpdateBuilder, OrderdetailsInsertBuilder> {
    /**
     * Represents db column classicmodels.orderdetails.orderNumber
     *
     * Foreign key to classicmodels.orders.orderNumber
     */
    val orderNumber = ForeignKeyLongColumn(this, "orderNumber", Orders.orderNumber)
    /**
     * Represents db column classicmodels.orderdetails.productCode
     *
     * Foreign key to classicmodels.products.productCode
     */
    val productCode = ForeignKeyVarcharColumn(this, "productCode", Products.productCode)
    /**
     * Represents db column classicmodels.orderdetails.quantityOrdered
     */
    val quantityOrdered = LongColumn(this, "quantityOrdered")
    /**
     * Represents db column classicmodels.orderdetails.priceEach
     */
    val priceEach = DoubleColumn(this, "priceEach")
    /**
     * Represents db column classicmodels.orderdetails.orderLineNumber
     */
    val orderLineNumber = IntegerColumn(this, "orderLineNumber")
    override val columns: List<AnyColumn> = listOf(orderNumber,productCode,quantityOrdered,priceEach,orderLineNumber)
    override fun toValue(values: List<Any?>) = OrderdetailsRow(values[0] as Long,values[1] as String,values[2] as Long,values[3] as Double,values[4] as Int)
    override fun metadata(): WriteQueryAccessors<OrderdetailsUpdateBuilder, OrderdetailsInsertBuilder> = WriteQueryAccessors(OrderdetailsUpdateBuilder(), OrderdetailsInsertBuilder())
}

class OrderdetailsUpdateBuilder() : UpdateBuilderBase(Orderdetails) {
    fun orderNumber(value: Long): OrderdetailsUpdateBuilder = put(Orderdetails.orderNumber, value)
    fun productCode(value: String): OrderdetailsUpdateBuilder = put(Orderdetails.productCode, value)
    fun quantityOrdered(value: Long): OrderdetailsUpdateBuilder = put(Orderdetails.quantityOrdered, value)
    fun priceEach(value: Double): OrderdetailsUpdateBuilder = put(Orderdetails.priceEach, value)
    fun orderLineNumber(value: Int): OrderdetailsUpdateBuilder = put(Orderdetails.orderLineNumber, value)
    
    /**
     * FOR INTERNAL USE ONLY
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as OrderdetailsRow
      add(Orderdetails.orderNumber, rowData.orderNumber)
      add(Orderdetails.productCode, rowData.productCode)
      add(Orderdetails.quantityOrdered, rowData.quantityOrdered)
      add(Orderdetails.priceEach, rowData.priceEach)
      add(Orderdetails.orderLineNumber, rowData.orderLineNumber)
      return where(Orderdetails.orderNumber.eq(rowData.orderNumber).and(Orderdetails.productCode.eq(rowData.productCode)))
    }    
        
}

class OrderdetailsInsertBuilder():InsertBuilderBase(){
    fun orderNumber(value: Long): OrderdetailsInsertBuilder = put(Orderdetails.orderNumber, value)
    fun productCode(value: String): OrderdetailsInsertBuilder = put(Orderdetails.productCode, value)
    fun quantityOrdered(value: Long): OrderdetailsInsertBuilder = put(Orderdetails.quantityOrdered, value)
    fun priceEach(value: Double): OrderdetailsInsertBuilder = put(Orderdetails.priceEach, value)
    fun orderLineNumber(value: Int): OrderdetailsInsertBuilder = put(Orderdetails.orderLineNumber, value)

    fun mandatoryColumns(orderNumber: Long, productCode: String, quantityOrdered: Long, priceEach: Double, orderLineNumber: Int) : OrderdetailsInsertBuilder {
      mandatory(Orderdetails.orderNumber, orderNumber)
      mandatory(Orderdetails.productCode, productCode)
      mandatory(Orderdetails.quantityOrdered, quantityOrdered)
      mandatory(Orderdetails.priceEach, priceEach)
      mandatory(Orderdetails.orderLineNumber, orderLineNumber)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as OrderdetailsRow
      add(Orderdetails.orderNumber, rowData.orderNumber)
      add(Orderdetails.productCode, rowData.productCode)
      add(Orderdetails.quantityOrdered, rowData.quantityOrdered)
      add(Orderdetails.priceEach, rowData.priceEach)
      add(Orderdetails.orderLineNumber, rowData.orderLineNumber)
      return execute()
    }    
        
}


data class OrderdetailsRow(
  val orderNumber: Long,
  val productCode: String,
  val quantityOrdered: Long,
  val priceEach: Double,
  val orderLineNumber: Int    
) : TableRowData<OrderdetailsUpdateBuilder, OrderdetailsInsertBuilder>(Orderdetails.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>(Pair(Orderdetails.orderNumber, orderNumber),Pair(Orderdetails.productCode, productCode))
}
        
