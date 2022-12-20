package com.dbobjekts.codegen.parsers

import com.dbobjekts.api.TransactionManager
import com.dbobjekts.codegen.configbuilders.CodeGeneratorConfig
import com.dbobjekts.vendors.Vendors
import com.dbobjekts.vendors.h2.H2CatalogParser
import com.dbobjekts.vendors.mariadb.MariaDBCatalogParser


class DBParserFactoryImpl : DBParserFactory {

    override fun create(
        codeGeneratorConfig: CodeGeneratorConfig
    ): CatalogParser {
        val transactionManager = TransactionManager.builder().withDataSource(codeGeneratorConfig.dataSource).build()
        return when (val vendor = codeGeneratorConfig.vendor) {
            Vendors.MARIADB -> {
                MariaDBCatalogParser(codeGeneratorConfig, transactionManager)
            }

            Vendors.H2 -> {
                H2CatalogParser(codeGeneratorConfig, transactionManager)
            }

            else -> throw IllegalStateException("Vendor ${vendor} not allowed")
        }
    }

}
