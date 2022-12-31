package com.dbobjekts.api

import com.dbobjekts.api.exception.DBObjektsException
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

/**
 * The factory for [Transaction] instances to connect to a database. It wraps a [javax.sqlDataSource] and you typically need only one in your application.
 *
 * Instances are obtained with a call to [TransactionManager.builder]
 *
 * You use a TransactionManager with a call to [newTransaction] or the shorthand [invoke] method, as follows:
 * ```kotlin
 *  transactionManager.newTransaction { transaction ->
 *     // your queries here
 *  }
 *
 *  transactionManager { it.select(..) }
 *
 * ```
 */
class TransactionManager private constructor(
    ds: DataSource,
    catalogOpt: Catalog? = null,
    private val customConnectionProvider: ((DataSource) -> Connection)? = null
) {
    private val log = LoggerFactory.getLogger(TransactionManager::class.java)
    private val dataSource: DataSourceAdapter
    private var catalog: Catalog
    internal val vendor: Vendor

    init {
        this.dataSource = when (ds) {
            is HikariDataSource -> HikariDataSourceAdapterImpl(ds)
            else -> DataSourceAdapterImpl(ds)
        }
        val vendorByDBMetaData = extractDBMetaData().vendor
        catalog = catalogOpt ?: Catalog(vendorByDBMetaData)
        if (catalog.vendor != vendorByDBMetaData.name)
            throw DBObjektsException(
                "You provided a Catalog implementation that is associated with vendor ${catalog.vendor}, " +
                        "but you connected to a ${vendorByDBMetaData.name} DataSource."
            )
        vendor = vendorByDBMetaData
    }

    /**
     * Shorthand for [newTransaction]
     */
    operator fun <T> invoke(function: (Transaction) -> T): T = newTransaction(function)

    /**
     * Creates a new [Transaction]
     * @param function a function that receives a [Transaction] on which you perform your queries
     * @return an arbitrary result of your queries. Can be Unit.
     */
    fun <T> newTransaction(function: (Transaction) -> T): T {

        val connection: Connection = customConnectionProvider?.invoke(dataSource.dataSource)
            ?: dataSource.createConnection()

        require(!connection.isClosed, { "Connection is closed" })

        val adapter = ConnectionAdapter(connection, StatementLogger(), catalog, vendor)
        val transaction = Transaction(adapter)
        try {
            val result: T = function(transaction)
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

    private fun extractDBMetaData(): DBConnectionMetaData {
        val connection = dataSource.createConnection()
        try {
            val metaData = connection.metaData
            val vendor =
                Vendors.byProductAndVersion(
                    metaData.getDatabaseProductName(),
                    metaData.databaseMajorVersion
                )
            val rs = metaData.catalogs
            val schemas = mutableListOf<String>()
            while (rs.next()) {
                val name = rs.getString(1)
                log.info("Extracted catalog $name from mariadb")
                schemas += StringUtil.initUpperCase(name)
            }
            return DBConnectionMetaData(vendor, schemas)

        } finally {
            connection.close()
        }
    }


    override fun toString(): String = "${dataSource} ${catalog}"

    /**
     * Provides access to a [TransactionManagerBuilder]
     */
    companion object {
        fun builder() = TransactionManagerBuilder()
    }

    /**
     * Creates a new [TransactionManagerBuilder] instances for configuring a [TransactionManager]. Use it as follows:
     * ```kotlin
     *  TransactionManager.builder()
     *      .withDataSource(dataSource)
     *      .withCatalog(AcmeCatalog)
     *      .build()
     * ```
     */
    class TransactionManagerBuilder {
        private lateinit var dataSource: DataSource
        private var catalog: Catalog? = null
        private var connectionFactory: ((DataSource) -> Connection)? = null

        /**
         * Sets the underlying [DataSource] for the new [TransactionManager]. Mandatory setting
         * @param a valid [DataSource]
         */
        fun withDataSource(dataSource: DataSource): TransactionManagerBuilder {
            this.dataSource = dataSource
            return this
        }

        /**
         * Sets the [Catalog] implementation (generated by the [com.dbobjekts.codegen.CodeGenerator]) associated with a database.
         *
         * While you can create a [TransactionManager] without this setting, it needs the reference to a Catalog for creating queries with the generated metadata objects.
         *
         * Without a [Catalog], you can only execute queries on native sql.
         *
         * @param the [Catalog] associated with the [DataSource]
         */
        fun withCatalog(catalog: Catalog): TransactionManagerBuilder {
            this.catalog = catalog
            return this
        }

        /**
         * An optional setting if you require full control over the creation of the [Connection]. By default, db-Objekts only does:
         * ```kotlin
         * dataSource.getConnection()
         * ```
         * With this feature you can customize the Connection that db-Obekts will use.
         * @param a function that receives a [DataSource] and returns a [Connection]
         */
        fun withCustomConnectionProvider(factory: (DataSource) -> Connection): TransactionManagerBuilder {
            this.connectionFactory = factory
            return this
        }

        /**
         * @return a new [TransactionManager] instance
         */
        fun build(): TransactionManager {
            return TransactionManager(dataSource, catalog, connectionFactory)
        }
    }

}

