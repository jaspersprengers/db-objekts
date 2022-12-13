package com.dbobjekts.jdbc

import com.dbobjekts.metadata.Catalog
import com.dbobjekts.util.HikariDataSourceAdapterImpl
import com.dbobjekts.util.StatementLogger
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

class TransactionManagerBuilder {
    internal lateinit var dataSource: DataSourceAdapter
    internal lateinit var catalog: Catalog
    internal var statementLogger: StatementLogger = StatementLogger()
    internal var querySettings: TransactionSettings = TransactionSettings()

    @Suppress("unchecked")
    fun dataSource(dataSource: DataSource): TransactionManagerBuilder {
        this.dataSource = when (dataSource) {
            is HikariDataSource -> HikariDataSourceAdapterImpl(dataSource)
            else -> DataSourceAdapterImpl(dataSource)
        }
        return this
    }

    fun catalog(catalog: Catalog): TransactionManagerBuilder {
        this.catalog = catalog
        return this
    }

    fun customLogger(logger: StatementLogger): TransactionManagerBuilder {
        this.statementLogger = logger
        return this
    }

    fun autoCommit(ac: Boolean): TransactionManagerBuilder {
        this.querySettings = this.querySettings.copy(autoCommit = ac)
        return this
    }

    fun build(): TransactionManager {
        return TransactionManager(dataSource, catalog, querySettings, statementLogger)
    }

    fun buildForSingleton() {
        TransactionManager.initialize(dataSource, catalog, querySettings, statementLogger)
    }


}

