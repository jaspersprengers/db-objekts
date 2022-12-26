package com.dbobjekts.api

import com.dbobjekts.vendors.Vendor

data class DBConnectionMetaData(val vendor: Vendor, val schemas: List<String>)
