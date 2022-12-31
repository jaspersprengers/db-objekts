package com.dbobjekts.api.exception

/**
 * Custom exception to signal errors during code generation.
 */
class CodeGenerationException(msg: String, throwable: Throwable?) : DBObjektsException(msg, throwable) {
    constructor(msg: String) : this(msg, null)
}
