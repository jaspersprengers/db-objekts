package com.dbobjekts.metadata.joins

import com.dbobjekts.api.AnyTable


abstract class JoinChain(val table: AnyTable) {
    abstract fun toSQL(): String
}
