package com.dbobjekts.metadata

internal interface SerializableToSQL {
    fun toSQL(): String
}
