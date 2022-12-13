package com.dbobjekts.vendors.mysql

import com.dbobjekts.vendors.VendorSpecificProperties

class MySQLProperties:VendorSpecificProperties {

    override fun sequencePattern(): String = ""

    override fun columnTypeCanBeAutoGeneratedKey(columnType: String): Boolean = MySQLDataTypeMapper.numericTypes.contains(columnType.lowercase())

    override fun supportsJoinsInUpdateAndDelete(): Boolean = true

}
