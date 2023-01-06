package com.dbobjekts.mysql

import com.dbobjekts.vendors.Vendors
import com.dbobjekts.vendors.mysql.MysqlVendor
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test


class VendorVersionTest {

    @Test
    fun `get vendor by name and version`() {
        Assertions.assertThat(Vendors.byProductAndVersion("MYSQL")).isEqualTo(MysqlVendor)
        Assertions.assertThat(Vendors.byProductAndVersion("MYSQL", 10)).isEqualTo(MysqlVendor)
        Assertions.assertThat(Vendors.byProductAndVersion("MYSQL", 9)).isEqualTo(MysqlVendor)
        Assertions.assertThatThrownBy { Vendors.byProductAndVersion("MYSQL", 11) }
    }

}
