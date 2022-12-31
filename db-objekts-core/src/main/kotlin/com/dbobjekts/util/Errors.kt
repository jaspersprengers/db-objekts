package com.dbobjekts.util

import com.dbobjekts.api.exception.DBObjektsException
import java.sql.SQLException

/**
 * Utility object to conveniently throw errors and perform validations
 */
object Errors {

    /**
     * Throws an IllegalStateException, possibly wrapping another exception
     */
    operator fun invoke(
        message: String,
        sqlException: SQLException? = null
    ): Nothing = throw DBObjektsException(message, sqlException)

    /**
     * @param f function must return true
     * @param msg if function returns false, throw an IllegalStateException with given message
     */
    fun require(f: Boolean, msg: String) {
        if (!f)
            throw DBObjektsException(msg)
    }

}
