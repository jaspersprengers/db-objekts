package com.dbobjekts.util

import com.dbobjekts.api.exception.DBObjektsException


object Version {
    fun validateCatalogVersion(version: Int) {
        if (version < MINIMUM_REQUIRED_CATALOG) {
            throw DBObjektsException("Catalog was created with a lower major version than the present: it must must be at least $MINIMUM_REQUIRED_CATALOG but is $version. You need to re-run CodeGenerator and fix any incompatibilities.")
        }
    }

    val MAJOR = 0
    val MINOR = 3
    private val MINIMUM_REQUIRED_CATALOG = 0

}
