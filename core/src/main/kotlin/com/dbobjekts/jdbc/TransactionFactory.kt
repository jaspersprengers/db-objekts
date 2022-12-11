package com.dbobjekts.jdbc

import com.dbobjekts.metadata.Catalog
import com.dbobjekts.util.QueryLogger
import com.dbobjekts.vendors.Vendors
import com.google.common.cache.CacheLoader
import java.sql.Connection

class TransactionFactory(
    val ds: DataSourceAdapter,
    val catalog: Catalog,
    val querySettings: TransactionSettings,
    val queryLogger: QueryLogger
) : CacheLoader<Long, Transaction>() {
    val cacheExpiryMillis: Long = querySettings.transactionCacheExpireMillis

    override fun load(key: Long): Transaction = newTransaction()

    fun newTransaction(): Transaction = Transaction(createConnection())

     fun createConnection(): ConnectionAdapter {
        val connection: Connection = ds.createConnection()
        require(!connection.isClosed, { "Connection is closed" })
        connection.setAutoCommit(querySettings.autoCommit)
        return ConnectionAdapter(
            connection,
            queryLogger,
            catalog,
            Vendors.byName(catalog.vendor)
        )
    }


}
