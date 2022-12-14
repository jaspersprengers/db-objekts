package com.dbobjekts.vendors

interface VendorSpecificProperties {

    fun sequencePattern(): String? = null

    fun getNotEqualsOperator() = "<>"

    fun supportsJoinsInUpdateAndDelete(): Boolean

    fun getLimitClause(maxRows: Int): String = "limit $maxRows"

}
