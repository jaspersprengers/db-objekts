package com.dbobjekts.codegen.metadata

import com.dbobjekts.api.ColumnName
import com.dbobjekts.api.TableName

interface DBPrimaryKeyDefinition {
    val tableName: TableName
    val columnName: ColumnName
}
