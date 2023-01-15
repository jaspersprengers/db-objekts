package com.dbobjekts.statement.delete

import com.dbobjekts.api.AnyTable
import com.dbobjekts.statement.Semaphore
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.metadata.joins.ManualJoinChain
import com.dbobjekts.metadata.joins.DerivedJoin
import com.dbobjekts.metadata.joins.JoinChain
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

    internal fun withTable(table: AnyTable): DeleteStatementExecutor {
        registerTable(table)
        return this
    }

    internal fun withJoinChain(tableJoinChain: JoinChain): DeleteStatementExecutor {
        registerJoinChain(tableJoinChain)
        return this
    }

    /**
     * Sets the whereclause on this delete statement and executes the statement. Note that when you want to delete all records in a table, you still explicitly provide an empty whereclause. Example:
     * ```kotlin
     * val rowsRemoved = transaction.deleteFrom(Employee).where(Employee.id.eq(42))
     * ```
     * @return the value returned by [java.sql.PreparedStatement.executeUpdate]
     */
    fun where(condition: SubClause): Long {
        withWhereClause(condition)
        return execute()
    }

    /**
     * Executes the delete statement with an empty whereclause, effectively deleting the entire table. Use with care!
     * ```kotlin
     * val rowsRemoved = transaction.deleteFrom(Employee).where()
     * ```
     * @return the value returned by [java.sql.PreparedStatement.executeUpdate]
     */
    fun where(): Long = where(EmptyWhereClause)

    private fun execute(): Long {
        try {
            val wc = getWhereClause()
            wc.getFlattenedConditions().forEach { registerTable(it.column.table) }
            val supportsJoins = connection.vendorSpecificProperties.supportsJoinsInUpdateAndDelete()
            val chain = joinChainSQL()
            val alias = if (supportsJoins) "${chain.table.alias()}.*" else ""
            val sql = StringUtil.concat(listOf("delete", alias, "from", chain.toSQL(), wc.build(SQLOptions(includeAlias = true))))
            val params = wc.getParameters()
            return connection.prepareAndExecuteDeleteStatement(sql, params).also {
                connection.statementLog.logResult(it)
            }
        } finally {
            semaphore.clear()
        }
    }

}
