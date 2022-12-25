package com.dbobjekts.metadata

import com.dbobjekts.sampledbs.h2.acme.TestCatalog
import com.dbobjekts.sampledbs.h2.acme.core.Core
import com.dbobjekts.sampledbs.h2.acme.core.Employee
import com.dbobjekts.sampledbs.h2.acme.hr.Hr
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test


class SchemaTest {

    @Test
    fun `Catalog has one schema`() {
        assertEquals(TestCatalog.schemas.size, 2)
    }

    @Test
    fun `Catalog has schema default`() {
        assertNotNull(TestCatalog.schemaByName("HR"))
        assertEquals("H2", TestCatalog.vendor)
    }

    @Test
    fun `Schema has 7 tables`() {
        assertEquals(7, Core.tables.size)
    }

    @Test
    fun `Schema has reference to catalog`() {
        assertEquals(TestCatalog, Hr.catalog)
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
