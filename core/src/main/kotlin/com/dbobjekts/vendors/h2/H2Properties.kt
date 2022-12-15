package com.dbobjekts.vendors.h2

import com.dbobjekts.vendors.VendorSpecificProperties

class H2Properties:VendorSpecificProperties {

    override fun sequencePattern(): String = "call nextval('%s')"

    override fun supportsJoinsInUpdateAndDelete(): Boolean = false

}
