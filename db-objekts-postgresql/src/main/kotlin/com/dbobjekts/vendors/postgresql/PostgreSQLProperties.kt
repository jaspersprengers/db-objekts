package com.dbobjekts.vendors.postgresql

import com.dbobjekts.vendors.VendorSpecificProperties

class PostgreSQLProperties:VendorSpecificProperties {

    override fun sequencePattern(): String = "select nextval('%s')"

    override fun supportsJoinsInUpdateAndDelete(): Boolean = false

}
