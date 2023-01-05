package com.dbobjekts.codegen.parsers

import com.dbobjekts.api.TransactionManager

interface VendorSpecificMetaDataExtractor {
    fun extractColumnAndTableMetaDataFromDB(transactionManager: TransactionManager): List<TableMetaDataRow>
    fun extractForeignKeyMetaDataFromDB(transactionManager: TransactionManager): List<ForeignKeyMetaDataRow>
}
