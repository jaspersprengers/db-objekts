package com.dbobjekts.metadata

import com.dbobjekts.testdb.acme.CatalogDefinition
import com.dbobjekts.testdb.acme.core.Core
import com.dbobjekts.testdb.acme.core.Employee
import com.dbobjekts.testdb.acme.hr.Hr
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test


class SchemaTest {

    @Test
    fun `Catalog has one schema`() {
        assertEquals(3, CatalogDefinition.schemas.size)
    }

    @Test
    fun `Catalog has schema default`() {
        assertNotNull(CatalogDefinition.schemaByName("HR"))
        assertEquals("H2", CatalogDefinition.vendor)
    }

    @Test
    fun `Schema has 7 tables`() {
        assertEquals(7, Core.tables.size)
    }

    @Test
    fun `Schema has reference to catalog`() {
        assertEquals(CatalogDefinition, Hr.catalog)
    }


    @Test
    fun `Table Employee has reference to Schema core`() {
        assertEquals("CORE", Employee.schemaName().value)
    }

    @Test
    fun `Alias for table is em`() {
        assertEquals("em", Employee.alias())
    }
}
