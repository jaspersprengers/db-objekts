package com.dbobjekts.metadata

import com.dbobjekts.api.AnyColumn

object DefaultTable : Table("default") {
    override val columns = listOf<AnyColumn>()
}
