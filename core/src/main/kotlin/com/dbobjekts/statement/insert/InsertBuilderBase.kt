package com.dbobjekts.statement.insert

import com.dbobjekts.api.AnyColumnAndValue
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.util.Errors

abstract class InsertBuilderBase() {
    internal lateinit var connection: ConnectionAdapter
    abstract fun data(): Set<AnyColumnAndValue>

    internal fun validate() = Errors.require(data().isNotEmpty(), "You must supply at least one column to insert")

    fun execute(): Long = InsertStatementExecutor(connection, data().toList(), connection.vendor.properties).execute().also { connection.statementLog.logResult(it) }
}
