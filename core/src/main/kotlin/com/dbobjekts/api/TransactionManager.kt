package com.dbobjekts.api

import java.sql.Connection


interface TransactionManager {
    operator fun <T> invoke(fct: (Transaction) -> T): T
    fun <T> newTransaction(fct: (Transaction) -> T): T
    fun commit()
    fun rollback()
    fun close()
}
