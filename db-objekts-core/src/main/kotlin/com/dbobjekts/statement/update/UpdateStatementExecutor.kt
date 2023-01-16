package com.dbobjekts.statement.update

import com.dbobjekts.api.AnyColumnAndValue
import com.dbobjekts.api.AnySqlParameter
import com.dbobjekts.api.AnyTable
import com.dbobjekts.api.exception.StatementBuilderException
import com.dbobjekts.statement.Semaphore
import com.dbobjekts.jdbc.ConnectionAdapter
import com.dbobjekts.statement.ColumnsForUpdate
import com.dbobjekts.statement.SQLOptions
import com.dbobjekts.statement.StatementBase
import com.dbobjekts.statement.whereclause.SubClause
import com.dbobjekts.util.Errors
import com.dbobjekts.util.StringUtil
import org.slf4j.LoggerFactory


class UpdateStatementExecutor(
    semaphore: Semaphore,
    connection: ConnectionAdapter,
    table: AnyTable,
    values: List<AnyColumnAndValue>
) : StatementBase<Long>(semaphore, connection) {

    override val statementType = "update"

    init {
        registerTable(table)
        if (values.isEmpty())
            Errors("List of columns to update is empty. Provide at least one.")
        if (values.map { it.column.table }.toSet().size > 1)
            Errors("Cannot update columns in more than one table.")

    }

    private val log = LoggerFactory.getLogger(UpdateStatementExecutor::class.java)
    private val columnsForUpdate = ColumnsForUpdate.fromValues(values)

    /**
     * Starts the whereclause for this update statement. Example:
     * ```kotlin
     *    transaction.update(Author)
     *           .name("Eric Blair")
     *           .where(Author.id.eq(orwell))
     * ```
     */
    fun where(subclause: SubClause): Long {
        try {
            withWhereClause(subclause)
            val sql = toSQL()
            val allParams: List<AnySqlParameter> = getAllParameters()
            return if (allParams.isEmpty()) {
                log.warn("No parameters defined for statement $sql. Skipping execute.")
                0
            } else {
                connection.prepareAndExecuteUpdate(sql, allParams)
            }
        } finally {
            semaphore.clear()
        }
    }

    /**
     * Serializes the update statement to SQL. Intended for internal use only.
     */
    internal fun toSQL(): String {
        getWhereClause().getFlattenedConditions().forEach { registerTable(it.column.table) }
        val supportsJoins = connection.vendorSpecificProperties.supportsJoinsInUpdateAndDelete()
        val joinChain = joinChainSQL(false)
        if (joinChain.hasJoins() && !supportsJoins) {
            throw StatementBuilderException("Update statements with join syntax are not supported by your database.")
        }
        val useTableAlias = joinChain.hasJoins()
        val columns =
            columnsForUpdate.params.map { (if (useTableAlias) it.column.aliasDotName() else it.column.nameInTable) + " = ?" }.joinToString(",")
        val clause = getWhereClause().build(SQLOptions(useTableAlias))
        return StringUtil.concat(listOf("update", joinChain.toSQL(SQLOptions(useTableAlias)), "set", columns, clause))
    }

    internal fun getAllParameters(): List<AnySqlParameter> {
        //parameters in the SET portion of the statement (a=3, b=5) are appended with those in the whereclause
        val nmbUpdateParameters = columnsForUpdate.numberOfParameters()
        val whereClauseParameters = getWhereClause().getParameters()
            .mapIndexed { index, sqlParameter -> sqlParameter.copy(oneBasedPosition = 1 + index + nmbUpdateParameters) }
        return StringUtil.concatLists(columnsForUpdate.params.toList(), whereClauseParameters)
    }


}
