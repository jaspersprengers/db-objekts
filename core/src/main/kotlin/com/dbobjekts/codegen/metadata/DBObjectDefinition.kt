package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.PackageName
import com.dbobjekts.util.StringUtil

interface DBObjectDefinition {

    val packageName: PackageName

    fun asClassName(): String = StringUtil.capitalCamel(toString())

    fun asFieldName(): String = ReservedKeywords.prependIfReserved(StringUtil.lowerCamel(toString().lowercase()))

    fun fullyQualifiedClassName(): String = packageName.concat(asClassName()).toString()
}
