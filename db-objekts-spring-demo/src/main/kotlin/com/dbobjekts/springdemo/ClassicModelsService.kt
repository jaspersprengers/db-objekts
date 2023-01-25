package com.dbobjekts.springdemo

import com.dbobjekts.api.TransactionManager
import com.dbobjekts.api.Tuple2
import com.dbobjekts.api.Tuple3
import com.dbobjekts.demo.db.Aliases
import com.dbobjekts.demo.db.HasAliases
import com.dbobjekts.demo.db.classicmodels.*
import com.dbobjekts.metadata.column.Aggregate
import org.springframework.stereotype.Service

@Service
class ClassicModelsService(val transactionManager: TransactionManager) : HasAliases by Aliases {

    val cu = Customers
    val ord = Orders
    val od = Orderdetails
    val pr = Products
    val pl = Productlines

    fun getOrdersWithStatus(status: OrderStatus): List<OrdersRow> =
        transactionManager {
            it.select(ord).where(ord.status.eq(status)).asList()
        }

    fun getCustomersWithMinimumOrderCount(minimum: Int): List<Tuple2<CustomersRow, Long>> =
        transactionManager {
            it.select(cu, ord.orderNumber.count())
                .where(ord.status.eq(OrderStatus.Shipped))
                .having(Aggregate.ge(minimum.toLong())).asList()
        }


    fun getOrderForCustomer(customerNumber: Long): List<Tuple3<OrderStatus, Double, String>> =
        transactionManager {
            it.select(ord.status, od.priceEach, pr.productName)
                .where(ord.customerNumber.eq(customerNumber)).asList()
        }


}
