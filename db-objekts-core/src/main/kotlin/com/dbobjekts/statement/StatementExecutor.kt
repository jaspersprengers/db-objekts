package com.dbobjekts.statement

import com.dbobjekts.jdbc.ConnectionAdapter

/**
  * Shared by all Statement implementations and ClauseElement
  */
 internal interface StatementExecutor {
    val connection: ConnectionAdapter
}
