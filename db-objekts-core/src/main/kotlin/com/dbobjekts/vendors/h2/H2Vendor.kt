package com.dbobjekts.vendors.h2

import com.dbobjekts.codegen.datatypemapper.ColumnTypeMapper
import com.dbobjekts.codegen.parsers.VendorSpecificMetaDataExtractor
import com.dbobjekts.vendors.Vendor
import com.dbobjekts.vendors.VendorSpecificProperties


object H2Vendor : Vendor {
    override val name: String = "H2"
    override val defaultMapper: ColumnTypeMapper = H2DataTypeMapper()
    override val properties: VendorSpecificProperties = H2Properties()
    override val metadataExtractor: VendorSpecificMetaDataExtractor = H2MetaDataExtractor
}
