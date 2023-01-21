package com.dbobjekts.springdemo

import com.dbobjekts.api.*
import com.dbobjekts.demo.db.Aliases
import com.dbobjekts.demo.db.HasAliases
import com.dbobjekts.demo.db.classicmodels.*
import com.dbobjekts.metadata.column.Aggregate
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate

@Service
class ClassicModelsService(val transactionManager: TransactionManager) : HasAliases by Aliases {

    val cu = Customers
    val or = Orders
    val od = Orderdetails
    val pr = Products
    val pl = Productlines

    fun getCustomersWithMinimumOrderCount(minimum: Int): List<Tuple2<CustomersRow, Long>> =
        transactionManager {
            it.select(cu, or.orderNumber.count()).having(Aggregate.ge(minimum.toLong())).asList()
        }


    fun getOrderForCustomer(customerNumber: Long): List<Tuple3<String, Double, String>> =
        transactionManager {
            it.select(or.status, od.priceEach, pr.productName)
                .where(or.customerNumber.eq(customerNumber)).asList()
        }


}
