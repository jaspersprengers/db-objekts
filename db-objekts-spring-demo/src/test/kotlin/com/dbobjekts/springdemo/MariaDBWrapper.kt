package com.dbobjekts.springdemo

import org.testcontainers.containers.BindMode
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.utility.DockerImageName

class MariaDBWrapper(
    version: String,
    files: List<String>,
) : MySQLContainer<MariaDBWrapper>(DockerImageName.parse("mariadb:$version")) {
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
}
