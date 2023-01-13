package com.dbobjekts.statement.delete

import com.dbobjekts.api.AnyTable
import com.dbobjekts.statement.Semaphore
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.jdbc.ConnectionAdapter
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

    internal fun withTable(table: AnyTable): DeleteStatementExecutor {
        registerTable(table)
        return this
    }

    internal fun withJoinChain(tableJoinChain: TableJoinChain<*>): DeleteStatementExecutor {
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
            val joinChain = buildJoinChain()
            val supportsJoins = connection.vendorSpecificProperties.supportsJoinsInUpdateAndDelete()
            if (joinChain.hasJoins() && !supportsJoins) {
                throw StatementBuilderException("Your database does not support DELETE statements with JOIN syntax.")
            }
            val alias = if (supportsJoins) "${joinChain.table.alias()}.*" else ""
            val sql = StringUtil.concat(listOf("delete", alias, "from", joinChain.toSQL(), wc.build(SQLOptions(includeAlias = true))))
            val params = wc.getParameters()
            return connection.prepareAndExecuteDeleteStatement(sql, params).also {
                connection.statementLog.logResult(it)
            }
        } finally {
            semaphore.clear()
        }
    }

}
