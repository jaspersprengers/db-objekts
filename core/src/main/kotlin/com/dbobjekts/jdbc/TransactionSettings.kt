package com.dbobjekts.jdbc

data class TransactionSettings(val autoCommit: Boolean = true,
                               val transactionCacheExpireMillis: Long = 60000)
