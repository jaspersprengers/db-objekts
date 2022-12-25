package com.dbobjekts.codegen

import com.dbobjekts.api.ColumnName
import com.dbobjekts.api.TableName
import com.dbobjekts.codegen.metadata.DBSequenceKeyDefinition
import com.dbobjekts.metadata.ColumnFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class DBSequenceKeyDefinitionTest {

    val key = DBSequenceKeyDefinition(
        TableName("people"), ColumnName("id"), "people_seq", ColumnFactory.SEQUENCE_LONG, "comment"
    )


    @Test
    fun `factory method`() {
        assertEquals("""com.dbobjekts.metadata.column.SequenceKeyLongColumn(this, "id", "people_seq")""", key.asFactoryMethod())
    }

    @Test
    fun `fully qualified`() {
        assertEquals("""com.dbobjekts.metadata.column.SequenceKeyLongColumn""", key.fullyQualifiedClassName())
    }

}

