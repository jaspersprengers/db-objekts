package com.dbobjekts.api.exception


open class DBObjektsException(msg: String, throwable: Throwable?) : RuntimeException(msg, throwable) {
    constructor(msg: String) : this(msg, null)
    constructor() : this("Generic error", null)
}
