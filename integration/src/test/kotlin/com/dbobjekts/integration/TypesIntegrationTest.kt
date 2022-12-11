package com.dbobjekts.integration

import com.dbobjekts.example.custom.AllTypes
import com.dbobjekts.fixture.h2.H2DB
import com.dbobjekts.metadata.column.*
import com.dbobjekts.statement.whereclause.SubClause
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import java.lang.Byte
import java.time.*


class TypesIntegrationTest {

    companion object {
        @BeforeAll
        @JvmStatic
        fun beforeGroup() {
            H2DB.setupDatabaseObjects()
        }
    }


    val time = LocalTime.of(12, 13, 14).withNano(123456000)
    val date = LocalDate.of(1980, 12, 5)
    val dateTime = LocalDateTime.of(date, time)
    val australia = ZoneId.of("Australia/Brisbane")
    val europe = ZoneId.of("Europe/Madrid")
    val dateTimeEurope = ZonedDateTime.of(dateTime, europe)
    val dateTimeEuropeAsOffset = OffsetDateTime.ofInstant(dateTimeEurope.toInstant(), europe)
    val dateTimeAsia = ZonedDateTime.of(dateTime, australia)

    @Test
    fun `save and retrieve`() {
        val fox = "The quick brown fox"
        val t = AllTypes
        H2DB.newTransaction { tr ->
            val execute = tr.insert(AllTypes)
                .tinyintC(java.lang.Byte.parseByte("2"))
                .smallintC(42)
                .intC(424242)
                .charC("C")
                .varcharC("Hello")
                .bigintC(999999L)
                .floatC(42.43f)
                .doubleC(44.44)
                .timeC(time)
                .dateC(date)
                .timestampC(dateTimeEurope.toInstant())
                .timestampTzC(dateTimeEuropeAsOffset)
                .booleanC(true)
                .intBooleanC(true)
                .blobC(BlobColumn.ofString(fox))
                .clobC(ClobColumn.ofString(fox)).execute()

            /*val row: Tuple16<Byte?, Int?, Int?, String?, String?, Long?, Float?, Double?, LocalTime?, LocalDate?, Instant?, OffsetDateTime?, Boolean?, Boolean, Blob?, Clob?> =
                 tr.select(t.timestampC, t.timestampTzC, t.booleanC, t.intBooleanC, t.blobC, t.clobC)
                 .where(AllTypes.intC.eq(424242)).first()*/
            val where: SubClause = AllTypes.intC.eq(424242)
            assertEquals(tr.select(t.tinyintC).where(where).first(), Byte.parseByte("2"))
            assertEquals(42, tr.select(t.smallintC).where(where).first())
            assertEquals(424242, tr.select(t.intC).where(where).first())
            assertEquals("C", tr.select(t.charC).where(where).first())
            assertEquals("Hello", tr.select(t.varcharC).where(where).first())
            assertEquals(999999L, tr.select(t.bigintC).where(where).first())
            assertEquals(42.43f, tr.select(t.floatC).where(where).first())
            assertEquals(44.44, tr.select(t.doubleC).where(where).first())
            assertEquals(time.withNano(0), tr.select(t.timeC).where(where).first())
            assertEquals(date, tr.select(t.dateC).where(where).first())
            assertEquals(dateTimeEurope.toInstant(), tr.select(t.timestampC).where(where).first())
            assertEquals(dateTimeEuropeAsOffset, tr.select(t.timestampTzC).where(where).first())
            assertTrue(tr.select(t.booleanC).where(where).first() ?: false)
            assertTrue(tr.select(t.intBooleanC).where(where).first())
            /*assertEquals(19, tr.select(t.blobC).where(where).first())?.length())
            assertEquals(19, tr.select(t.clobC).where(where).first())?.length())*/
        }

    }
}
