package com.dbobjekts.codegen.parsers

import com.dbobjekts.codegen.ProgressLogger
import com.dbobjekts.codegen.configbuilders.CodeGeneratorConfig
import com.dbobjekts.codegen.configbuilders.DataSourceInfo
import com.dbobjekts.vendors.mariadb.MariaDBCatalogParser
import com.dbobjekts.jdbc.TransactionManager
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.util.HikariDataSourceFactory
import com.dbobjekts.vendors.Vendors
import com.dbobjekts.vendors.h2.H2CatalogParser
import javax.sql.DataSource


object LiveDBParserFactory {

    fun create(
        codeGeneratorConfig: CodeGeneratorConfig,
        logger: ProgressLogger
    ): LiveDBParser {
        val dataSource =
            createDataSource(codeGeneratorConfig.dataSourceInfo ?: throw IllegalArgumentException("dataSourceInfo cannot be null"))
        val builder = TransactionManager.builder().dataSource(dataSource)

        return when (val vendor = codeGeneratorConfig.vendor) {
            Vendors.MARIADB -> {
                val transactionManager = builder.catalog(Catalog(Vendors.MARIADB.name)).build()
                MariaDBCatalogParser(codeGeneratorConfig, transactionManager, logger)
            }
            Vendors.H2 -> {
                val transactionManager = builder.catalog(Catalog(Vendors.H2.name)).build()
                H2CatalogParser(codeGeneratorConfig, transactionManager, logger)
            }
            else -> throw IllegalStateException("Vendor ${vendor} not allowed")
        }
    }

    private fun createDataSource(dataSourceInfo: DataSourceInfo): DataSource {
        return HikariDataSourceFactory.create(
            url = dataSourceInfo.url,
            username = dataSourceInfo.user,
            password = dataSourceInfo.password,
            driver = dataSourceInfo.driver
        )
    }
}
