package com.dbobjekts.metadata

import com.dbobjekts.api.AnyColumn

object DefaultTable : Table<Boolean>("dummy_table") {
    override val columns = listOf<AnyColumn>()
    override fun toValue(values: List<Any?>) = true
}
