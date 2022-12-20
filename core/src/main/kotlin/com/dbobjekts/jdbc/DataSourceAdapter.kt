package com.dbobjekts.jdbc

import java.sql.Connection
import javax.sql.DataSource

interface DataSourceAdapter {
    fun close()
    fun createConnection(): Connection
    val dataSource: DataSource
}
