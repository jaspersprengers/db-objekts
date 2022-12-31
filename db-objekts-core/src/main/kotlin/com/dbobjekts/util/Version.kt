package com.dbobjekts.util

import org.slf4j.LoggerFactory
import java.util.*
import java.util.regex.Pattern


object Version {

    private val log = LoggerFactory.getLogger(Version::class.java)

    val FULL: String
    val MAJOR: String
    val MINOR: String

    init {
        FULL = getVersion()
        val matcher = FULL.split(".")
        MAJOR = matcher[0]
        MINOR = matcher[1]
    }

    private fun getVersion(): String {
        try {
            val inputStream = this.javaClass.classLoader
                .getResourceAsStream("version.properties")
            val prop = Properties()
            prop.load(inputStream)
            return prop.getProperty("version")
        } catch (e: Exception) {
            log.error("Error getting version from properties file", e)
            return "UNKNOWN"
        }
    }
}
