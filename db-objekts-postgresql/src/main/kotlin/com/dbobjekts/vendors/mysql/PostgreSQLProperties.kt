package com.dbobjekts.vendors.mysql

import com.dbobjekts.vendors.VendorSpecificProperties

class PostgreSQLProperties:VendorSpecificProperties {

    override fun sequencePattern(): String = ""

    override fun supportsJoinsInUpdateAndDelete(): Boolean = true

}
