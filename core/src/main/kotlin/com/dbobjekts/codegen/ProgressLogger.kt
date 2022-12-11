package com.dbobjekts.codegen

import org.slf4j.LoggerFactory


class ProgressLogger {

    private val logger = LoggerFactory.getLogger(CodeGenerator::class.java)
    private val errors = mutableListOf<String>()
    private val info = mutableListOf<String>()

    fun error(msg: String): ProgressLogger {
        errors += msg
        logger.error(msg)
        return this
    }

    fun info(msg: String): ProgressLogger {
        info += msg
        logger.info(msg)
        return this
    }
}
