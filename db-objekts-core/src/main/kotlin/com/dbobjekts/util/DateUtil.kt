package com.dbobjekts.util

import java.sql.Time
import java.sql.Timestamp
import java.time.*

object DateUtil {

    //conversions from java.sql.Date|Time|Timestamp

    fun toInstant(timestamp: java.sql.Timestamp): Instant = timestamp.toInstant()

    fun toLocalTime(time: java.sql.Time): LocalTime = time.toLocalTime()

    fun toUtilDate(date: java.sql.Date): java.util.Date = java.util.Date(date.getTime())

    fun toUtilDate(date: java.sql.Timestamp): java.util.Date = java.util.Date(date.getTime())

    //conversions from java.util.Date

    fun toSqlDate(date: java.util.Date): java.sql.Date = java.sql.Date(date.getTime())

    fun toLocalDateTime(date: java.util.Date,
                        zoneId: ZoneId = ZoneId.systemDefault()): LocalDateTime =
        LocalDateTime.ofInstant(date.toInstant(), zoneId)

    fun toLocalDate(date: java.util.Date,
                    zoneId: ZoneId = ZoneId.systemDefault()): LocalDate =
        date.toInstant().atZone(zoneId).toLocalDate()

    //conversions from java.time objects

    fun toTime(localDateTime: LocalTime): Time = Time.valueOf(localDateTime)

    fun toSqlTimeStamp(localDateTime: LocalDateTime,
                       zoneId: ZoneId = ZoneId.systemDefault()): Timestamp =
        toSqlTimeStamp(localDateTime.atZone(zoneId).toInstant())

    fun toSqlTimeStamp(instant: Instant): Timestamp = Timestamp.from(instant)

    fun toUtilDate(localDate: LocalDate,
                   zoneId: ZoneId = ZoneId.systemDefault()): java.util.Date =
        java.util.Date.from(localDate.atStartOfDay(zoneId).toInstant())

    fun toSqlDate(localDate: LocalDate): java.sql.Date = toSqlDate(toUtilDate(localDate))


}
