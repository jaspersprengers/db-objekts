package com.dbobjekts.jdbc

import com.dbobjekts.metadata.Catalog
import com.dbobjekts.statement.TransactionResultValidator
import com.dbobjekts.util.QueryLogger
import com.dbobjekts.util.SLF4JQueryLogger
import com.dbobjekts.vendors.Vendors
import com.google.common.cache.CacheLoader
import java.sql.Connection

class TransactionManager(
    private val dataSource: DataSourceAdapter,
    val catalog: Catalog,
    val transactionSettings: TransactionSettings = TransactionSettings(),
    val queryLogger: QueryLogger = SLF4JQueryLogger()
) : CacheLoader<Long, Transaction>() {

    private val transactionCache = TransactionCache(this, transactionSettings)

    operator fun <T> invoke(fct: (Transaction) -> T): T = newTransaction(fct)

    override fun load(key: Long): Transaction = newTransaction()

    fun newTransaction(): Transaction {
        val connection: Connection = dataSource.createConnection()
        require(!connection.isClosed, { "Connection is closed" })
        connection.setAutoCommit(transactionSettings.autoCommit)
        return Transaction(
            ConnectionAdapter(
                connection,
                queryLogger,
                catalog,
                Vendors.byName(catalog.vendor)
            )
        )
    }

    fun <T> newTransaction(fct: (Transaction) -> T): T {
        val transaction = newTransaction()
        try {
            val result: T = fct(transaction)
            if (!transactionSettings.autoCommit) {
                transaction.commit()
            }
            return TransactionResultValidator.validate(result)
        } catch (e: Exception) {
            queryLogger.error("Caught exception while executing query for select. Rolling back", e)
            if (!transactionSettings.autoCommit) {
                transaction.rollback()
            }
            throw e
        } finally {
            transaction.close()
        }
    }

    fun <T> joinTransaction(fct: (Transaction) -> T): T {
        val currentTransaction = transactionCache.get()
        try {
            val result: T = fct(currentTransaction)
            return TransactionResultValidator.validate(result)
        } catch (e: Exception) {
            queryLogger.error("Caught exception while executing query. Rolling back ", e)
            currentTransaction.rollback()
            transactionCache.evict()
            throw e
        }
    }


    fun commit() {
        transactionCache.getIfExists()?.let {
            if (!transactionSettings.autoCommit)
                it.commit()
        }
        transactionCache.evict()
    }

    fun rollback() {
        transactionCache.getIfExists()?.let({
            if (!transactionSettings.autoCommit)
                it.rollback()
        })
        transactionCache.evict()
    }

    fun close() {
        dataSource.close()
    }

    override fun toString(): String = "${dataSource} ${catalog}"

    fun test(): Boolean {
        val tr = newTransaction()
        return tr.isValid().also { tr.close() }
    }

    companion object {
        fun newBuilder(): TransactionManagerBuilder = TransactionManagerBuilder()
    }

}

