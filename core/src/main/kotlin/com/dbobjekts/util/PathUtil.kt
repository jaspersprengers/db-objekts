package com.dbobjekts.util

import java.nio.file.Paths


object PathUtil {

    fun getFileInResourcesDir(fileName: String): String {
        val path = Paths.get("src", "test", "resources", fileName)
        return path.toAbsolutePath().toString()
    }

    fun getResourcesDir(): String =
    Paths.get("src", "test", "resources").toAbsolutePath().toString()

    fun getMainSourceDir(): String = Paths.get("src", "main", "kotlin").toAbsolutePath().toString()

    @JvmStatic
    fun getTestSourceDir(): String = Paths.get("src", "test", "kotlin").toAbsolutePath().toString()

    fun getGeneratedSourceDir(): String = Paths.get( "../core/src/generated-sources/kotlin").toAbsolutePath().toString()

}
