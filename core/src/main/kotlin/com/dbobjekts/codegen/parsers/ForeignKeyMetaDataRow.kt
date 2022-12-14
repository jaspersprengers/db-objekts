package com.dbobjekts.codegen.parsers

data class ForeignKeyMetaDataRow(
    val schema: String,
    val table: String,
    val column: String,
    val refSchema: String,
    val refTable: String,
    val refColumn: String
)
