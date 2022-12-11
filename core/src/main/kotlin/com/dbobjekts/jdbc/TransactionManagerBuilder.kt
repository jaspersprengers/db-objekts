package com.dbobjekts.jdbc

import com.dbobjekts.metadata.Catalog
import com.dbobjekts.util.HikariDataSourceAdapterImpl
import com.dbobjekts.util.QueryLogger
import com.dbobjekts.util.SLF4JQueryLogger
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

abstract class TransactionManagerBuilderBase<out T> {
    protected lateinit var dataSource: DataSourceAdapter
    protected var catalog: Catalog? = null
    protected var queryLogger: QueryLogger = SLF4JQueryLogger()
    protected var querySettings: TransactionSettings = TransactionSettings()

    @Suppress("unchecked")
    fun dataSource(dataSource: DataSource): T {
        this.dataSource = when (dataSource) {
            is HikariDataSource -> HikariDataSourceAdapterImpl(dataSource)
            else -> DataSourceAdapterImpl(dataSource)
        }
        return this as T
    }

    fun catalog(catalog: Catalog): T {
        this.catalog = catalog
        return this as T
    }

    fun customLogger(logger: QueryLogger): T {
        this.queryLogger = logger
        return this as T
    }

    fun autoCommit(ac: Boolean): T {
        this.querySettings = this.querySettings.copy(autoCommit = ac)
        return this as T
    }

    protected fun validate() {
        if (catalog == null) {
            throw IllegalStateException("Catalog property is mandatory")
        }
    }
}

class TransactionManagerBuilder : TransactionManagerBuilderBase<TransactionManagerBuilder>() {
    fun build(): TransactionManager {
        validate()
        return TransactionManager(dataSource, catalog!!, querySettings, queryLogger)
    }
}

object SingletonTransactionManagerBuilder : TransactionManagerBuilderBase<SingletonTransactionManagerBuilder>() {
    fun initialize(): Boolean {
        validate()
        return SingletonTransactionManager.initialize(dataSource, catalog!!, querySettings, queryLogger)
    }

}

