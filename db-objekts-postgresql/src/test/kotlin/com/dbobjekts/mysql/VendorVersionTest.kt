package com.dbobjekts.mysql

import com.dbobjekts.vendors.Vendors
import com.dbobjekts.vendors.mysql.PostgreSQLVendor
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test


class VendorVersionTest {

    @Test
    fun `get vendor by name and version`() {
        Assertions.assertThat(Vendors.byProductAndVersion("POSTGRESQL")).isEqualTo(PostgreSQLVendor)
        Assertions.assertThat(Vendors.byProductAndVersion("POSTGRESQL", 10)).isEqualTo(PostgreSQLVendor)
        Assertions.assertThat(Vendors.byProductAndVersion("POSTGRESQL", 9)).isEqualTo(PostgreSQLVendor)
        Assertions.assertThatThrownBy { Vendors.byProductAndVersion("POSTGRESQL", 11) }
    }

}
