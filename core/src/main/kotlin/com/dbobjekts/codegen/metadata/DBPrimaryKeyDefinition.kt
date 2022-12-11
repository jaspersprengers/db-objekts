package com.dbobjekts.codegen.metadata

import com.dbobjekts.ColumnName
import com.dbobjekts.TableName

interface DBPrimaryKeyDefinition {
    val tableName: TableName
    val columnName: ColumnName
}
