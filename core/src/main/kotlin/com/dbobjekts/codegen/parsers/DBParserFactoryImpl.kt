package com.dbobjekts.codegen.parsers

import com.dbobjekts.codegen.configbuilders.CodeGeneratorConfig
import com.dbobjekts.api.TransactionManagerBuilder
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.vendors.Vendors
import com.dbobjekts.vendors.h2.H2CatalogParser
import com.dbobjekts.vendors.mariadb.MariaDBCatalogParser


class DBParserFactoryImpl : DBParserFactory {

    override fun create(
        codeGeneratorConfig: CodeGeneratorConfig
    ): CatalogParser {
        val builder = TransactionManagerBuilder().dataSource(codeGeneratorConfig.dataSource)

        return when (val vendor = codeGeneratorConfig.vendor) {
            Vendors.MARIADB -> {
                val transactionManager = builder.catalog(Catalog(Vendors.MARIADB.name)).build()
                MariaDBCatalogParser(codeGeneratorConfig, transactionManager)
            }

            Vendors.H2 -> {
                val transactionManager = builder.catalog(Catalog(Vendors.H2.name)).build()
                H2CatalogParser(codeGeneratorConfig, transactionManager)
            }

            else -> throw IllegalStateException("Vendor ${vendor} not allowed")
        }
    }

}
