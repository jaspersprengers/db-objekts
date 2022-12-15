package com.dbobjekts.codegen.parsers

data class TableMetaDataRow(
    val schema: String,
    val table: String,
    val autoIncrement: Boolean = false,
    val column: String,
    val isPrimaryKey: Boolean = false,
    val nullable: Boolean = true,
    val defaultValue: String? = null,
    val dataType: String
)
