package com.dbobjekts.integration.mariadb

import org.testcontainers.containers.BindMode
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.utility.DockerImageName
import java.lang.IllegalArgumentException


class MariaDBWrapper(vararg files: String) : MariaDBContainer<MariaDBWrapper>(DockerImageName.parse("mariadb:10.6")) {
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
