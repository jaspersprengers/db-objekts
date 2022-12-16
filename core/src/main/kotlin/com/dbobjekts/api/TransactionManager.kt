package com.dbobjekts.api

import com.dbobjekts.vendors.Vendor


interface TransactionManager {
    operator fun <T> invoke(fct: (Transaction) -> T): T
    fun <T> newTransaction(fct: (Transaction) -> T): T
    fun commit()
    fun rollback()
    fun close()
    val vendor: Vendor
}
