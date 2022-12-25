package com.dbobjekts.integration.mariadb

import org.testcontainers.containers.BindMode
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.utility.DockerImageName


class MariaDBWrapper(val createScript: String) : MariaDBContainer<MariaDBWrapper>(DockerImageName.parse("mariadb:10.6")) {
    init {
        withDatabaseName("test")
        //withEnv("MARIADB_USER", "test")
        withEnv("MARIADB_ROOT_PASSWORD", "test")
        withClasspathResourceMapping(
            createScript,
            "/docker-entrypoint-initdb.d/",
            BindMode.READ_ONLY
        )

    }
}
