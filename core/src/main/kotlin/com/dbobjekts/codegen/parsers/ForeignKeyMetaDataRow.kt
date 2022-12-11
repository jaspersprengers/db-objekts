package com.dbobjekts.codegen.parsers

import com.dbobjekts.Tuple6

data class ForeignKeyMetaDataRow(
    val schema: String,
    val table: String,
    val refTable: String,
    val refSchema: String,
    val column: String,
    val refColumn: String
) {
    companion object {
        fun parse(tuple: Tuple6<String, String, String, String, String, String>): ForeignKeyMetaDataRow {
            return ForeignKeyMetaDataRow(tuple.v1, tuple.v2, tuple.v3, tuple.v4, tuple.v5, tuple.v6)
        }
    }
}
