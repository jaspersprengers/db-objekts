package com.dbobjekts.metadata.column

/**
 * Optional property of [Column] to indicate whether it is involved in an aggregate operation.
 */
enum class AggregateType(val usesGroupBy: Boolean) {
    AVG(true), SUM(true), MIN(true), MAX(true), COUNT(true),
    DISTINCT(false) {
        override fun forColumn(aliasDotName: String): String = "DISTINCT ${aliasDotName} as ${Aggregate.ALIAS}"
    },
    COUNT_DISTINCT(true) {
        override fun forColumn(aliasDotName: String): String = "COUNT(DISTINCT ${aliasDotName}) as ${Aggregate.ALIAS}"
    };

    /**
     * @return E.g. SUM(e.id) as AGGR
     */
    open fun forColumn(aliasDotName: String): String = "$name(${aliasDotName}) as ${Aggregate.ALIAS}"

}
