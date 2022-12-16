package com.dbobjekts.statement.update

import com.dbobjekts.AnyColumnAndValue
import com.dbobjekts.AnySqlParameter
import com.dbobjekts.SQL
import com.dbobjekts.jdbc.ConnectionAdapterImpl
import com.dbobjekts.metadata.Table
import com.dbobjekts.statement.ColumnsForUpdate
import com.dbobjekts.statement.SQLOptions
import com.dbobjekts.statement.StatementBase
import com.dbobjekts.statement.whereclause.SubClause
import com.dbobjekts.util.Errors
import com.dbobjekts.util.StringUtil


class UpdateStatementExecutor(
    connection: ConnectionAdapterImpl,
    table: Table,
    values: List<AnyColumnAndValue>
) : StatementBase<Long>(connection) {


    init {
        registerTable(table)
        if (values.isEmpty())
            Errors("List of columns to update is empty. Provide at least one.")
        if (values.map { it.column.table }.toSet().size > 1)
            Errors("Cannot update columns in more than one table.")

    }

    private val columnsForUpdate = ColumnsForUpdate.fromValues(values)

    fun where(subclause: SubClause): Long {
        withWhereClause(subclause)
        return execute()
    }

    fun execute(): Long {
        val sql = toSQL()
        val allParams: List<AnySqlParameter> = getAllParameters()
        connection.statementLogger.logStatement(sql, allParams)
        return if (allParams.isEmpty()) {
            connection.statementLogger.info("No parameters defined. Skipping execute.")
            0
        } else {
            connection.prepareAndExecuteUpdate(sql, allParams)
        }
    }

     fun toSQL(): SQL {
        getWhereClause().getFlattenedConditions().forEach { registerTable(it.column.table) }
        val columns = columnsForUpdate.params.map { it.column!!.aliasDotName() + " = ?" }.joinToString(",")
        val clause = getWhereClause().build(SQLOptions(includeAlias = true))
        return SQL(StringUtil.concat(listOf("update", joinChain().toSQL(), "set", columns, clause)))
    }

     fun getAllParameters(): List<AnySqlParameter> {
        //parameters in the SET portion of the statement (a=3, b=5) are appended with those in the whereclause
        val nmbUpdateParameters = columnsForUpdate.numberOfParameters()
        val whereClauseParameters = getWhereClause().getParameters()
            .mapIndexed { index, sqlParameter -> sqlParameter.copy(oneBasedPosition = 1 + index + nmbUpdateParameters) }
        return StringUtil.concatLists(columnsForUpdate.params.toList(), whereClauseParameters)
    }


}
