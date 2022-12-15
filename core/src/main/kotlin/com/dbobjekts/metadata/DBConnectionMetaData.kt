package com.dbobjekts.metadata

import com.dbobjekts.vendors.Vendor

data class DBConnectionMetaData(val vendor: Vendor, val catalogs: List<String>)
