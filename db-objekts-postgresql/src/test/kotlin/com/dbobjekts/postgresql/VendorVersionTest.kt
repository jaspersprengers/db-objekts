package com.dbobjekts.postgresql

import com.dbobjekts.vendors.Vendors
import com.dbobjekts.vendors.postgresql.PostgreSQLVendor
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test


class VendorVersionTest {

    @Test
    fun `get vendor by name and version`() {
        Assertions.assertThat(Vendors.byProductAndVersion("POSTGRESQL")).isEqualTo(PostgreSQLVendor)
        Assertions.assertThat(Vendors.byProductAndVersion("POSTGRESQL", 15)).isEqualTo(PostgreSQLVendor)
        Assertions.assertThat(Vendors.byProductAndVersion("POSTGRESQL", 14)).isEqualTo(PostgreSQLVendor)
        Assertions.assertThatThrownBy { Vendors.byProductAndVersion("POSTGRESQL", 16) }
    }

}
