package com.dbobjekts.fixture

import java.time.*

object DateFixtures {

    val time = LocalTime.of(12, 13, 14).withNano(123456000)

    val date = LocalDate.of(1980, 12, 5)

    val dateTime = LocalDateTime.of(date, time)

    val australia = ZoneId.of("Australia/Brisbane")

    val europe = ZoneId.of("Europe/Madrid")

    val dateTimeEurope = ZonedDateTime.of(dateTime, europe)

    val dateTimeEuropeAsOffset = OffsetDateTime.ofInstant(dateTimeEurope.toInstant(), europe)

    val dateTimeAsia = ZonedDateTime.of(dateTime, australia)

}
