package com.dbobjekts.api

import com.dbobjekts.util.StringUtil
import com.dbobjekts.util.ObjectNameValidator
import java.io.File
import java.util.regex.Pattern

internal interface DBObjectName {
    fun capitalCamelCase(): String
    fun lowerCamelCase(): String
}

class DBObjectNameBase(val value: String) : DBObjectName {
    override fun capitalCamelCase(): String =
        if (StringUtil.isCamelCase(value)) StringUtil.initUpperCase(value) else StringUtil.capitalCamel(value)

    override fun lowerCamelCase(): String =
        if (StringUtil.isCamelCase(value)) StringUtil.initLowerCase(value) else StringUtil.lowerCamel(value)
}

data class SchemaName(val value: String) : DBObjectName by DBObjectNameBase(value) {
    init {
        require(value.isNotBlank(), { "Schema name cannot be blank" })
        ObjectNameValidator.validate(capitalCamelCase(), "$value for schema $value is not a valid identifier.")
    }

    fun asPackage(): String = value.lowercase()

    override fun toString(): String = value

}

data class TableName(
    val value: String,
    private val custom: String? = null
) : DBObjectName by DBObjectNameBase(value) {
    val metaDataObjectName: String

    init {
        require(value.isNotBlank(), { "Table name cannot be blank" })
        metaDataObjectName = custom?.let {
            require(it.isNotBlank(), { "Custom table name cannot be blank" })
            StringUtil.initUpperCase(it)
        } ?: capitalCamelCase()
        ObjectNameValidator.validate(metaDataObjectName, "$metaDataObjectName for table $value is not a valid identifier.")
    }

    override fun toString(): String = value
}

data class ColumnName(
    val value: String,
    private val custom: String? = null
) : DBObjectName by DBObjectNameBase(value) {
    val fieldName: String

    init {
        require(value.isNotBlank(), { "Column name cannot be blank" })
        fieldName = custom?.let {
            require(it.isNotBlank(), { "Custom column name cannot be blank" })
            StringUtil.initLowerCase(it)
        } ?: lowerCamelCase()
        ObjectNameValidator.validate(fieldName, "$fieldName for column $value is not a valid column identifier.")
    }

    override fun toString(): String = value
}

class PackageName(val parts: List<String>) {
    constructor(value: String) : this(value.split("."))

    init {
        parts.forEach { part -> require(regex.matcher(part).matches(), { "$part is not a valid package element." }) }
    }

    fun createSubPackageForSchema(sub: SchemaName): PackageName = concat(sub.value.lowercase())

    fun concat(part: String): PackageName = PackageName(parts + part)

    fun asFilePath(): String = parts.joinToString(File.separator)

    override fun toString(): String = parts.joinToString(".")

    companion object {
        fun fromClass(clz: Class<*>) = PackageName(clz.`package`.name)
        val regex = Pattern.compile("[a-zA-Z0-9]+")
    }
}
