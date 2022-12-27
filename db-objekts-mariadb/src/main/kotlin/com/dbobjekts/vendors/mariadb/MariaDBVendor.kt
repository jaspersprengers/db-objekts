package com.dbobjekts.vendors.mariadb

import com.dbobjekts.codegen.datatypemapper.ColumnTypeMapper
import com.dbobjekts.codegen.datatypemapper.VendorDefaultColumnTypeMapper
import com.dbobjekts.codegen.parsers.VendorSpecificMetaDataExtractor
import com.dbobjekts.vendors.Vendor
import com.dbobjekts.vendors.VendorSpecificProperties

object MariaDBVendor : Vendor {
    override val name: String = "MARIADB"
    override val defaultMapper: VendorDefaultColumnTypeMapper = MariaDBDataTypeMapper()
    override val properties: VendorSpecificProperties = MariaDBProperties()
    override val metadataExtractor: VendorSpecificMetaDataExtractor = MariaDBMetadataExtractor
}
