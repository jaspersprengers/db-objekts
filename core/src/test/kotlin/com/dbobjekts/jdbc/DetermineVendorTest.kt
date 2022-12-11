package com.dbobjekts.jdbc

import com.dbobjekts.metadata.Catalog
import com.dbobjekts.metadata.NilSchemaCatalog
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.sql.Connection
import java.sql.DatabaseMetaData


class DetermineVendorTest {

    val determineVendor = DetermineVendor()
    val metaData = mock<DatabaseMetaData>()
    val conn = mock<Connection> {
        on { getMetaData() } doReturn metaData
    }

    @Test
    fun `valid vendor with no catalog`() {
        whenever(metaData.databaseProductName).thenReturn("H2")
        assertEquals("h2", determineVendor(conn, null).vendorName)
    }

    @Test
    fun `valid vendor with catalog`() {
        whenever(metaData.databaseProductName).thenReturn("H2")
        assertEquals("h2", determineVendor(conn, NilSchemaCatalog).vendorName)
    }

    @Test
    fun `non-supported vendor will throw`() {
        whenever(metaData.databaseProductName).thenReturn("sybase")
        Assertions.assertThatThrownBy { determineVendor(conn, null) }.hasMessage("Unsupported vendor sybase")
    }

    @Test
    fun `Mismatch between vendor type in db metadata and Catalog with throw`() {
        whenever(metaData.databaseProductName).thenReturn("h2")
        Assertions.assertThatThrownBy { determineVendor(conn, DummyCatalog) }
            .hasMessage("Mismatch between the vendor type of the connected database (h2), and the one specified in the Catalog definition: sybase.")
    }

    object DummyCatalog : Catalog("sybase", listOf())
}


