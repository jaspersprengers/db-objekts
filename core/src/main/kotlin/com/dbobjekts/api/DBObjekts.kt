package com.dbobjekts.api

import com.dbobjekts.jdbc.TransactionManagerBuilder
import com.dbobjekts.jdbc.TransactionManagerImpl


object DBObjekts {

    fun configure() = TransactionManagerBuilder()

    fun singletonTransactionManager(): TransactionManager = TransactionManagerImpl.singletonInstance()

}
