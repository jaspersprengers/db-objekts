package com.dbobjekts.util

import java.util.regex.Pattern

object ValidateDBObjectName {
    private val regex = Pattern.compile("^[a-zA-Z0-9\\-_]+$")

    operator fun invoke(value: String?): Boolean =
        if (value == null) false else regex.matcher(value).matches()

}
