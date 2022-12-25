package com.dbobjekts.codegen.configbuilders

import com.dbobjekts.api.PackageName
import com.dbobjekts.codegen.writer.SourceWriter

/**
 * Configuration object for the output of the generated code
 */
class OutputConfigurer {

    internal var basedirOpt: String? = null
    internal var basePackage: PackageName? = null

    /**
     * Sets the dot-separated package name for the generated source files, e.g. com.acme.dbmetatdata
     *
     * Make sure it is unique to your database.
     *
     * A folder for every schema will be created under this package
     */
    fun basePackageForSources(pkg: String): OutputConfigurer {
        basePackage = PackageName(pkg)
        return this
    }

    /**
     * Sets the output directory where the packages will be created. See [basePackageForSources]
     */
    fun outputDirectoryForGeneratedSources(path: String): OutputConfigurer {
        require(ValidateFile(path, isDir = true), { "$path is not a valid directory" })
        basedirOpt = path
        return this
    }


}
