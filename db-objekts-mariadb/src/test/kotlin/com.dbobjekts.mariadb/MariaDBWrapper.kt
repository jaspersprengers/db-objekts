package com.dbobjekts.integration.mariadb

import com.dbobjekts.util.HikariDataSourceFactory
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.utility.DockerImageName
import java.lang.IllegalArgumentException
import javax.sql.DataSource


class MariaDBWrapper(
    version: String,
    files: List<String>,
) : MariaDBContainer<MariaDBWrapper>(DockerImageName.parse("mariadb:$version")) {
    init {
        withDatabaseName("test")
        withEnv("MARIADB_ROOT_PASSWORD", "test")
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
                url = "jdbc:mariadb://localhost:${firstMappedPort}/test",
                username = "root",
                password = "test",
                driver = "org.mariadb.jdbc.Driver"
            )
    }
}
