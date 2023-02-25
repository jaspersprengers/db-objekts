package com.dbobjekts.mysql

import com.dbobjekts.util.HikariDataSourceFactory
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.utility.DockerImageName
import javax.sql.DataSource


class MysqlWrapper(
    version: String,
    files: List<String>,
) : MySQLContainer<MysqlWrapper>(DockerImageName.parse("mysql:$version")) {
    init {
        withDatabaseName("test")
        withEnv("MYSQL_ROOT_PASSWORD", "test")
        withConnectTimeoutSeconds(600)
        withStartupTimeoutSeconds(600)
        if (files.isEmpty())
            throw IllegalArgumentException("Provide at least one sql file")
        files.forEach {
            withClasspathResourceMapping(
                it,
                "/docker-entrypoint-initdb.d/",
                BindMode.READ_ONLY
            )
        }

    }

    fun createDataSource(): DataSource {
        return HikariDataSourceFactory
            .create(
                url = "jdbc:mysql://localhost:${firstMappedPort}/test",
                username = "root",
                password = "test",
                driver = "com.mysql.cj.jdbc.Driver"
            )
    }
}
