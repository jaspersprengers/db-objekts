package com.dbobjekts.metadata.joins

import com.dbobjekts.api.AnyTable
import com.dbobjekts.statement.SQLOptions
import com.dbobjekts.statement.SQLOptions.Companion.ALIAS

abstract class JoinChain(val table: AnyTable) {
    abstract fun hasJoins(): Boolean
    abstract fun toSQL(options: SQLOptions = ALIAS): String
}
