package com.dbobjekts.codegen.configbuilders

import com.dbobjekts.api.PackageName
import com.dbobjekts.api.exception.CodeGenerationException
import com.dbobjekts.codegen.CodeGenerator

/**
 * Configuration object for the output of the generated code
 */
class OutputConfigurer(generator: CodeGenerator) : AbstractConfigurer(generator) {

    internal var basedirOpt: String? = null
    internal var basePackage: PackageName? = null

    /**
     * Sets the dot-separated package name for the generated source files, e.g. com.acme.dbmetatdata.
     *
     * Sub-packages will be created for each schema. In the root will be a CatalogDefinition object that ties them all together
     *
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
