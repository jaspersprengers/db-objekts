package com.dbobjekts.api

import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.column.Column
import com.dbobjekts.metadata.column.ColumnAndValue
import com.dbobjekts.statement.Condition
import com.dbobjekts.statement.SqlParameter

typealias AnyTable = Table<*>
typealias AnySqlParameter = SqlParameter<*>
typealias AnyColumn = Column<*>
typealias AnyColumnAndValue = ColumnAndValue<*>
typealias AnyCondition = Condition<*, *>
