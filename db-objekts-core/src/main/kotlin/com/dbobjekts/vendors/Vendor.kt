package com.dbobjekts.vendors

import com.dbobjekts.codegen.datatypemapper.ColumnTypeMapper
import com.dbobjekts.codegen.parsers.VendorSpecificMetaDataExtractor

/**
 * Enumeration of all supported database vendors/version combinations.
 *
 * If there are breaking incompatibilities between major versions, the convention is to indicate this in the name
 * E.g. ACME_5, ACME_10.
 *
 * For most vendors, such a distinction is not relevant at present.
 */
enum class Vendors(val vendorClass: String) {
    H2("com.dbobjekts.vendors.h2.H2Vendor"),
    MARIADB("com.dbobjekts.vendors.mariadb.MariaDBVendor");

    companion object {

        /**
         * Finds a [Vendor] instance by matching on its name, case-insensitive
         */
        fun byName(name: String): Vendor {
            val vendor = Vendors.values().find {
                it.name.equals(name, true)
            } ?: throw IllegalArgumentException(
                "Vendor $name is not supported, Choose from ${
                    Vendors.values().map { it.name }.joinToString(",")
                }"
            )
            val kClass = Class.forName(vendor.vendorClass).kotlin
            return (kClass.objectInstance ?: kClass.java.newInstance()) as Vendor
        }

        /**
         * Retrieves the appropriate vendor, if available.
         *
         * WARNING: the version attribute is ignored for now
         *
         * @param name the name as returned by [java.sql.DatabaseMetaData.getDatabaseProductVersion]
         * @version the major version as returned by [java.sql.DatabaseMetaData.getDatabaseMajorVersion]
         */
        fun byProductAndVersion(name: String, version: Int) = byName(name)
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
     * The vendor-specific implementation to map JDBC types to db-objekts [Column] implemetations
     */
    val defaultMapper: ColumnTypeMapper
    val properties: VendorSpecificProperties
    val metadataExtractor: VendorSpecificMetaDataExtractor
}
