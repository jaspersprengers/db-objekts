package com.dbobjekts.api

import com.dbobjekts.metadata.column.Column
import com.dbobjekts.metadata.column.ColumnAndValue
import com.dbobjekts.metadata.column.IsForeignKey
import com.dbobjekts.statement.Condition
import com.dbobjekts.statement.SqlParameter

typealias AnySqlParameter = SqlParameter<*>
typealias AnyColumn = Column<*>
typealias AnyForeignKey = IsForeignKey<*, *>
typealias AnyColumnAndValue = ColumnAndValue<*>
typealias AnyCondition = Condition<*, *>
