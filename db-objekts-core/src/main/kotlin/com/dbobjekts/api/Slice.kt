package com.dbobjekts.api

/**
 * Represents a slice of a result set, starting with `skip` and containing `limit` rows
 */
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

    companion object {
        fun singleRow(): Slice {
            return Slice(0, 1)
        }
    }

}
