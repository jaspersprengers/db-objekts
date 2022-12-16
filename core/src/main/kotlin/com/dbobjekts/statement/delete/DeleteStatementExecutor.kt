package com.dbobjekts.statement.delete

import com.dbobjekts.SQL
import com.dbobjekts.jdbc.ConnectionAdapterImpl
import com.dbobjekts.metadata.Table
import com.dbobjekts.metadata.TableJoinChain
import com.dbobjekts.statement.SQLOptions
import com.dbobjekts.statement.StatementBase
import com.dbobjekts.statement.whereclause.EmptyWhereClause
import com.dbobjekts.statement.whereclause.SubClause
import com.dbobjekts.util.StringUtil

class DeleteStatementExecutor(connection: ConnectionAdapterImpl) :StatementBase<Long>(connection) {

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

    fun noWhereClause(): Long = where(EmptyWhereClause)

    private fun execute(): Long {
        val wc = getWhereClause()
        wc.getFlattenedConditions().forEach {registerTable(it.column.table)}
        val alias = if (connection.vendorSpecificProperties.supportsJoinsInUpdateAndDelete()) "${joinChain().table.alias()}.*" else ""
        val sql = SQL(StringUtil.concat(listOf("delete", alias, "from", joinChain().toSQL(), wc.build(SQLOptions(includeAlias = true)))))
        val params = wc.getParameters()
        return connection.prepareAndExecuteDeleteStatement(sql, params)
    }

}
