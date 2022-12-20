package com.dbobjekts.codegen.parsers

import com.dbobjekts.api.Catalogs
import com.dbobjekts.api.TransactionManager
import com.dbobjekts.codegen.configbuilders.CodeGeneratorConfig
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.vendors.Vendors
import com.dbobjekts.vendors.h2.H2CatalogParser
import com.dbobjekts.vendors.mariadb.MariaDBCatalogParser


class DBParserFactoryImpl : DBParserFactory {

    override fun create(
        codeGeneratorConfig: CodeGeneratorConfig
    ): CatalogParser {

        return when (val vendor = codeGeneratorConfig.vendor) {
            Vendors.MARIADB -> {
                val transactionManager = TransactionManager(codeGeneratorConfig.dataSource, Catalogs.EMPTY_MARIADB_CATALOG)
                MariaDBCatalogParser(codeGeneratorConfig, transactionManager)
            }

            Vendors.H2 -> {
                val transactionManager = TransactionManager(codeGeneratorConfig.dataSource, Catalogs.EMPTY_H2_CATALOG)
                H2CatalogParser(codeGeneratorConfig, transactionManager)
            }

            else -> throw IllegalStateException("Vendor ${vendor} not allowed")
        }
    }

}
