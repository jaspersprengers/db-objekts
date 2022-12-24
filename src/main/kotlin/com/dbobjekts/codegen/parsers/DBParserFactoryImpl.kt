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
        ParserConfig
        val transactionManager = TransactionManager.builder().withDataSource(codeGeneratorConfig.dataSource).build()
        val vendor = transactionManager.vendor
        val config = ParserConfig.fromCodeGeneratorConfig(vendor, codeGeneratorConfig)
        return when (vendor) {
            Vendors.MARIADB -> {
                MariaDBCatalogParser(config, transactionManager)
            }
            Vendors.H2 -> {
                H2CatalogParser(config, transactionManager)
            }

            else -> throw IllegalStateException("Vendor ${vendor} not allowed")
        }
    }

}
