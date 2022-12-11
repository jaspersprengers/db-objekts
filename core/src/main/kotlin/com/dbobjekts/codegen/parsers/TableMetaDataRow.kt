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
) {
    companion object {
        fun parse(tuple: Tuple8<String, String, String?, String, String?, String, String?, String>): TableMetaDataRow =
            TableMetaDataRow(
                schema = tuple.v1,
                table = tuple.v2,
                autoIncrement = (tuple.v3 ?: "") == "auto_increment",
                column = tuple.v4,
                isPrimaryKey = tuple.v5 == "PRI",
                nullable = tuple.v6 == "YES",
                defaultValue = tuple.v7,
                dataType = tuple.v8
            )
    }
}
