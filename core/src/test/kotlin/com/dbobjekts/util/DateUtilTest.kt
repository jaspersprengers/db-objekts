package com.dbobjekts.util

import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.ZoneOffset


class DateUtilTest {

    val refDateTime = LocalDateTime.of(2020, 5, 7, 10, 10, 10)
    val utilDate: java.util.Date = java.util.Date.from(refDateTime.toInstant(ZoneOffset.UTC))

    @Test
    fun `round trip conversions of Date`() {
        val sqlDate = DateUtil.toSqlDate(utilDate)
        val backToUtil = DateUtil.toUtilDate(sqlDate)
        assert(sqlDate.time == backToUtil.time)
    }

}
