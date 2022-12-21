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
    val catalog: Catalog,
    private val customConnectionProvider: ((DataSource) -> Connection)? = null
) {
    private val log = LoggerFactory.getLogger(TransactionManager::class.java)
    private val dataSourceAdapter: DataSourceAdapter
    val vendor: Vendor

    init {
        vendor = Vendors.byName(catalog.vendor)
        dataSourceAdapter = when (dataSource) {
            is HikariDataSource -> HikariDataSourceAdapterImpl(dataSource)
            else -> DataSourceAdapterImpl(dataSource)
        }
        val metaData = DetermineVendor(this)
        if (!catalog.vendor.contentEquals(metaData.vendor.name))
            throw java.lang.IllegalStateException("You provided a Catalog implementation that is associated with vendor ${catalog.vendor}, but you connected to a ${metaData.vendor.name} DataSource.")
    }


    operator fun <T> invoke(fct: (Transaction) -> T): T = newTransaction(fct)

    fun <T> newTransaction(fct: (Transaction) -> T): T {

        val connection: Connection = customConnectionProvider?.invoke(dataSourceAdapter.dataSource)
            ?: dataSourceAdapter.createConnection()

        require(!connection.isClosed, { "Connection is closed" })
        val transaction = Transaction(
            ConnectionAdapter(
                connection,
                StatementLogger(),
                catalog,
                vendor
            )
        )
        try {
            val result: T = fct(transaction)
            transaction.commit()
            return TransactionResultValidator.validate(result)
        } catch (e: Exception) {
            log.error("Caught exception while executing query for select. Rolling back", e)
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
        fun builder() = TransactionManagerBuilder()
    }

    class TransactionManagerBuilder {
        private lateinit var dataSource: DataSource
        private var catalog: Catalog? = null
        private var connectionFactory: ((DataSource) -> Connection)? = null

        fun withDataSource(dataSource: DataSource): TransactionManagerBuilder {
            this.dataSource = dataSource
            return this
        }

        fun withCatalog(catalog: Catalog): TransactionManagerBuilder {
            this.catalog = catalog
            return this
        }

        fun withCustomConnectionProvider(factory: (DataSource) -> Connection): TransactionManagerBuilder {
            this.connectionFactory = factory
            return this
        }

        fun build(): TransactionManager {
            return TransactionManager(dataSource, catalog ?: PlaceHolderCatalog)
        }
    }

}

