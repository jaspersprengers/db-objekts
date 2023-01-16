package com.dbobjekts.statement


data class SQLOptions(val includeAlias: Boolean = false) {
    companion object {
        val ALIAS = SQLOptions(true)
        val NO_ALIAS = SQLOptions(false)
    }
}
