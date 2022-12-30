package com.dbobjekts.mariadb

import com.dbobjekts.vendors.Vendors
import com.dbobjekts.vendors.mariadb.MariaDBVendor
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test


class VersionTest {

    @Test
    fun `get vendor by name and version`() {
        Assertions.assertThat(Vendors.byProductAndVersion("MARIADB")).isEqualTo(MariaDBVendor)
        Assertions.assertThat(Vendors.byProductAndVersion("MARIADB", 10)).isEqualTo(MariaDBVendor)
        Assertions.assertThat(Vendors.byProductAndVersion("MARIADB", 9)).isEqualTo(MariaDBVendor)
        Assertions.assertThatThrownBy { Vendors.byProductAndVersion("MARIADB", 11) }
    }

}
