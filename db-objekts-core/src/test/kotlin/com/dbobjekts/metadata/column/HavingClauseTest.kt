package com.dbobjekts.metadata.column

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class HavingClauseTest {

    @Test
    fun `simple condition`(){
        val cond: HavingClauseForLong = Aggregate.gt(5)
        Assertions.assertThat(cond.toSQL()).isEqualTo("having AGGREGATE > ?")
    }

    @Test
    fun `two conditions`(){
        val cond: HavingClauseForLong = Aggregate.gt(5).and().lt(10)
        Assertions.assertThat(cond.toSQL()).isEqualTo("having AGGREGATE > ? and AGGREGATE < ?")
    }

    @Test
    fun `three conditions`(){
        val cond: HavingClauseForLong = Aggregate.eq(5).or().lt(10).or().lt(10)
        Assertions.assertThat(cond.toSQL()).isEqualTo("having AGGREGATE = ? or AGGREGATE < ? or AGGREGATE < ?")
        cond.parameters(4).forEach { println(it) }
    }

}
