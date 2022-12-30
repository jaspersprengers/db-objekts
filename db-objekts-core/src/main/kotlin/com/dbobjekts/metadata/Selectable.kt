package com.dbobjekts.metadata

import com.dbobjekts.api.AnyColumn

interface Selectable<I> {
    val columns: List<AnyColumn>
    fun toValue(values: List<Any?>): I
}
