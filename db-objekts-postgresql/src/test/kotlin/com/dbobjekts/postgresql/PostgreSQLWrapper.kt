package com.dbobjekts.postgresql

import com.dbobjekts.util.HikariDataSourceFactory
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import javax.sql.DataSource


class PostgreSQLWrapper(
    version: String,
    files: List<String>,
) : PostgreSQLContainer<PostgreSQLWrapper>(DockerImageName.parse("postgres:$version")) {
    init {
        withDatabaseName("test")
        withEnv("POSTGRES_PASSWORD", "test")
        withEnv("POSTGRES_USER", "test")
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
                url = "jdbc:postgresql://localhost:${firstMappedPort}/test",
                username = "test",
                password = "test",
                driver = "org.postgresql.Driver"
            )
    }

}
