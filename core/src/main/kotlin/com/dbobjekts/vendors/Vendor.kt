package com.dbobjekts.vendors

import com.dbobjekts.codegen.datatypemapper.ColumnTypeMapper
import com.dbobjekts.codegen.datatypemapper.ColumnTypeResolver
import com.dbobjekts.vendors.h2.H2DataTypeMapper
import com.dbobjekts.vendors.h2.H2Properties
import com.dbobjekts.vendors.mysql.MariaDBDataTypeMapper
import com.dbobjekts.vendors.mysql.MariaDBProperties

enum class Vendors(val vendor: Vendor) {
    H2_TYPE(H2), MARIADB_TYPE(MariaDB);

    companion object {
        fun byName(name: String): Vendor = values().filter { name.equals(it.vendor.vendorName, true) }
            .firstOrNull()?.vendor
            ?: throw java.lang.IllegalArgumentException("Unsupported vendor $name")

        fun byProductAndVersion(name: String, version: String?) = byName(name)
    }
}

abstract class Vendor(val vendorName: String) {

    open val schemasToSkip: Set<String> = setOf()

    override fun toString(): String = vendorName

    abstract val asClassName: String

    abstract fun createSQLDataTypeMapper(): ColumnTypeMapper

    abstract val properties: VendorSpecificProperties

    fun defaultMapper(): ColumnTypeResolver = ColumnTypeResolver(createSQLDataTypeMapper())

}


object H2 : Vendor("h2") {

    override val schemasToSkip = setOf("INFORMATION_SCHEMA")

    override val asClassName = "H2"

    override val properties = H2Properties()

    override fun createSQLDataTypeMapper(): ColumnTypeMapper = H2DataTypeMapper()

}

object MariaDB : Vendor("mariadb") {

    override val schemasToSkip = setOf("mysql","information_schema")

    override val asClassName = "MariaDB"

    override val properties  = MariaDBProperties()

    override fun createSQLDataTypeMapper(): ColumnTypeMapper = MariaDBDataTypeMapper()

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
