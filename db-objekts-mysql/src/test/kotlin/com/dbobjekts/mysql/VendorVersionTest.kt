package com.dbobjekts.mysql

import com.dbobjekts.vendors.Vendors
import com.dbobjekts.vendors.mysql.MysqlVendor
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class VendorVersionTest {

    @Test
    fun `get vendor by name and version`() {
        assertThat(Vendors.byProductAndVersion("MYSQL")).isEqualTo(MysqlVendor)
        assertThat(Vendors.byProductAndVersion("MYSQL", 8)).isEqualTo(MysqlVendor)
        Assertions.assertThatThrownBy { Vendors.byProductAndVersion("MYSQL", 9) }
    }

}
