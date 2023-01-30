package com.dbobjekts.api

import com.dbobjekts.api.exception.DBObjektsException
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.jdbc.DataSourceAdapter
import com.dbobjekts.jdbc.DataSourceAdapterImpl
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.metadata.DefaultNoVendorCatalog
import com.dbobjekts.statement.TransactionResultValidator
import com.dbobjekts.util.HikariDataSourceAdapterImpl
import com.dbobjekts.util.StatementLogger
import com.dbobjekts.util.StringUtil
import com.dbobjekts.util.Version
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
    val catalog: Catalog,
    private val customConnectionProvider: ((DataSource) -> Connection)? = null
) {
    private val log = LoggerFactory.getLogger(TransactionManager::class.java)
    private val dataSource: DataSourceAdapter
    internal val vendor: Vendor

    init {
        this.dataSource = when (ds) {
            is HikariDataSource -> HikariDataSourceAdapterImpl(ds)
            else -> DataSourceAdapterImpl(ds)
        }
        val vendorByDBMetaData = extractDBMetaData().vendor
        if (catalog.vendor.isBlank()) {
            log.warn("The catalog is not associated with any vendor.")
        } else if (catalog.vendor != vendorByDBMetaData.name)
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
            val info = transaction.connection.statementLog.lastStatement()
            log.error("Caught exception while executing query '${info?: "<NIL>"}'. Rolling back", e)
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
         * A Catalog reference is essential for creating queries with the generated metadata objects. If you omit this step, the [com.dbobjekts.metadata.DefaultNoVendorCatalog] will be used, which is only suitable for native SQL queries.
         *
         * @param the [Catalog] associated with the [DataSource]
         * @throws com.dbobjekts.api.exception.DBObjektsException if the major version of this catalog is too much behind and would cause compatability issues
         */
        fun withCatalog(catalog: Catalog): TransactionManagerBuilder {
            Version.validateCatalogVersion(catalog.version)
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
            return TransactionManager(dataSource, catalog ?: DefaultNoVendorCatalog, connectionFactory)
        }
    }

}

