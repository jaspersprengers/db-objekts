package com.dbobjekts.vendors.mysql

import com.dbobjekts.codegen.datatypemapper.VendorDefaultColumnTypeMapper
import com.dbobjekts.codegen.parsers.VendorSpecificMetaDataExtractor
import com.dbobjekts.vendors.Vendor
import com.dbobjekts.vendors.VendorSpecificProperties

object MysqlVendor : Vendor {
    override val name: String = "MYSQL"
    override val defaultMapper: VendorDefaultColumnTypeMapper = MysqlDataTypeMapper()
    override val properties: VendorSpecificProperties = MysqlProperties()
    override val metadataExtractor: VendorSpecificMetaDataExtractor = MysqlMetadataExtractor
}
