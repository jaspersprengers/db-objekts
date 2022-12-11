package com.dbobjekts.codegen.parsers

import com.dbobjekts.codegen.ProgressLogger
import com.dbobjekts.codegen.configbuilders.CodeGeneratorConfig
import com.dbobjekts.codegen.configbuilders.DataSourceInfo
import com.dbobjekts.codegen.parsers.mysql.MariaDBCatalogParser
import com.dbobjekts.jdbc.TransactionManagerBuilder
import com.dbobjekts.util.HikariDataSourceFactory
import com.dbobjekts.vendors.MariaDB
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import javax.sql.DataSource


object LiveDBParserFactory {

    fun create(codeGeneratorConfig: CodeGeneratorConfig,
               logger: ProgressLogger): LiveDBParser {
        val dataSource = createDataSource(codeGeneratorConfig.dataSourceInfo?: throw IllegalArgumentException("dataSourceInfo cannot be null"))
        val transactionManager = TransactionManagerBuilder().dataSource(dataSource).build()
        return when (codeGeneratorConfig.vendor) {
            is MariaDB -> MariaDBCatalogParser(codeGeneratorConfig, transactionManager, logger)
            else -> throw IllegalStateException("Not allowed")
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
