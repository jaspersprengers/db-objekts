package com.dbobjekts.api

import java.nio.file.Paths


object PathsUtil {
    /**
     * Resolves to the absolute path of the `src/generated-sources/kotlin` directory in the current project
     */
    fun getGeneratedSourcesDirectory(): String =
        Paths.get("src/generated-sources/kotlin").toAbsolutePath().toString()

}
