package com.dbobjekts.jdbc

import com.dbobjekts.api.Transaction
import com.dbobjekts.api.TransactionManager
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.statement.TransactionResultValidator
import com.dbobjekts.util.StatementLogger
import com.dbobjekts.vendors.Vendors
import com.google.common.cache.CacheLoader
import java.sql.Connection

class TransactionManagerImpl(
    private val dataSource: DataSourceAdapter,
    val catalog: Catalog,
    val transactionSettings: TransactionSettings = TransactionSettings(),
    val statementLogger: StatementLogger = StatementLogger()
) : TransactionManager, CacheLoader<Long, Transaction>() {

    private val transactionCache = TransactionCache(this, transactionSettings)

    override val vendor = Vendors.byName(catalog.vendor)

    override operator fun <T> invoke(fct: (Transaction) -> T): T = newTransaction(fct)

    override fun load(key: Long): Transaction = newTransaction()

    private fun newTransaction(): TransactionImpl {
        val connection: Connection = dataSource.createConnection()
        require(!connection.isClosed, { "Connection is closed" })
        connection.setAutoCommit(transactionSettings.autoCommit)
        return TransactionImpl(
            ConnectionAdapter(
                connection,
                statementLogger,
                catalog,
                Vendors.byName(catalog.vendor)
            )
        )
    }

    override fun <T> newTransaction(fct: (Transaction) -> T): T {
        val transaction = newTransaction()
        try {
            val result: T = fct(transaction)
            if (!transactionSettings.autoCommit) {
                transaction.commit()
            }
            return TransactionResultValidator.validate(result)
        } catch (e: Exception) {
            statementLogger.error("Caught exception while executing query for select. Rolling back", e)
            if (!transactionSettings.autoCommit) {
                transaction.rollback()
            }
            throw e
        } finally {
            transaction.close()
        }
    }

    override fun commit() {
        transactionCache.getIfExists()?.let {
            if (!transactionSettings.autoCommit)
                it.commit()
        }
        transactionCache.evict()
    }

    override fun rollback() {
        transactionCache.getIfExists()?.let({
            if (!transactionSettings.autoCommit)
                it.rollback()
        })
        transactionCache.evict()
    }

    override fun close() {
        dataSource.close()
    }


    override fun toString(): String = "${dataSource} ${catalog}"

    private fun test(): Boolean {
        val tr = newTransaction()
        return tr.isValid().also { tr.close() }
    }

    companion object {
        internal var INSTANCE: TransactionManagerImpl? = null

        fun builder(): TransactionManagerBuilder =
            TransactionManagerBuilder()


        // FOR THE SINGLETON
        internal fun initialize(
            dataSource: DataSourceAdapter,
            catalog: Catalog,
            querySettings: TransactionSettings = TransactionSettings(),
            statementLogger: StatementLogger = StatementLogger()
        ): Boolean {
            if (INSTANCE != null)
                throw IllegalStateException("The singleton TransactionManager has already been initialized. You must call invalidate() first to close any open connections before you can call initialize() again.")
            val tr = TransactionManagerImpl(dataSource, catalog, querySettings, statementLogger)
            return tr.test().also { INSTANCE = tr }
        }

        fun singletonInstance(): TransactionManager = ensure()

        fun <T> newTransaction(fct: (Transaction) -> T): T = ensure().newTransaction(fct)

        fun commit() = ensure().commit()

        fun rollback() = ensure().rollback()

        fun isConfigured(): Boolean = INSTANCE != null

        internal fun invalidate() {
            INSTANCE?.close()
            INSTANCE = null
        }

        private fun ensure(): TransactionManager = INSTANCE ?: throw IllegalStateException(
            "Singleton TransactionManager has not been initialized yet or was invalidated. " +
                    "You need to call TransactionManager.builder() and finish with a call to configureSingleton()"
        )

    }

}

