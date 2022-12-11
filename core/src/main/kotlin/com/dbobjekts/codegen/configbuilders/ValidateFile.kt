package com.dbobjekts.codegen.configbuilders

import java.nio.file.Files
import java.nio.file.Paths


object ValidateFile {

    operator fun invoke(str: String, isDir: Boolean = false): Boolean {
        try {
            val path = Paths.get(str)
            return Files.exists(path) && (isDir || Files.isRegularFile(path))
        } catch (e: Exception) {
            throw IllegalArgumentException("Could not parse $str to a valid file: ${e.message}")
        }
    }
}
