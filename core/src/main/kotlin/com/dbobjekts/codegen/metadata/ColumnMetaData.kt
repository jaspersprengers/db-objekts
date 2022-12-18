package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.ColumnName

data class ColumnMetaData(
    val columnName: ColumnName = ColumnName(""),
    val columnType: String,
    val isAutoIncrement: Boolean = false,
    val isPrimaryKey: Boolean = false,
    val remarks: String? = null,
    val nullable: Boolean = false
) {

    override fun toString(): String {
        val isNull = if(nullable) "NULL" else "NOT NULL"
        val isPK = if(isPrimaryKey) " PRIMARY KEY" else ""
        val increment = if (isAutoIncrement) " AUTO_INCREMENT" else ""
        return "$columnName $columnType $isNull$isPK$increment"
    }
}

