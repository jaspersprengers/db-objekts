package com.dbobjekts.util

import com.dbobjekts.jdbc.DataSourceAdapterImpl
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection

class HikariDataSourceAdapterImpl(private val ds: HikariDataSource): DataSourceAdapterImpl<HikariDataSource>(ds) {

    override fun createConnection(): Connection  {
        if ( !dataSource.isClosed())
          return dataSource.getConnection()
        else throw IllegalStateException("Datasource is already closed.")
    }

    override fun close() {
       ds.close()
    }
}
