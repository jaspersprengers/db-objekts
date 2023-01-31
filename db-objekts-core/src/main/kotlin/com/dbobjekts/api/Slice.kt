package com.dbobjekts.api


data class Slice(
    val skip: Long,
    val limit: Long
) {
    init {
        if (skip < 0)
            throw IllegalArgumentException("Skip must be >= 0")
        if (limit < 1)
            throw IllegalArgumentException("Limit must be >= 1")
    }
}
