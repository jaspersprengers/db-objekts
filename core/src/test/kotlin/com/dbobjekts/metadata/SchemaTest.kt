package com.dbobjekts.metadata

import com.dbobjekts.Catalogdefinition
import com.dbobjekts.Core
import com.dbobjekts.Employee
import com.dbobjekts.Hr
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test


class SchemaTest {

    @Test
    fun `Catalog has one schema`() {
        assertEquals(Catalogdefinition.schemas.size, 2)
    }

    @Test
    fun `Catalog has schema default`() {
        assertNotNull(Catalogdefinition.schemaByName("HR"))
        assertEquals("H2", Catalogdefinition.vendor)
    }

    @Test
    fun `Schema has one table`() {
        assertEquals(6, Core.tables.size)
    }

    @Test
    fun `Schema has reference to catalog`() {
        assertEquals(Catalogdefinition, Hr.catalog)
    }


    @Test
    fun `Table Employee has reference to Schema core`() {
        assertEquals("CORE", Employee.schemaName().value)
    }

    @Test
    fun `Alias for table is e`() {
        assertEquals("e", Employee.alias())
    }
}
