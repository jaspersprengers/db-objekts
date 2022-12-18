package com.dbobjekts.api

import com.dbobjekts.util.StringUtil
import java.io.File
import java.util.regex.Pattern

interface DBObjectName {
    fun capitalCamelCase(): String
    fun lowerCamelCase(): String
}

class DBObjectNameBase(val value: String) : DBObjectName {
    override fun capitalCamelCase(): String = StringUtil.capitalCamel(value)
    override fun lowerCamelCase(): String = StringUtil.lowerCamel(value)
}

data class SchemaName(val value: String) : DBObjectName by DBObjectNameBase(value) {
    init {
        require(value.isNotBlank(), { "Schema name cannot be blank" })
    }

    fun asPackage(): String = value.lowercase()

    override fun toString(): String = value

}

data class TableName(val value: String) : DBObjectName by DBObjectNameBase(value) {
    init {
        require(value.isNotBlank(), { "Table name cannot be blank" })
    }

    override fun toString(): String = value
}

data class ColumnName(val value: String) : DBObjectName by DBObjectNameBase(value) {
    init {
        require(value.isNotBlank(), { "Column name cannot be blank" })
    }

    override fun toString(): String = value
}

class PackageName(val parts: List<String>) {
    constructor(value: String) : this(value.split("."))

    init {
        parts.forEach { part -> require(regex.matcher(part).matches(), { "$part is not a valid package element." }) }
    }

    fun isBlank(): Boolean = parts.isEmpty()

    fun createSubPackageForSchema(sub: SchemaName): PackageName = concat(sub.value.lowercase())

    fun concat(part: String): PackageName = PackageName(StringUtil.concatToList(parts, part))

    fun asFilePath(): String = parts.joinToString(File.separator)

    override fun toString(): String = parts.joinToString(".")

    companion object {
        fun fromClass(clz: Class<*>) = PackageName(clz.`package`.name)
        val regex = Pattern.compile("[a-zA-Z0-9]+")
    }
}
