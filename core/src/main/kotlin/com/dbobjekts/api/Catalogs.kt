package com.dbobjekts.api

import com.dbobjekts.metadata.Catalog
import com.dbobjekts.vendors.Vendors


object Catalogs {
    val EMPTY_MARIADB_CATALOG = Catalog(Vendors.MARIADB.name)
    val EMPTY_H2_CATALOG = Catalog(Vendors.H2.name)
}
