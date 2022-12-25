package com.dbobjekts.api

import com.dbobjekts.vendors.Vendor

internal data class DBConnectionMetaData(val vendor: Vendor, val catalogs: List<String>)
