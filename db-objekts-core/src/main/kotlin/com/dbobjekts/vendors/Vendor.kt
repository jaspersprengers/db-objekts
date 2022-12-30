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
    val requiredMajorVersion: Int
) {
    H2("com.dbobjekts.vendors.h2.H2Vendor", 2),
    MARIADB("com.dbobjekts.vendors.mariadb.MariaDBVendor", 10);

    private fun validateVersion(actualVersionOfDataSource: Int): Vendors {
        if (requiredMajorVersion < actualVersionOfDataSource)
            throw IllegalStateException("Vendor major version $actualVersionOfDataSource is not supported.")
        return this
    }

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
            val vendor = byName(name)
            version?.let { vendor.validateVersion(version) }
            return Class.forName(vendor.vendorClass).kotlin.objectInstance as Vendor
        }

        fun byName(name: String): Vendors =
            Vendors.values().find { it.name == name } ?: throw IllegalStateException("Unknown vendor $name")
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
