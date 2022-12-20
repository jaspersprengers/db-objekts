package com.dbobjekts.jdbc

import com.dbobjekts.api.TransactionManager
import com.dbobjekts.metadata.DBConnectionMetaData
import com.dbobjekts.util.StringUtil
import com.dbobjekts.vendors.Vendors


object DetermineVendor {

    operator fun invoke(transactionManager: TransactionManager): DBConnectionMetaData {
        return transactionManager.newTransaction {
            val metaData = it.connection().metaData
            val vendor =
                Vendors.byProductAndVersion(
                    metaData.getDatabaseProductName(),
                    metaData.getDatabaseProductVersion()
                )
            val rs = metaData.catalogs
            val catalogs = mutableListOf<String>()
            while (rs.next()) {
                rs.getString(1).also {
                    catalogs += StringUtil.initUpperCase(it)
                }
            }
            DBConnectionMetaData(vendor, catalogs)
        }
    }

}
