package com.dbobjekts.codegen.parsers

import com.dbobjekts.Tuple8

data class TableMetaDataRow(
    val schema: String,
    val table: String,
    val autoIncrement: Boolean,
    val column: String,
    val isPrimaryKey: Boolean,
    val nullable: Boolean,
    val defaultValue: String?,
    val dataType: String
)
