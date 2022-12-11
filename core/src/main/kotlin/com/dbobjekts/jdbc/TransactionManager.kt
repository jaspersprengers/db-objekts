package com.dbobjekts.jdbc

import com.dbobjekts.metadata.Catalog
import com.dbobjekts.statement.TransactionResultValidator
import com.dbobjekts.util.QueryLogger
import com.dbobjekts.util.SLF4JQueryLogger

class TransactionManager(
    private val dataSource: DataSourceAdapter,
    val catalog: Catalog,
    val transactionSettings: TransactionSettings = TransactionSettings(),
    val queryLogger: QueryLogger = SLF4JQueryLogger()
) {

    private val transactionFactory =
        TransactionFactory(dataSource, catalog, TransactionSettings(transactionSettings.autoCommit), queryLogger)
    private val transactionCache = TransactionCache(transactionFactory)

    operator fun <T> invoke(fct: (Transaction) -> T): T = newTransaction(fct)

    fun <T> newTransaction(fct: (Transaction) -> T): T {
        val transaction = transactionFactory.newTransaction()
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
        val tr = transactionFactory.newTransaction()
        return tr.isValid().also { tr.close() }
    }

    companion object {
        fun newBuilder(): TransactionManagerBuilder = TransactionManagerBuilder()
    }

}

