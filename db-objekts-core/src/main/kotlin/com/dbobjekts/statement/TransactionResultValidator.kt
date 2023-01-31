package com.dbobjekts.statement

import com.dbobjekts.api.ResultRow
import com.dbobjekts.api.ResultSetIterator
import com.dbobjekts.api.exception.StatementExecutionException
import com.dbobjekts.statement.customsql.SQLStatementExecutor
import com.dbobjekts.statement.insert.InsertBuilderBase
import com.dbobjekts.statement.update.UpdateBuilderBase

object TransactionResultValidator {
  fun <T> validate(result: T): T {
    fun exception(tp: String = "") = StatementExecutionException(
      """You are not allowed to return an unfinished statement from a transactional block, because the underlying Connection handle has been closed.
         $tp. """)
    return when(result) {
      is StatementBase<*> -> throw exception("Fetch the results from this select statement within the transaction block")
      is SQLStatementExecutor<*,*> -> throw exception("Fetch or execute the SQL statement within the transaction block")
      is UpdateBuilderBase -> throw exception("The Update statement must be completed with the transaction block")
      is InsertBuilderBase -> throw exception("The Insert statement must be closed with execute()")
      is ResultRow<*> -> throw exception("Fetch the results from this select statement within the transaction block")
      is Iterator<*>  -> throw exception("An Iterator over a ResultSet must be consumed within the transaction block.")
      else -> result
    }

  }
}
