package com.dbobjekts.vendors.postgresql

import com.dbobjekts.codegen.datatypemapper.VendorDefaultColumnTypeMapper
import com.dbobjekts.codegen.parsers.VendorSpecificMetaDataExtractor
import com.dbobjekts.vendors.Vendor
import com.dbobjekts.vendors.VendorSpecificProperties

object PostgreSQLVendor : Vendor {
    override val name: String = "POSTGRESQL"
    override val defaultMapper: VendorDefaultColumnTypeMapper = PostgreSQLDataTypeMapper()
    override val properties: VendorSpecificProperties = PostgreSQLProperties()
    override val metadataExtractor: VendorSpecificMetaDataExtractor = PostgreSQLMetaDataExtractor
}
