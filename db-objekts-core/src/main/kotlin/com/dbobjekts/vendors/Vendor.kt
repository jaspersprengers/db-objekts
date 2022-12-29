package com.dbobjekts.vendors

import com.dbobjekts.codegen.datatypemapper.VendorDefaultColumnTypeMapper
import com.dbobjekts.codegen.parsers.VendorSpecificMetaDataExtractor

/**
 * Enumeration of all supported database vendors/version combinations.
 *
 * If there are breaking incompatibilities between major versions, the convention is to indicate this in the name
 * E.g. ACME_5, ACME_10.
 *
 * For most vendors, such a distinction is not relevant at present.
 */
enum class Vendors(
    val vendorClass: String,
    val majorVersion: Int
) {
    H2("com.dbobjekts.vendors.h2.H2Vendor", 10),
    MARIADB("com.dbobjekts.vendors.mariadb.MariaDBVendor", 10);

    companion object {

        /**
         * Retrieves the appropriate vendor, if available.
         *
         * WARNING: the version attribute is ignored for now
         *
         * @param name the name as returned by [java.sql.DatabaseMetaData.getDatabaseProductVersion]
         * @version the major version as returned by [java.sql.DatabaseMetaData.getDatabaseMajorVersion]
         */
        fun byProductAndVersion(name: String, version: Int? = null): Vendor {
            val vendor = Vendors.values().find {
                it.name.equals(name, true)
                if (version != null) it.majorVersion >= version else true
            } ?: throw IllegalArgumentException(
                "Vendor $name is not supported, Choose from ${
                    values().joinToString(",") { it.name }
                }"
            )
            return Class.forName(vendor.vendorClass).kotlin.objectInstance as Vendor
        }
    }
}

/**
 * Refers to a database vendor, e.g. H2 or MariaDb.
 */
interface Vendor {
    /**
     * Conforms to one of the enum literals in [Vendors]
     */
    val name: String

    /**
     * The vendor-specific implementation to map JDBC types to db-objekts [com.dbobjekts.metadata.column.Column] implementations.
     */
    val defaultMapper: VendorDefaultColumnTypeMapper
    val properties: VendorSpecificProperties
    val metadataExtractor: VendorSpecificMetaDataExtractor
}
