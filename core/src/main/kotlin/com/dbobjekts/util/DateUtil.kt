package com.dbobjekts.util

import java.sql.Time
import java.sql.Timestamp
import java.time.*

object DateUtil {

    //conversions from raw string or integers
    fun createDate(yr: Int, mn: Int, day: Int): java.util.Date = toUtilDate(LocalDate.of(yr, mn, day))

    //coversions from java.sql.Date|Time|Timestamp

    fun toInstant(timestamp: java.sql.Timestamp): Instant = timestamp.toInstant()

    fun toLocalTime(time: java.sql.Time): LocalTime = time.toLocalTime()

    fun toUtilDate(date: java.sql.Date): java.util.Date = java.util.Date(date.getTime())

    fun toUtilDate(date: java.sql.Timestamp): java.util.Date = java.util.Date(date.getTime())

    fun toZonedDateTime(date: java.sql.Timestamp, zoneId: java.time.ZoneId = ZoneId.systemDefault()): ZonedDateTime = ZonedDateTime.of(toLocalDateTime(toUtilDate(date)), zoneId)

    //conversions from java.util.Date

    fun toSqlDate(date: java.util.Date): java.sql.Date = java.sql.Date(date.getTime())

    fun toLocalDateTime(date: java.util.Date,
                        zoneId: java.time.ZoneId = ZoneId.systemDefault()): LocalDateTime =
        LocalDateTime.ofInstant(date.toInstant(), zoneId)

    fun toLocalDate(date: java.util.Date,
                    zoneId: java.time.ZoneId = ZoneId.systemDefault()): LocalDate =
        date.toInstant().atZone(zoneId).toLocalDate()

    //conversions from java.time objects

    fun toTime(localDateTime: LocalTime): Time = Time.valueOf(localDateTime)

    fun toSqlTimeStamp(localDateTime: LocalDateTime,
                       zoneId: java.time.ZoneId = ZoneId.systemDefault()): Timestamp =
        toSqlTimeStamp(localDateTime.atZone(zoneId).toInstant())

    fun toSqlTimeStamp(zonedDateTime: ZonedDateTime): Timestamp = toSqlTimeStamp(zonedDateTime.toInstant())

    fun toSqlTimeStamp(instant: Instant): Timestamp = Timestamp.from(instant)

    fun toUtilDate(localDate: LocalDate,
                   zoneId: java.time.ZoneId = ZoneId.systemDefault()): java.util.Date =
        java.util.Date.from(localDate.atStartOfDay(zoneId).toInstant())

    fun toSqlDate(localDate: LocalDate): java.sql.Date = toSqlDate(toUtilDate(localDate))


}
