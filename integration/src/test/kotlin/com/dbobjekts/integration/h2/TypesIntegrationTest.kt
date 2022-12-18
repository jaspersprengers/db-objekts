package com.dbobjekts.integration.h2

import com.dbobjekts.integration.h2.core.AllTypes
import com.dbobjekts.metadata.column.BlobColumn
import com.dbobjekts.statement.whereclause.SubClause
import org.h2.api.Interval
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.Serializable
import java.math.BigDecimal
import java.time.*
import java.util.*


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
                .uuidCol(UUID.randomUUID())
                .uuidColNil(null)
                .intervalCol(Interval.ofMonths(15))
                .intervalColNil(null)
                //.geometryCol("GEOMETRY X'00000000013ff00000000000003ff0000000000000'")
                //.geometryColNil(null)
                .intArrayCol(arrayOf(1, 2, 3))
                .objectCol(SerializableColumn("John"))
                .execute()

            val where: SubClause = AllTypes.id.eq(row)
            assertEquals("C", tr.select(t.characterCol).where(where).first())
            assertEquals("Hello world", tr.select(t.charactervaryingCol).where(where).first())
            assertEquals("Hello world", tr.select(t.characterlargeobjectCol).where(where).first())
            assertEquals("HeLlLo WoRlD", tr.select(t.varcharIgnorecaseCol).where(where).first())
            assertEquals("maybe", tr.select(t.enumCol).where(where).first())

            //assertEquals(15, tr.select(t.intervalCol).where(where).first().months)
            //assertThat(tr.select(t.intArrayCol).where(where).first().asList()).containsExactly(1,2,3)
            //assertThat(tr.select(t.objectCol).where(where).first().toString()).isEqualTo("name")

            /*assertEquals(42, tr.select(t.smallintC).where(where).first())
            assertEquals(424242, tr.select(t.intC).where(where).first())
            assertEquals("C", tr.select(t.charC).where(where).first())
            assertEquals("Hello", tr.select(t.varcharC).where(where).first())
            assertEquals(999999L, tr.select(t.bigintC).where(where).first())
            assertEquals(42.43f, tr.select(t.floatC).where(where).first())
            assertEquals(44.44, tr.select(t.doubleC).where(where).first())*/
            assertEquals(time.withNano(0), tr.select(t.timeCol).where(where).first())
            assertEquals(date, tr.select(t.dateCol).where(where).first())
            assertEquals(dateTimeEurope.toInstant(), tr.select(t.timestampCol).where(where).first())
            assertEquals(dateTimeEuropeAsOffset, tr.select(t.timestampwithtimezoneCol).where(where).first())
            assertTrue(tr.select(t.booleanCol).where(where).first() ?: false)
            //assertTrue(tr.select(t.intBooleanC).where(where).first())
            //assertEquals(19, tr.select(t.blobC).where(where).first())?.length())

        }

    }
}

class SerializableColumn(var name: String) : Serializable{

}
