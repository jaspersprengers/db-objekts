package com.dbobjekts.vendors

import com.dbobjekts.codegen.datatypemapper.ColumnTypeMapper
import com.dbobjekts.metadata.Catalog
import com.dbobjekts.vendors.h2.H2DataTypeMapper
import com.dbobjekts.vendors.h2.H2Properties
import com.dbobjekts.vendors.mariadb.MariaDBDataTypeMapper
import com.dbobjekts.vendors.mariadb.MariaDBProperties
import com.dbobjekts.vendors.mysql.MySQLDataTypeMapper
import com.dbobjekts.vendors.mysql.MySQLProperties
import com.dbobjekts.vendors.postgresql.PostgreSQLDataTypeMapper
import com.dbobjekts.vendors.postgresql.PostgreSQLProperties

/**
 * Enumeration of all supported database vendors. Instances of Vendors implement the [Vendor] interface
 * Specifies vendor-specific classes for sql-specific properties and the default [ColumnTypeMapper]
 */
enum class Vendors(
    override val defaultMapper: ColumnTypeMapper,
    override val properties: VendorSpecificProperties,
    val defaultCatalog: Catalog
) : Vendor {
    H2(H2DataTypeMapper(), H2Properties(), Catalog("H2")),
    MARIADB(MariaDBDataTypeMapper(), MariaDBProperties(), Catalog("MARIADB")),
    MYSQL(MySQLDataTypeMapper(), MySQLProperties(), Catalog("MYSQL")),
    POSTGRESQL(PostgreSQLDataTypeMapper(), PostgreSQLProperties(), Catalog("POSTGRESQL"));

    companion object {
        /**
         * Finds a [Vendors] instance by matching on its name, case insensitive
         */
        fun byName(name: String): Vendors = values().filter { name.equals(it.name, true) }
            .firstOrNull()
            ?: throw java.lang.IllegalArgumentException("Unsupported vendor $name")

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
}
