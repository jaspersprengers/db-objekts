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
        fun byName(name: String): Vendors = values().filter { name.equals(it.name, true) }
            .firstOrNull()
            ?: throw java.lang.IllegalArgumentException("Unsupported vendor $name")

        fun byProductAndVersion(name: String, version: String?) = byName(name)
    }

}

interface Vendor {
    val name: String
    val defaultMapper: ColumnTypeMapper
    val properties: VendorSpecificProperties
}
