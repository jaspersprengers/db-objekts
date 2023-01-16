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
 * Auto-generated metadata object for db table classicmodels.orderdetails.
 *
 * Do not edit this file manually! Always use [com.dbobjekts.codegen.CodeGenerator] when the metadata model is no longer in sync with the database.           
 *
 * Primary keys: [orderNumber, productCode]
 *
 * Foreign keys: [orderNumber to classicmodels.orders.orderNumber, productCode to classicmodels.products.productCode] 
 */
object Orderdetails:Table<OrderdetailsRow>("orderdetails"), HasUpdateBuilder<OrderdetailsUpdateBuilder, OrderdetailsInsertBuilder> {
    /**
     * Represents db column classicmodels.orderdetails.orderNumber
     *
     * Foreign key to classicmodels.orders.orderNumber
     */
    val ordernumber = com.dbobjekts.metadata.column.ForeignKeyLongColumn(this, "orderNumber", Orders.ordernumber)
    /**
     * Represents db column classicmodels.orderdetails.productCode
     *
     * Foreign key to classicmodels.products.productCode
     */
    val productcode = com.dbobjekts.metadata.column.ForeignKeyVarcharColumn(this, "productCode", Products.productcode)
    /**
     * Represents db column classicmodels.orderdetails.quantityOrdered
     */
    val quantityordered = com.dbobjekts.metadata.column.LongColumn(this, "quantityOrdered")
    /**
     * Represents db column classicmodels.orderdetails.priceEach
     */
    val priceeach = com.dbobjekts.metadata.column.BigDecimalColumn(this, "priceEach")
    /**
     * Represents db column classicmodels.orderdetails.orderLineNumber
     */
    val orderlinenumber = com.dbobjekts.metadata.column.IntegerColumn(this, "orderLineNumber")
    override val columns: List<AnyColumn> = listOf(ordernumber,productcode,quantityordered,priceeach,orderlinenumber)
    override fun toValue(values: List<Any?>) = OrderdetailsRow(values[0] as Long,values[1] as String,values[2] as Long,values[3] as java.math.BigDecimal,values[4] as Int)
    override fun metadata(): WriteQueryAccessors<OrderdetailsUpdateBuilder, OrderdetailsInsertBuilder> = WriteQueryAccessors(OrderdetailsUpdateBuilder(), OrderdetailsInsertBuilder())
}

class OrderdetailsUpdateBuilder() : UpdateBuilderBase(Orderdetails) {
    fun ordernumber(value: Long): OrderdetailsUpdateBuilder = put(Orderdetails.ordernumber, value)
    fun productcode(value: String): OrderdetailsUpdateBuilder = put(Orderdetails.productcode, value)
    fun quantityordered(value: Long): OrderdetailsUpdateBuilder = put(Orderdetails.quantityordered, value)
    fun priceeach(value: java.math.BigDecimal): OrderdetailsUpdateBuilder = put(Orderdetails.priceeach, value)
    fun orderlinenumber(value: Int): OrderdetailsUpdateBuilder = put(Orderdetails.orderlinenumber, value)
    
    /**
     * FOR INTERNAL USE ONLY
     */
    override fun updateRow(rowData: TableRowData<*, *>): Long {
      rowData as OrderdetailsRow
      add(Orderdetails.ordernumber, rowData.ordernumber)
      add(Orderdetails.productcode, rowData.productcode)
      add(Orderdetails.quantityordered, rowData.quantityordered)
      add(Orderdetails.priceeach, rowData.priceeach)
      add(Orderdetails.orderlinenumber, rowData.orderlinenumber)
      return where(Orderdetails.ordernumber.eq(rowData.ordernumber).and(Orderdetails.productcode.eq(rowData.productcode)))
    }    
        
}

class OrderdetailsInsertBuilder():InsertBuilderBase(){
    fun ordernumber(value: Long): OrderdetailsInsertBuilder = put(Orderdetails.ordernumber, value)
    fun productcode(value: String): OrderdetailsInsertBuilder = put(Orderdetails.productcode, value)
    fun quantityordered(value: Long): OrderdetailsInsertBuilder = put(Orderdetails.quantityordered, value)
    fun priceeach(value: java.math.BigDecimal): OrderdetailsInsertBuilder = put(Orderdetails.priceeach, value)
    fun orderlinenumber(value: Int): OrderdetailsInsertBuilder = put(Orderdetails.orderlinenumber, value)

    fun mandatoryColumns(ordernumber: Long, productcode: String, quantityordered: Long, priceeach: java.math.BigDecimal, orderlinenumber: Int) : OrderdetailsInsertBuilder {
      mandatory(Orderdetails.ordernumber, ordernumber)
      mandatory(Orderdetails.productcode, productcode)
      mandatory(Orderdetails.quantityordered, quantityordered)
      mandatory(Orderdetails.priceeach, priceeach)
      mandatory(Orderdetails.orderlinenumber, orderlinenumber)
      return this
    }


    override fun insertRow(rowData: TableRowData<*, *>): Long {
      rowData as OrderdetailsRow
      add(Orderdetails.ordernumber, rowData.ordernumber)
      add(Orderdetails.productcode, rowData.productcode)
      add(Orderdetails.quantityordered, rowData.quantityordered)
      add(Orderdetails.priceeach, rowData.priceeach)
      add(Orderdetails.orderlinenumber, rowData.orderlinenumber)
      return execute()
    }    
        
}


data class OrderdetailsRow(
  val ordernumber: Long,
  val productcode: String,
  val quantityordered: Long,
  val priceeach: java.math.BigDecimal,
  val orderlinenumber: Int    
) : TableRowData<OrderdetailsUpdateBuilder, OrderdetailsInsertBuilder>(Orderdetails.metadata()){
     override val primaryKeys = listOf<Pair<AnyColumn, Any?>>(Pair(Orderdetails.ordernumber, ordernumber),Pair(Orderdetails.productcode, productcode))
}
        
