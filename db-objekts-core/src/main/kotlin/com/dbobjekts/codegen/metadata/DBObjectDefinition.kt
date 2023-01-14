package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.PackageName
import com.dbobjekts.util.StringUtil

internal interface DBObjectDefinition {

    val packageName: PackageName

    /**
     * @return camel case with capital
     */
    fun asClassName(): String = StringUtil.capitalCamel(toString())

    /**
     * @return camel case with lower init
     */
    fun asFieldName(): String = ReservedKeywords.prependIfReserved(StringUtil.lowerCamel(toString().lowercase()))

    /**
     * @return example: com.dbobjekts.acme.AddressColumn
     */
    fun fullyQualifiedClassName(): String = packageName.concat(asClassName()).toString()
}
