package com.dbobjekts.metadata

import com.dbobjekts.example.Catalogdefinition
import com.dbobjekts.example.core.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test


class SchemaTest {


    @Test
    fun `Catalog has two schemas`() {
        assertEquals(Catalogdefinition.schemas.size, 5)
    }

    @Test
    fun `Catalog has schema default`() {
        assertNotNull(Catalogdefinition.schemaByName("core"))
        assertEquals("h2", Catalogdefinition.vendor)
    }

    @Test
    fun `Schema has one table`() {
        assertEquals(7, Core.tables.size)
    }

    @Test
    fun `Schema has reference to catalog`() {
        assertEquals(Catalogdefinition, Core.catalog)
    }


    @Test
    fun `Table Employee has reference to Schema core`() {
        assertEquals("core", Employee.schemaName().value)
    }

    @Test
    fun `Alias for table is e`() {
        assertEquals("e", Employee.alias())
        assertEquals("a", Address.alias())
        assertEquals("c", Country.alias())
        assertEquals("d", Department.alias())
        assertEquals("ea", EmployeeAddress.alias())
        assertEquals("ed", EmployeeDepartment.alias())
    }
}
