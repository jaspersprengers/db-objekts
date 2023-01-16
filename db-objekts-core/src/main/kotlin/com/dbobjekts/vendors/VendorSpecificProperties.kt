package com.dbobjekts.vendors

interface VendorSpecificProperties {

    fun sequencePattern(): String? = null

    fun supportsJoinsInUpdateAndDelete(): Boolean

    fun getLimitClause(maxRows: Int): String = "limit $maxRows"

}
