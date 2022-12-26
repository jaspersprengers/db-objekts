package com.dbobjekts.vendors.mariadb

import com.dbobjekts.vendors.VendorSpecificProperties

class MariaDBProperties:VendorSpecificProperties {

    override fun sequencePattern(): String = ""

    override fun supportsJoinsInUpdateAndDelete(): Boolean = true

}
