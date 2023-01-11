package com.dbobjekts.mysql

import org.testcontainers.containers.BindMode
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName


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

}
