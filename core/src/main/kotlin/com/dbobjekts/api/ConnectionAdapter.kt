package com.dbobjekts.api

import com.dbobjekts.metadata.Catalog
import com.dbobjekts.vendors.Vendor
import java.sql.Connection


interface ConnectionAdapter {

    fun catalog(): Catalog

    val jdbcConnection: Connection

    val vendor: Vendor
}
