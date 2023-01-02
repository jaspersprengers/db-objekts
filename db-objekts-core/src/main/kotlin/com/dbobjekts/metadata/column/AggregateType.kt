package com.dbobjekts.metadata.column

import com.dbobjekts.api.AnyColumn


enum class AggregateType {
    AVG, SUM, MIN, MAX, COUNT;

    /**
     * @return E.g. SUM(e.id) as AGGR
     */
    fun forColumn(c: AnyColumn): String = "$name(${c.aliasDotName()}) as ${Aggregate.ALIAS}"

}
