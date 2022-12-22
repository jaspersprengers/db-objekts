package com.dbobjekts.util

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

object HikariDataSourceFactory {

    fun create(
        url: String,
        username: String,
        password: String?,
        driver: String? = null,
        maxPoolSize: Int = 50,
        connectionTimeoutMillis: Long = 10000
    ): HikariDataSource {
        val conf = HikariConfig()
        conf.setJdbcUrl(url)
        conf.setUsername(username)
        conf.setPassword(password)
        driver?.let { conf.setDriverClassName(it) }
        conf.setMaximumPoolSize(maxPoolSize)
        conf.setConnectionTimeout(connectionTimeoutMillis)
        return HikariDataSource(conf)
    }

}
