package com.dbobjekts

import com.dbobjekts.jdbc.SingletonTransactionManagerBuilder
import com.dbobjekts.jdbc.TransactionManagerBuilder


object DBObjekts {

    fun configureTransactionManager(): TransactionManagerBuilder = TransactionManagerBuilder()

    fun configureSingletonTransactionManager(): SingletonTransactionManagerBuilder = SingletonTransactionManagerBuilder

}
