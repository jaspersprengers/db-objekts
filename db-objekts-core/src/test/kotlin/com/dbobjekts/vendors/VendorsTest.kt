package com.dbobjekts.vendors

import com.dbobjekts.vendors.h2.H2Vendor
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class VendorsTest {

    @Test
    fun `get vendor by name and version`() {
        Assertions.assertThat(Vendors.byProductAndVersion("H2")).isEqualTo(H2Vendor)
        Assertions.assertThat(Vendors.byProductAndVersion("H2", 2)).isEqualTo(H2Vendor)
        Assertions.assertThat(Vendors.byProductAndVersion("H2", 1)).isEqualTo(H2Vendor)
        Assertions.assertThatThrownBy { Vendors.byProductAndVersion("H2", 3) }
    }

}
