package com.dbobjekts.api

import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.jdbc.DataSourceAdapter
import com.dbobjekts.jdbc.DataSourceAdapterImpl
import com.dbobjekts.jdbc.DetermineVendor
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.metadata.PlaceHolderCatalog
import com.dbobjekts.statement.TransactionResultValidator
import com.dbobjekts.util.HikariDataSourceAdapterImpl
import com.dbobjekts.util.StatementLogger
import com.dbobjekts.vendors.Vendor
import com.dbobjekts.vendors.Vendors
import com.zaxxer.hikari.HikariDataSource
import org.slf4j.LoggerFactory
import java.sql.Connection
import javax.sql.DataSource

class TransactionManager(
    dataSource: DataSource,
    val catalog: Catalog = PlaceHolderCatalog
) {

    private val dataSourceAdapter: DataSourceAdapter
    val vendor: Vendor

    init {
        dataSourceAdapter = when (dataSource) {
            is HikariDataSource -> HikariDataSourceAdapterImpl(dataSource)
            else -> DataSourceAdapterImpl(dataSource)
        }
        val metaData = DetermineVendor(this)
        if ( catalog.vendor.isNotBlank() && !catalog.vendor.contentEquals(metaData.vendor.name))
            throw java.lang.IllegalStateException("You provided a Catalog implementation that is associated with vendor ${catalog.vendor}, but you connected to a ${metaData.vendor.name} DataSource.")
        vendor = metaData.vendor
    }

    private val statementLogger: StatementLogger = StatementLogger()


    operator fun <T> invoke(fct: (Transaction) -> T): T = newTransaction(fct)

    private fun newTransaction(): Transaction {
        val connection: Connection = dataSourceAdapter.createConnection()
        require(!connection.isClosed, { "Connection is closed" })
        return Transaction(
            ConnectionAdapter(
                connection,
                statementLogger,
                catalog,
                vendor
            )
        )
    }

    fun <T> newTransaction(fct: (Transaction) -> T): T {
        val transaction = newTransaction()
        try {
            val result: T = fct(transaction)
            transaction.commit()
            return TransactionResultValidator.validate(result)
        } catch (e: Exception) {
            statementLogger.error("Caught exception while executing query for select. Rolling back", e)
            transaction.rollback()
            throw e
        } finally {
            transaction.close()
        }
    }

    fun close() {
        dataSourceAdapter.close()
    }

    override fun toString(): String = "${dataSourceAdapter} ${catalog}"

    companion object {
        private val logger = LoggerFactory.getLogger(TransactionManager::class.java)

        private var INSTANCE: TransactionManager? = null

        // FOR THE SINGLETON
        fun setup(
            dataSource: DataSource,
            catalog: Catalog = PlaceHolderCatalog
        ) {
            if (INSTANCE != null) {
                logger.error("The singleton TransactionManager has already been initialized. You must call invalidate() first to close any open connections before you can call initialize() again.")
                return
            }
            INSTANCE = TransactionManager(dataSource, catalog)
        }

        fun singleton(): TransactionManager = ensure()

        fun <T> newTransaction(fct: (Transaction) -> T): T = ensure().newTransaction(fct)

        fun isValid(): Boolean = INSTANCE != null

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

