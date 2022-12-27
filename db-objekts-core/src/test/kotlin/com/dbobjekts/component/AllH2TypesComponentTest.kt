package com.dbobjekts.component

import com.dbobjekts.testdb.acme.core.AllTypes
import com.dbobjekts.metadata.column.BlobColumn
import com.dbobjekts.testdb.AddressType
import org.assertj.core.api.Assertions.assertThat
import org.h2.api.Interval
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.*
import java.util.*


class AllH2TypesComponentTest {

    companion object {
        @BeforeAll
        @JvmStatic
        fun beforeGroup() {
            AcmeDB.setupDatabaseObjects()
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
        val uuid = UUID.randomUUID()
        AcmeDB.newTransaction { tr ->

            val row = tr.insert(AllTypes)
                .characterCol("C")
                .characterColNil(null)
                .charactervaryingCol("Hello world")
                .charactervaryingColNil(null)
                .characterlargeobjectCol("Hello world")
                .characterlargeobjectColNil(null)
                .varcharIgnorecaseCol("HeLlLo WoRlD")
                .varcharIgnorecaseColNil(null)
                .enumCol("maybe")
                .enumColNil(null)
                .binaryCol("bytearray".encodeToByteArray())
                .binaryColNil(null)
                .binaryvaryingCol("binary varying".encodeToByteArray())
                .binaryvaryingColNil(null)
                .binarylargeobjectCol(BlobColumn.ofString(fox))
                .binarylargeobjectColNil(null)
                .jsonCol("{\"value\": 42}".encodeToByteArray())
                .jsonColNil(null)
                .booleanCol(true)
                .booleanColNil(null)
                .tinyintCol(0B1)
                .tinyintColNil(null)
                .smallintCol(42)
                .smallintColNil(null)
                .integerCol(100_000)
                .integerColNil(null)
                .bigintCol(1_000_000_000L)
                .bigintColNil(null)
                .numericCol(BigDecimal.TEN)
                .numericColNil(null)
                .decfloatCol(BigDecimal.TEN)
                .decfloatColNil(null)
                .realCol(42.42f)
                .realColNil(null)
                .doubleprecisionCol(100_000.999999)
                .doubleprecisionColNil(null)
                .dateCol(date)
                .dateColNil(null)
                .timeCol(time)
                .timeColNil(null)
                .timewithtimezoneCol(dateTimeEuropeAsOffset)
                .timewithtimezoneColNil(null)
                .timestampCol(dateTimeEurope.toInstant())
                .timestampColNil(null)
                .timestampwithtimezoneCol(dateTimeEuropeAsOffset)
                .timestampwithtimezoneColNil(null)
                .uuidCol(uuid)
                .uuidColNil(null)
                .intervalCol(Interval.ofMonths(15))
                .intervalColNil(null)
                .addressInt(AddressType.HOME)
                .addressString(AddressType.WORK)
                //.geometryCol("GEOMETRY X'00000000013ff00000000000003ff0000000000000'")
                //.geometryColNil(null)
                .intArrayCol(arrayOf(1, 2, 3))
                .execute()

            assertEquals("C", tr.select(t.characterCol).first())
            assertEquals("Hello world", tr.select(t.charactervaryingCol).first())
            assertEquals("Hello world", tr.select(t.characterlargeobjectCol).first())
            assertEquals("HeLlLo WoRlD", tr.select(t.varcharIgnorecaseCol).first())
            assertEquals("maybe", tr.select(t.enumCol).first())
            assertThat(tr.select(t.binaryCol).first()).hasSize(1024)
            assertThat(String(tr.select(t.binaryvaryingCol).first())).isEqualTo("binary varying")
            assertThat(BlobColumn.serialize(tr.select(t.binarylargeobjectCol).first())).isEqualTo("The quick brown fox")


            assertEquals(0B1, tr.select(t.tinyintCol).first())
            assertEquals(42, tr.select(t.smallintCol).first())
            assertEquals(100_000, tr.select(t.integerCol).first())
            assertEquals(1_000_000_000L, tr.select(t.bigintCol).first())
            assertEquals(10, tr.select(t.numericCol).first().toLong())
            assertEquals(10, tr.select(t.decfloatCol).first().toLong())
            assertEquals(42.42f, tr.select(t.realCol).first())
            assertEquals(100_000.999999, tr.select(t.doubleprecisionCol).first())

            assertEquals(15, tr.select(t.intervalCol).first().months)
            //assertThat(tr.select(t.intArrayCol).first().asList()).containsExactly(1,2,3)
            //assertThat(tr.select(t.objectCol).first().toString()).isEqualTo("John")
            assertEquals(uuid, tr.select(t.uuidCol).first())

            assertEquals(AddressType.HOME, tr.select(t.addressInt).first())
            assertEquals(AddressType.WORK, tr.select(t.addressString).first())

            assertEquals(time.withNano(0), tr.select(t.timeCol).first())
            assertEquals(date, tr.select(t.dateCol).first())
            assertEquals(dateTimeEurope.toInstant(), tr.select(t.timestampCol).first())
            assertEquals(dateTimeEuropeAsOffset, tr.select(t.timestampwithtimezoneCol).first())
            assertTrue(tr.select(t.booleanCol).first())


        }

    }
}
