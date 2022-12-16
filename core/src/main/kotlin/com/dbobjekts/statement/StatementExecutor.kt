package com.dbobjekts.statement

import com.dbobjekts.jdbc.ConnectionAdapterImpl

/**
  * Shared by all Statement implementations and ClauseElement
  */
 internal interface StatementExecutor {
    val connection: ConnectionAdapterImpl
}
