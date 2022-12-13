package com.dbobjekts.jdbc

import com.dbobjekts.metadata.Catalog
import com.dbobjekts.vendors.Vendor
import com.dbobjekts.vendors.Vendors
import java.sql.Connection


class DetermineVendor {

    operator fun invoke(conn: Connection, catalogOpt: Catalog?): Vendor {
        val metaData = conn.getMetaData()
        val vendor =
            Vendors.byProductAndVersion(
                metaData.getDatabaseProductName(),
                metaData.getDatabaseProductVersion()
            )
        return if (catalogOpt == null)
            vendor
        else {
            if (catalogOpt.vendor != vendor.name)
                throw IllegalArgumentException("Mismatch between the vendor type of the connected database (${vendor.name}), and the one specified in the Catalog definition: ${catalogOpt.vendor}.")
            Vendors.byName(catalogOpt.vendor)
        }
    }

}
