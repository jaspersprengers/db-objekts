package com.dbobjekts.jdbc

import java.sql.Connection

import javax.sql.DataSource
import org.slf4j.LoggerFactory

open class DataSourceAdapterImpl<DS : DataSource>(override val dataSource: DS) : DataSourceAdapter {
    val logger = LoggerFactory.getLogger(DataSourceAdapterImpl::class.java)

    override fun close() {
       logger.warn("Close method is not implemented by this dataSource")
    }

    override fun createConnection(): Connection = dataSource.getConnection()

}
