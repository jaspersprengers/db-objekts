package com.dbobjekts.metadata

import com.dbobjekts.api.AnyColumn

object DefaultTable : Table<Boolean>("default") {
    override val columns = listOf<AnyColumn>()
    override fun toValue(values: List<Any?>) = true
}
