package com.dbobjekts.fixture

import java.time.*

 interface DateTimeFixtures {

    val time : LocalTime

    val date : LocalDate

    val dateTime : LocalDateTime

    val australia : ZoneId

    val europe : ZoneId

    val dateTimeEurope : ZonedDateTime

    val dateTimeEuropeAsOffset : OffsetDateTime

    val dateTimeAsia : ZonedDateTime

}

class DateTimeFixturesImpl : DateTimeFixtures {

    override val time = LocalTime.of(12, 13, 14).withNano(123456000)

    override val date = LocalDate.of(1980, 12, 5)

    override val dateTime = LocalDateTime.of(date, time)

    override val australia = ZoneId.of("Australia/Brisbane")

    override val europe = ZoneId.of("Europe/Madrid")

    override val dateTimeEurope = ZonedDateTime.of(dateTime, europe)

    override val dateTimeEuropeAsOffset = OffsetDateTime.ofInstant(dateTimeEurope.toInstant(), europe)

    override val dateTimeAsia = ZonedDateTime.of(dateTime, australia)

}
