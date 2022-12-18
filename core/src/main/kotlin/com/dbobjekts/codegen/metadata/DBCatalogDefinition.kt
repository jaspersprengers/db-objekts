package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.PackageName
import com.dbobjekts.vendors.Vendor

data class DBCatalogDefinition(override val packageName: PackageName,
                               val vendor: Vendor,
                               val schemas: List<DBSchemaDefinition>,
                               val name: String): DBObjectDefinition {

    override fun toString(): String = name

    fun prettyPrint(): String =
        """${vendor} Database in package ${packageName} with ${schemas.size} schemas.
           |${schemas.map {it.prettyPrint()}.joinToString("\n")}"""
}
