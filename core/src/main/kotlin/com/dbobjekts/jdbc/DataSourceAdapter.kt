package com.dbobjekts.jdbc

import java.sql.Connection

interface DataSourceAdapter {
    fun close()
    fun createConnection(): Connection
}
