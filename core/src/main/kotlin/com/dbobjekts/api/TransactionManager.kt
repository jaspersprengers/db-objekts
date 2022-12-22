package com.dbobjekts.api

import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.jdbc.DataSourceAdapter
import com.dbobjekts.jdbc.DataSourceAdapterImpl
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.statement.TransactionResultValidator
import com.dbobjekts.util.HikariDataSourceAdapterImpl
import com.dbobjekts.util.StatementLogger
import com.dbobjekts.util.StringUtil
import com.dbobjekts.vendors.Vendor
import com.dbobjekts.vendors.Vendors
import com.zaxxer.hikari.HikariDataSource
import org.slf4j.LoggerFactory
import java.sql.Connection
import javax.sql.DataSource

class TransactionManager(
    ds: DataSource,
    catalogOpt: Catalog? = null,
    private val customConnectionProvider: ((DataSource) -> Connection)? = null
) {
    private val log = LoggerFactory.getLogger(TransactionManager::class.java)
    internal val dataSource: DataSourceAdapter
    private var catalog: Catalog
    val vendor: Vendor

    init {
        this.dataSource = when (ds) {
            is HikariDataSource -> HikariDataSourceAdapterImpl(ds)
            else -> DataSourceAdapterImpl(ds)
        }
        val metaData = extractDBMetaData()
        catalog = catalogOpt ?: Vendors.byName(metaData.vendor.name).defaultCatalog
        vendor = Vendors.byName(catalog.vendor)
        if (vendor != metaData.vendor)
            throw java.lang.IllegalStateException("You provided a Catalog implementation that is associated with vendor ${catalog.vendor}, but you connected to a ${metaData.vendor.name} DataSource.")
    }


    operator fun <T> invoke(fct: (Transaction) -> T): T = newTransaction(fct)

    fun <T> newTransaction(fct: (Transaction) -> T): T {

        val connection: Connection = customConnectionProvider?.invoke(dataSource.dataSource)
            ?: dataSource.createConnection()

        require(!connection.isClosed, { "Connection is closed" })

        val adapter = ConnectionAdapter(connection, StatementLogger(), catalog, vendor)
        val transaction = Transaction(adapter)
        try {
            val result: T = fct(transaction)
            transaction.commit()
            return TransactionResultValidator.validate(result)
        } catch (e: Exception) {
            log.error("Caught exception while executing query for select. Rolling back", e)
            transaction.rollback()
            throw e
        } finally {
            transaction.semaphore.clear()
            transaction.close()
        }
    }

    internal fun extractDBMetaData(): DBConnectionMetaData {
        val connection = dataSource.createConnection()
        try {
            val metaData = connection.metaData
            val vendor =
                Vendors.byProductAndVersion(
                    metaData.getDatabaseProductName(),
                    metaData.getDatabaseProductVersion()
                )
            val rs = metaData.catalogs
            val catalogs = mutableListOf<String>()
            while (rs.next()) {
                val name = rs.getString(1)
                catalogs += StringUtil.initUpperCase(name)

            }
            return DBConnectionMetaData(vendor, catalogs)

        } finally {
            connection.close()
        }
    }


    override fun toString(): String = "${dataSource} ${catalog}"

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
            return TransactionManager(dataSource, catalog, connectionFactory)
        }
    }

}

