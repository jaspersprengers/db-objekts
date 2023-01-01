package com.dbobjekts.util

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class VersionTest {

    @Test
    fun `test current major and minor version`(){
        Assertions.assertThat(Version.MAJOR).isEqualTo(0)
        Assertions.assertThat(Version.MINOR).isEqualTo(3)
    }

}
