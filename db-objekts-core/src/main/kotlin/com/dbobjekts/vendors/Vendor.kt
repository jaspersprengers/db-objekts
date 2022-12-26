package com.dbobjekts.vendors

import com.dbobjekts.codegen.datatypemapper.ColumnTypeMapper
import com.dbobjekts.codegen.parsers.VendorSpecificMetaDataExtractor

/**
 * Enumeration of all supported database vendors. Instances of Vendors implement the [Vendor] interface
 * Specifies vendor-specific classes for sql-specific properties and the default [ColumnTypeMapper]
 */
enum class Vendors(val vendorClass: String) {
    H2("com.dbobjekts.vendors.h2.H2Vendor"),
    MARIADB("com.dbobjekts.vendors.mariadb.MariaDBVendor");

    companion object {

        /**
         * Finds a [Vendors] instance by matching on its name, case-insensitive
         */
        fun byName(name: String): Vendor {
            val vendor = Vendors.values().find {
                it.name.equals(name, true)
            } ?: throw IllegalArgumentException("Vendor $name is not supported, Choose from ${Vendors.values().map { it.name }.joinToString(",")}")
            val kClass = Class.forName(vendor.vendorClass).kotlin
            return (kClass.objectInstance ?: kClass.java.newInstance()) as Vendor
        }

        fun byProductAndVersion(name: String, version: String?) = byName(name)
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
