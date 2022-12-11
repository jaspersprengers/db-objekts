package com.dbobjekts.metadata

import com.dbobjekts.AnyColumn

object DefaultTable : Table("default") {
    override val columns = listOf<AnyColumn>()
}
