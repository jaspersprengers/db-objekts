package com.dbobjekts.codegen.configbuilders

import com.dbobjekts.api.PackageName
import com.dbobjekts.codegen.writer.SourceWriter

/**
 * Configuration object for the output of the generated code
 */
class OutputConfigurer {

    internal var basedirOpt: String? = null
    internal var basePackage: PackageName? = null
    internal var customSourceWriter: SourceWriter? = null

    /**
     * Sets the dot-separated package name for the generated source files, e.g. com.acme.dbmetatdata
     *
     * If you don't provide a value, com.dbobjekts.<catalog_name> will be used.
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
        if (customSourceWriter != null) {
            throw IllegalStateException("Cannot set output directory when using a custom source writer.")
        }
        require(ValidateFile(path, isDir = true), { "$path is not a valid directory" })
        basedirOpt = path
        return this
    }

    /**
     * For debugging purposes. Sets a custom [SourceWriter]
     */
    fun sourceWriter(writer: SourceWriter): OutputConfigurer {
        this.customSourceWriter = writer
        return this
    }


}
