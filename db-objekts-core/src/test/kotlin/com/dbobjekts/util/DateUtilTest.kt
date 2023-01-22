package com.dbobjekts.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.sql.Date
import java.time.LocalDate
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

    @Test
    fun `round trip conversions of LocalDateTime`() {
        val sqlTimestamp = DateUtil.toSqlTimeStamp(refDateTime)
        val backToLocalDateTime = DateUtil.toLocalDateTime(sqlTimestamp)
        assertThat(refDateTime).isEqualTo(backToLocalDateTime)
    }

    @Test
    fun `round trip conversions of Instant`() {
        val sqlTimestamp = DateUtil.toSqlTimeStamp(refDateTime.toInstant(ZoneOffset.UTC))
        val backToInstant = DateUtil.toInstant(sqlTimestamp)
        assertThat(refDateTime.toInstant(ZoneOffset.UTC)).isEqualTo(backToInstant)
    }

    @Test
    fun `round trip conversions of LocalDate`() {
        val sqlDate: Date = DateUtil.toSqlDate(refDateTime.toLocalDate())
        val backToLocalDate: LocalDate = DateUtil.toLocalDate(java.util.Date(sqlDate.time))
        assertThat(refDateTime.toLocalDate()).isEqualTo(backToLocalDate)
    }

    @Test
    fun `round trip conversions of LocalTime`() {
        val sqlTime = DateUtil.toTime(refDateTime.toLocalTime())
        val backToLocalTime = DateUtil.toLocalTime(sqlTime)
        assertThat(refDateTime.toLocalTime()).isEqualTo(backToLocalTime)
    }

    @Test
    fun `round trip conversions of util date`() {
        val sqlTimestamp = DateUtil.toSqlTimeStamp(utilDate.toInstant())
        val backToUtilDate = DateUtil.toUtilDate(sqlTimestamp)
        assertThat(utilDate).isEqualTo(backToUtilDate)
    }

}
