package com.dbobjekts.statement.delete

import com.dbobjekts.api.Semaphore
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.joins.TableJoinChain
import com.dbobjekts.statement.SQLOptions
import com.dbobjekts.statement.StatementBase
import com.dbobjekts.statement.whereclause.EmptyWhereClause
import com.dbobjekts.statement.whereclause.SubClause
import com.dbobjekts.util.StringUtil

class DeleteStatementExecutor(
    semaphore: Semaphore,
    connection: ConnectionAdapter
) : StatementBase<Long>(semaphore, connection) {

    override val statementType = "delete"

    init {
        semaphore.claim("delete")
    }

    internal fun withTable(table: Table): DeleteStatementExecutor {
        registerTable(table)
        return this
    }

    internal fun withJoinChain(tableJoinChain: TableJoinChain): DeleteStatementExecutor {
        registerJoinChain(tableJoinChain)
        return this
    }

    fun where(condition: SubClause): Long {
        withWhereClause(condition)
        return execute()
    }

    fun where(): Long = where(EmptyWhereClause)

    private fun execute(): Long {
        val wc = getWhereClause()
        wc.getFlattenedConditions().forEach { registerTable(it.column.table) }
        val alias = if (connection.vendorSpecificProperties.supportsJoinsInUpdateAndDelete()) "${buildJoinChain().table.alias()}.*" else ""
        val sql = StringUtil.concat(listOf("delete", alias, "from", buildJoinChain().toSQL(), wc.build(SQLOptions(includeAlias = true))))
        val params = wc.getParameters()
        return connection.prepareAndExecuteDeleteStatement(sql, params).also {
            connection.statementLog.logResult(it)
            semaphore.clear()
        }
    }

}
