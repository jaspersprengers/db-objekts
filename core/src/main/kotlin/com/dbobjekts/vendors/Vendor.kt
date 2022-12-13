package com.dbobjekts.vendors

import com.dbobjekts.codegen.datatypemapper.ColumnTypeMapper
import com.dbobjekts.vendors.h2.H2DataTypeMapper
import com.dbobjekts.vendors.h2.H2Properties
import com.dbobjekts.vendors.mysql.MariaDBDataTypeMapper
import com.dbobjekts.vendors.mysql.MariaDBProperties

enum class Vendors(
    override val defaultMapper: ColumnTypeMapper,
    override val properties: VendorSpecificProperties
) : Vendor {
    H2(H2DataTypeMapper(), H2Properties()),
    MARIADB(MariaDBDataTypeMapper(), MariaDBProperties());

    companion object {
        fun byName(name: String): Vendor = values().filter { name.equals(it.name, true) }
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

/*
object MySQL10 : Vendor("mysql") {

    override val schemasToSkip = setOf("mysql")

    override val asClassName = "MySQL10"

    override val properties  = MySQLProperties

    override fun createSQLDataTypeMapper(): VendorDefaultSqlDataTypeMapper = MySQLDataTypeMapper

}*/
/*

object PostgreSQL : Vendor("postgresql") {

    override val asClassName = "PostgreSQL"

    override val properties = PostgreSQLProperties

    override fun createSQLDataTypeMapper(): VendorDefaultSqlDataTypeMapper = PostgreSQLDataTypeMapper

}



*/
